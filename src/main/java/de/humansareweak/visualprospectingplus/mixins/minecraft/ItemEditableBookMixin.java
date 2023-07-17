package de.humansareweak.visualprospectingplus.mixins.minecraft;

import de.humansareweak.visualprospectingplus.VPP;
import de.humansareweak.visualprospectingplus.Tags;
import de.humansareweak.visualprospectingplus.Utils;
import de.humansareweak.visualprospectingplus.database.ClientCache;
import de.humansareweak.visualprospectingplus.database.ServerCache;
import de.humansareweak.visualprospectingplus.database.UndergroundFluidPosition;
import de.humansareweak.visualprospectingplus.database.OreVeinPosition;
import de.humansareweak.visualprospectingplus.network.ProspectingNotification;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = ItemEditableBook.class, remap = true)
public class ItemEditableBookMixin {

    @Inject(method = "onItemRightClick",
            at = @At("HEAD"),
            remap = true,
            require = 1,
            locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer, CallbackInfoReturnable<ItemStack> callbackInfoReturnable) {
        if(world.isRemote == false) {
            final NBTTagCompound compound = itemStack.getTagCompound();
            if(compound.hasKey(Tags.VISUALPROSPECTING_FLAG)) {
                final int dimensionId = compound.getInteger(Tags.PROSPECTION_DIMENSION_ID);
                final int blockX = compound.getInteger(Tags.PROSPECTION_BLOCK_X);
                final int blockZ = compound.getInteger(Tags.PROSPECTION_BLOCK_Z);
                final int blockRadius = compound.getInteger(Tags.PROSPECTION_ORE_RADIUS);
                final List<OreVeinPosition> foundOreVeins = ServerCache.instance.prospectOreBlockRadius(dimensionId, blockX, blockZ, blockRadius);
                final List<UndergroundFluidPosition> foundUndergroundFluids = ServerCache.instance.prospectUndergroundFluidBlockRadius(world, blockX, blockZ, VPP.undergroundFluidChunkProspectingBlockRadius);
                if(Utils.isLogicalClient()) {
                    ClientCache.instance.putOreVeins(foundOreVeins);
                    ClientCache.instance.putUndergroundFluids(foundUndergroundFluids);
                }
                else {
                    VPP.network.sendTo(new ProspectingNotification(foundOreVeins, foundUndergroundFluids), (EntityPlayerMP) entityPlayer);
                }
            }
        }
    }
}
