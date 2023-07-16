package de.humansareweak.visualprospectingplus.mixins.gregtech;

import de.humansareweak.visualprospectingplus.database.ClientCache;
import gregapi.network.IPacket;
import gregapi.tileentity.notick.TileEntityBase02Sync;
import gregapi.tileentity.notick.TileEntityBase03MultiTileEntities;
import gregapi.util.WD;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static gregapi.data.CS.F;
import static gregapi.data.CS.SIDES_INVALID;

/**
 * Refer to <a href="https://ftb.fandom.com/wiki/GregTech_6/Stones_and_Minerals#Ores">Stones and Minerals</a> if
 * you are confused what I'm doing here.
 */
@Mixin(value = TileEntityBase03MultiTileEntities.class, remap = false)
public abstract class TileEntityBase03MultiTileEntitiesMixin extends TileEntityBase02Sync {

    @Shadow
    public abstract boolean checkObstruction(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ);

    @Shadow
    public abstract boolean onBlockActivated2(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ);

    /**
     * @author HumansAreWeak
     * @reason Inform the client cache on each interaction with an GT Stone / Ore block
     */
    @Overwrite
    public final boolean onBlockActivated(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        ClientCache.instance.onOreInteracted(worldObj, xCoord, yCoord, zCoord, aPlayer);
        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
        return allowRightclick(aPlayer) && (checkObstruction(aPlayer, aSide, aHitX, aHitY, aHitZ) || onBlockActivated2(aPlayer, aSide, aHitX, aHitY, aHitZ));
    }

}
