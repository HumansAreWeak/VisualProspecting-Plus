package de.humansareweak.visualprospectingplus.network;

import de.humansareweak.visualprospectingplus.Config;
import de.humansareweak.visualprospectingplus.Utils;
import de.humansareweak.visualprospectingplus.VPP;
import de.humansareweak.visualprospectingplus.database.OreVeinPosition;
import de.humansareweak.visualprospectingplus.database.ServerCache;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import gregapi.block.prefixblock.PrefixBlock;
import gregapi.block.prefixblock.PrefixBlockTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.*;

public class ProspectingRequest implements IMessage {

    public static long timestampLastRequest = 0;

    private int dimensionId;
    private int blockX;
    private int blockY;
    private int blockZ;
    private short foundOreMetaData;

    public ProspectingRequest() {

    }

    public ProspectingRequest(int dimensionId, int blockX, int blockY, int blockZ, short foundOreMetaData) {
        this.dimensionId = dimensionId;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.foundOreMetaData = foundOreMetaData;
    }

    public static boolean canSendRequest() {
        final long timestamp = System.currentTimeMillis();
        if(timestamp - timestampLastRequest > Config.minDelayBetweenVeinRequests) {
            timestampLastRequest = timestamp;
            return true;
        }
        return false;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dimensionId = buf.readInt();
        blockX = buf.readInt();
        blockY = buf.readInt();
        blockZ = buf.readInt();
        foundOreMetaData = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(dimensionId);
        buf.writeInt(blockX);
        buf.writeInt(blockY);
        buf.writeInt(blockZ);
        buf.writeShort(foundOreMetaData);
    }

    public static class Handler implements IMessageHandler<ProspectingRequest, IMessage> {

        private static final Map<UUID, Long> lastRequestPerPlayer = new HashMap<>();

        @Override
        public IMessage onMessage(ProspectingRequest message, MessageContext ctx) {
            // Check if request is valid/not tempered with
            final UUID uuid = ctx.getServerHandler().playerEntity.getUniqueID();
            final long lastRequest = lastRequestPerPlayer.containsKey(uuid) ? lastRequestPerPlayer.get(uuid) : 0;
            final long timestamp = System.currentTimeMillis();
            final float distanceSquared = ctx.getServerHandler().playerEntity.getPlayerCoordinates().getDistanceSquared(message.blockX, message.blockY, message.blockZ);
            final World world = ctx.getServerHandler().playerEntity.getEntityWorld();
            final int chunkX = Utils.coordBlockToChunk(message.blockX);
            final int chunkZ = Utils.coordBlockToChunk(message.blockZ);
            final boolean isChunkLoaded = world.getChunkProvider().chunkExists(chunkX, chunkZ);
            if(ctx.getServerHandler().playerEntity.dimension == message.dimensionId
                    && distanceSquared <= 1024  // max 32 blocks distance
                    && timestamp - lastRequest >= Config.minDelayBetweenVeinRequests
                    && isChunkLoaded) {
                final Block block = world.getBlock(message.blockX, message.blockY, message.blockZ);

                // TODO: Fix the API
                VPP.info("BLOCK is instanceof PrefixBlock: " + (block instanceof PrefixBlock));

                if(block instanceof PrefixBlock) {
                    final TileEntity tileEntity = world.getTileEntity(message.blockX, message.blockY, message.blockZ);

                    // TODO: Fix the API
                    VPP.info("TILE_ENTITY is instanceof PrefixBlockTileEntity: " + (tileEntity instanceof PrefixBlockTileEntity));

                    if (tileEntity instanceof PrefixBlockTileEntity) {
                        final short metaData = ((PrefixBlockTileEntity) tileEntity).mMetaData;
                        //((PrefixBlockTileEntity) tileEntity).mPrefix
                        if(Utils.isSmallOreId(metaData) == false && Utils.oreIdToMaterialId(metaData) == message.foundOreMetaData) {
                            lastRequestPerPlayer.put(uuid, timestamp);

                            // Prioritise center vein
                            final OreVeinPosition centerOreVeinPosition = ServerCache.instance.getOreVein(message.dimensionId, chunkX, chunkZ);
                            if(centerOreVeinPosition.veinType.containsOre(message.foundOreMetaData)) {
                                return new ProspectingNotification(centerOreVeinPosition);
                            }

                            // Check if neighboring veins could fit
                            final int centerChunkX = Utils.mapToCenterOreChunkCoord(chunkX);
                            final int centerChunkZ = Utils.mapToCenterOreChunkCoord(chunkZ);
                            for(int offsetChunkX = -3; offsetChunkX <= 3; offsetChunkX += 3) {
                                for (int offsetChunkZ = -3; offsetChunkZ <= 3; offsetChunkZ += 3) {
                                    if (offsetChunkX != 0 || offsetChunkZ != 0) {
                                        final int neighborChunkX = centerChunkX + offsetChunkX;
                                        final int neighborChunkZ = centerChunkZ + offsetChunkZ;
                                        final int distanceBlocks = Math.max(Math.abs(neighborChunkX - chunkX), Math.abs(neighborChunkZ - chunkZ));
                                        final OreVeinPosition neighborOreVeinPosition = ServerCache.instance.getOreVein(message.dimensionId, neighborChunkX, neighborChunkZ);
                                        final int maxDistance = ((neighborOreVeinPosition.veinType.blockSize + 16) >> 4) + 1;  // Equals to: ceil(blockSize / 16.0) + 1
                                        if (neighborOreVeinPosition.veinType.containsOre(message.foundOreMetaData) && distanceBlocks <= maxDistance) {
                                            return new ProspectingNotification(neighborOreVeinPosition);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
    }
}
