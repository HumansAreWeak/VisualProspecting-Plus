package de.humansareweak.visualprospectingplus.database.cachebuilder;

import de.humansareweak.visualprospectingplus.Config;
import de.humansareweak.visualprospectingplus.VPP;
import de.humansareweak.visualprospectingplus.Utils;
import de.humansareweak.visualprospectingplus.database.ServerCache;
import de.humansareweak.visualprospectingplus.database.veintypes.VeinType;
import io.xol.enklume.MinecraftRegion;
import io.xol.enklume.MinecraftWorld;
import io.xol.enklume.nbt.NBTCompound;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class DimensionAnalysis {

    public final int dimensionId;

    public DimensionAnalysis(int dimensionId) {
        this.dimensionId = dimensionId;
    }

    private interface IChunkHandler {
        void processChunk(NBTCompound root, int chunkX, int chunkZ);
    }

    public void processMinecraftWorld(MinecraftWorld world) throws IOException {
        final Map<Long, Integer> veinBlockY = new ConcurrentHashMap<>();
        final List<File> regionFiles = world.getAllRegionFiles(dimensionId);
        final long dimensionSizeMB = regionFiles.stream().mapToLong(File::length).sum() >> 20;

        if (dimensionSizeMB <= Config.maxDimensionSizeMBForFastScanning) {
            AnalysisProgressTracker.announceFastDimension(dimensionId);
            AnalysisProgressTracker.setNumberOfRegionFiles(regionFiles.size());

            final Map<Long, DetailedChunkAnalysis> chunksForSecondIdentificationPass = new ConcurrentHashMap<>();

            VPP.info("Region Files: " + regionFiles);

            regionFiles.parallelStream().forEach(regionFile -> {
                executeForEachGeneratedOreChunk(regionFile, (root, chunkX, chunkZ) -> {
                    final ChunkAnalysis chunk = new ChunkAnalysis();
                    chunk.processMinecraftChunk(root);

                    if (chunk.matchesSingleVein()) {
                        ServerCache.instance.notifyOreVeinGeneration(dimensionId, chunkX, chunkZ, chunk.getMatchedVein());
                        veinBlockY.put(Utils.chunkCoordsToKey(chunkX, chunkZ), chunk.getVeinBlockY());
                    } else {
                        final DetailedChunkAnalysis detailedChunk = new DetailedChunkAnalysis(dimensionId, chunkX, chunkZ);
                        detailedChunk.processMinecraftChunk(root);
                        chunksForSecondIdentificationPass.put(Utils.chunkCoordsToKey(chunkX, chunkZ), detailedChunk);
                    }
                });
            });

            chunksForSecondIdentificationPass.values().parallelStream().forEach(chunk -> {
                chunk.cleanUpWithNeighbors(veinBlockY);
                ServerCache.instance.notifyOreVeinGeneration(dimensionId, chunk.chunkX, chunk.chunkZ, chunk.getMatchedVein());
            });
        } else {
            AnalysisProgressTracker.announceSlowDimension(dimensionId);
            AnalysisProgressTracker.setNumberOfRegionFiles(regionFiles.size() * 2);

            regionFiles.parallelStream().forEach(regionFile -> {
                executeForEachGeneratedOreChunk(regionFile, (root, chunkX, chunkZ) -> {
                    final ChunkAnalysis chunk = new ChunkAnalysis();
                    chunk.processMinecraftChunk(root);

                    if (chunk.matchesSingleVein()) {
                        ServerCache.instance.notifyOreVeinGeneration(dimensionId, chunkX, chunkZ, chunk.getMatchedVein());
                        veinBlockY.put(Utils.chunkCoordsToKey(chunkX, chunkZ), chunk.getVeinBlockY());
                    }
                });
            });

            regionFiles.parallelStream().forEach(regionFile -> {
                executeForEachGeneratedOreChunk(regionFile, (root, chunkX, chunkZ) -> {
                    if (ServerCache.instance.getOreVein(dimensionId, chunkX, chunkZ).veinType == VeinType.NO_VEIN) {
                        final DetailedChunkAnalysis detailedChunk = new DetailedChunkAnalysis(dimensionId, chunkX, chunkZ);
                        detailedChunk.processMinecraftChunk(root);
                        detailedChunk.cleanUpWithNeighbors(veinBlockY);
                        ServerCache.instance.notifyOreVeinGeneration(dimensionId, detailedChunk.chunkX, detailedChunk.chunkZ, detailedChunk.getMatchedVein());
                    }
                });
            });
        }
    }

    private void executeForEachGeneratedOreChunk(File regionFile, IChunkHandler chunkHandler) {
        try {
            if ( !Pattern.matches("^r\\.-?\\d+\\.-?\\d+\\.mca$", regionFile.getName())) {
                VPP.warn("Invalid region file found! " + regionFile.getCanonicalPath() + " continuing");
                return;
            }
            final String[] parts = regionFile.getName().split("\\.");
            final int regionChunkX = Integer.parseInt(parts[1]) << 5;
            final int regionChunkZ = Integer.parseInt(parts[2]) << 5;
            final MinecraftRegion region = new MinecraftRegion(regionFile);
            for (int localChunkX = 0; localChunkX < VPP.chunksPerRegionFileX; localChunkX++) {
                for (int localChunkZ = 0; localChunkZ < VPP.chunksPerRegionFileZ; localChunkZ++) {
                    final int chunkX = regionChunkX + localChunkX;
                    final int chunkZ = regionChunkZ + localChunkZ;

                    // Only process ore chunks
                    if (chunkX == Utils.mapToCenterOreChunkCoord(chunkX) && chunkZ == Utils.mapToCenterOreChunkCoord(chunkZ)) {
                        // Helpful read about 'root' structure: https://minecraft.fandom.com/wiki/Chunk_format
                        final NBTCompound root = region.getChunk(localChunkX, localChunkZ).getRootTag();

                        // root == null occurs when a chunk is not yet generated
                        if (root != null) {
                            chunkHandler.processChunk(root, chunkX, chunkZ);
                        }
                    }
                }
            }
            region.close();
            AnalysisProgressTracker.regionFileProcessed();
        } catch (DataFormatException | IOException e) {
            AnalysisProgressTracker.notifyCorruptFile(regionFile);
        }
    }
}
