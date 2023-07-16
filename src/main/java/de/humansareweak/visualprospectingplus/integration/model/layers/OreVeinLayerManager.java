package de.humansareweak.visualprospectingplus.integration.model.layers;

import de.humansareweak.visualprospectingplus.Utils;
import de.humansareweak.visualprospectingplus.database.ClientCache;
import de.humansareweak.visualprospectingplus.database.OreVeinPosition;
import de.humansareweak.visualprospectingplus.database.veintypes.VeinType;
import de.humansareweak.visualprospectingplus.database.veintypes.VeinTypeCaching;
import de.humansareweak.visualprospectingplus.integration.model.buttons.OreVeinButtonManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.IWaypointAndLocationProvider;
import de.humansareweak.visualprospectingplus.integration.model.locations.OreVeinLocation;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class OreVeinLayerManager extends WaypointProviderManager {

    public static final OreVeinLayerManager instance = new OreVeinLayerManager();

    private int oldMinOreChunkX = 0;
    private int oldMaxOreChunkX = 0;
    private int oldMinOreChunkZ = 0;
    private int oldMaxOreChunkZ = 0;

    public OreVeinLayerManager() {
        super(OreVeinButtonManager.instance);
    }

    @Override
    public void onOpenMap() {
        VeinTypeCaching.recalculateNEISearch();
    }

    @Override
    protected boolean needsRegenerateVisibleElements(int minBlockX, int minBlockZ, int maxBlockX, int maxBlockZ) {
        final int minOreChunkX = Utils.mapToCenterOreChunkCoord(Utils.coordBlockToChunk(minBlockX));
        final int minOreChunkZ = Utils.mapToCenterOreChunkCoord(Utils.coordBlockToChunk(minBlockZ));
        final int maxOreChunkX = Utils.mapToCenterOreChunkCoord(Utils.coordBlockToChunk(maxBlockX));
        final int maxOreChunkZ = Utils.mapToCenterOreChunkCoord(Utils.coordBlockToChunk(maxBlockZ));

        if (minOreChunkX != oldMinOreChunkX || maxOreChunkX != oldMaxOreChunkX || minOreChunkZ != oldMinOreChunkZ || maxOreChunkZ != oldMaxOreChunkZ) {
            oldMinOreChunkX = minOreChunkX;
            oldMaxOreChunkX = maxOreChunkX;
            oldMinOreChunkZ = minOreChunkZ;
            oldMaxOreChunkZ = maxOreChunkZ;
            return true;
        }
        return false;
    }

    @Override
    protected List<? extends IWaypointAndLocationProvider> generateVisibleElements(int minBlockX, int minBlockZ, int maxBlockX, int maxBlockZ) {
        final int minOreChunkX = Utils.mapToCenterOreChunkCoord(Utils.coordBlockToChunk(minBlockX));
        final int minOreChunkZ = Utils.mapToCenterOreChunkCoord(Utils.coordBlockToChunk(minBlockZ));
        final int maxOreChunkX = Utils.mapToCenterOreChunkCoord(Utils.coordBlockToChunk(maxBlockX));
        final int maxOreChunkZ = Utils.mapToCenterOreChunkCoord(Utils.coordBlockToChunk(maxBlockZ));
        final int playerDimensionId = Minecraft.getMinecraft().thePlayer.dimension;

        ArrayList<OreVeinLocation> oreChunkLocations = new ArrayList<>();

        for (int chunkX = minOreChunkX; chunkX <= maxOreChunkX; chunkX = Utils.mapToCenterOreChunkCoord(chunkX + 3)) {
            for (int chunkZ = minOreChunkZ; chunkZ <= maxOreChunkZ; chunkZ = Utils.mapToCenterOreChunkCoord(chunkZ + 3)) {
                final OreVeinPosition oreVeinPosition = ClientCache.instance.getOreVein(playerDimensionId, chunkX, chunkZ);
                if (oreVeinPosition.veinType != VeinType.NO_VEIN) {
                    oreChunkLocations.add(new OreVeinLocation(oreVeinPosition));
                }
            }
        }

        return oreChunkLocations;
    }
}
