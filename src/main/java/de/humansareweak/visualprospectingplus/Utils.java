package de.humansareweak.visualprospectingplus;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cpw.mods.fml.common.Loader;
import de.humansareweak.visualprospectingplus.hooks.HooksClient;
import gregapi.tileentity.notick.TileEntityBase03MultiTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.launchwrapper.Launch;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static boolean isDevelopmentEnvironment() {
        return (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    public static boolean isBartworksInstalled() {
        return false;
    }

    public static boolean isNEIInstalled() {
        return Loader.isModLoaded("NotEnoughItems");
    }

    public static boolean isTCNodeTrackerInstalled() {
        return Loader.isModLoaded("tcnodetracker");
    }

    public static boolean isJourneyMapInstalled() {
        return Loader.isModLoaded("journeymap");
    }

    public static boolean isXaerosWorldMapInstalled() {
        return Loader.isModLoaded("XaeroWorldMap");
    }

    public static boolean isVoxelMapInstalled() {
        try {
            // If a LiteLoader mod is present cannot be checked by calling Loader#isModLoaded.
            // Instead, we check if the VoxelMap main class is present.
            Class.forName("com.thevoxelbox.voxelmap.litemod.LiteModVoxelMap");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int coordBlockToChunk(int blockCoord) {
        return blockCoord < 0 ? -((-blockCoord - 1) >> 4) - 1 : blockCoord >> 4;
    }

    public static int coordChunkToBlock(int chunkCoord) {
        return chunkCoord < 0 ? -((-chunkCoord) << 4) : chunkCoord << 4;
    }

    public static long chunkCoordsToKey(int chunkX, int chunkZ) {
        return (((long)chunkX) << 32) | (chunkZ & 0xffffffffL);
    }

    public static int mapToCenterOreChunkCoord(final int chunkCoord) {
        if(chunkCoord >= 0) {
            return chunkCoord - (chunkCoord % 3) + 1;
        }
        else {
            return chunkCoord - (chunkCoord % 3) - 1;
        }
    }

    public static int mapToCornerUndergroundFluidChunkCoord(final int chunkCoord) {
        return chunkCoord & 0xFFFFFFF8;
    }

    public static double journeyMapScaleToLinear(final int jzoom) {
        return Math.pow(2, jzoom);
    }

    public static boolean isSmallOre(TileEntityBase03MultiTileEntities tileEntity) {
        return tileEntity.getTileEntityName().startsWith("gt.multitileenetity.ore.small");
    }

    public static short oreIdToMaterialId(short metaData) {
        return (short)(metaData % 1000);
    }

    public static boolean isLogicalClient() {
        return VPMod.PROXY instanceof HooksClient;
    }

    public static File getMinecraftDirectory() {
        if(isLogicalClient()) {
            return Minecraft.getMinecraft().mcDataDir;
        }
        else {
            return new File(".");
        }
    }

    public static File getSubDirectory(final String subdirectory) {
        return new File(getMinecraftDirectory(), subdirectory);
    }

    public static void deleteDirectoryRecursively(final File targetDirectory) {
        try {
            Files.walk(targetDirectory.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ByteBuffer readFileToBuffer(File file) {
        if(file.exists() == false) {
            return null;
        }
        try
        {
            final FileInputStream inputStream = new FileInputStream(file);
            final FileChannel inputChannel = inputStream.getChannel();
            final ByteBuffer buffer = ByteBuffer.allocate((int) inputChannel.size());

            inputChannel.read(buffer);
            buffer.flip();

            inputChannel.close();
            inputStream.close();

            return buffer;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Short> readFileToMap(File file) {
        if(file.exists() == false) {
            return new HashMap<>();
        }
        try {
            final Gson gson = new Gson();
            final Reader reader = Files.newBufferedReader(file.toPath());
            final Map<String, Short> map = gson.fromJson(reader, new TypeToken<Map<String, Short>>() { }.getType());
            reader.close();
            return map;
        }
        catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void writeMapToFile(File file, Map<String, Short> map) {
        try {
            if(file.exists()) {
                file.delete();
            }
            final Gson gson = new Gson();
            final Writer writer = Files.newBufferedWriter(file.toPath(), StandardOpenOption.CREATE_NEW);
            gson.toJson(map, new TypeToken<Map<String, Short>>() { }.getType(), writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToFile(File file, ByteBuffer byteBuffer) {
        try {
            if(file.exists() == false) {
                file.createNewFile();
            }
            final FileOutputStream outputStream = new FileOutputStream(file, true);
            final FileChannel outputChannel = outputStream.getChannel();

            outputChannel.write(byteBuffer);

            outputChannel.close();
            outputStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, ByteBuffer> getDIMFiles(File directory) {
        try {
            final List<Integer> dimensionIds = Files.walk(directory.toPath(), 1)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("DIM"))
                    .map(dimensionFolder -> Integer.parseInt(dimensionFolder.getFileName().toString().substring(3)))
                    .collect(Collectors.toList());
            final Map<Integer, ByteBuffer> dimensionFiles = new HashMap<>();
            for (int dimensionId : dimensionIds) {
                ByteBuffer buffer = readFileToBuffer(new File(directory.toPath() + "/DIM" + dimensionId));
                if (buffer != null) {
                    dimensionFiles.put(dimensionId, buffer);
                }
            }
            return dimensionFiles;

        }
        catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static int bind8ArrayToInt(short[] arr) {
        assert arr.length > 3;
        return
            ((arr[0] & 0xFF) << 24) |
            ((arr[1] & 0xFF) << 16) |
            ((arr[2] & 0xFF) <<  8) |
            ((arr[3] & 0xFF) <<  0);
    }

    // TODO: Implement it correctly...
    public static boolean isSmallOreId(short metaData) {
        return false;
    }



    public static void chatAt(EntityPlayer aPlayer, Utils.ChatString rock2) {
        if(aPlayer.isClientWorld()) {
            net.minecraft.util.ChatComponentText chaty = new net.minecraft.util.ChatComponentText(rock2.toString());
            aPlayer.addChatMessage(chaty);
        } else {
            VP.info("Send message to player " + aPlayer.getDisplayName() + ": " + rock2);
        }
    }

    public final static byte ROCK = 0, FLOWER = 1, ORE_VEIN = 2, BEDROCK = 3;
    public final static String GT_FILE = "GT6OreVeins.json",
        GT_BED_FILE = "GT6BedrockSpots.json",
        DWARF_FILE = "GT6_Geochemistry.json",
        IE_VOID_FILE = "IE_Excavations.json";

    public static void writeJson(String name) {
        VP.info("Trying to write JSON with (name="+name+")");
    }

    public static enum ChatString {
        ROCK("msg.rock.name"),
        DRILL_FAIL("msg.iesamplefail.name"),
        CORE_WAIT("msg.core_wait.name"),
        SMALL("msg.smallore.name"),
        FLINT("msg.flint.name"),
        METEOR("msg.meteor.name"),
        FLOWERS("msg.flowerpatch.name"),
        BEDFLOWER("msg.bedflower.name"),
        DUPE("msg.duplicate.name"),
        DEPLETED("msg.depleted.name"),
        CHANGED("msg.iechanged.name"),
        RECLASS("msg.reclass.name"),
        FINDING("msg.finding.name");
        private final String mKey;
        ChatString(String key) {
            mKey = key;
        }

        @Override
        public String toString(){
            return mKey;
        }
    }
}
