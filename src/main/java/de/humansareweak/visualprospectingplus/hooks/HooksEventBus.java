package de.humansareweak.visualprospectingplus.hooks;

import de.humansareweak.visualprospectingplus.VPP;
import de.humansareweak.visualprospectingplus.Utils;
import de.humansareweak.visualprospectingplus.database.ClientCache;
import de.humansareweak.visualprospectingplus.database.ServerCache;
import de.humansareweak.visualprospectingplus.database.WorldIdHandler;
import de.humansareweak.visualprospectingplus.network.WorldIdNotification;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;

public class HooksEventBus {

    @SubscribeEvent
    public void onEvent(WorldEvent.Unload event) {
        if(Utils.isLogicalClient()) {
            ClientCache.instance.saveVeinCache();
        }
    }

    @SubscribeEvent
    public void onEvent(WorldEvent.Save event) {
        ServerCache.instance.saveVeinCache();
    }

    @SubscribeEvent
    public void onEvent(EntityJoinWorldEvent event) {
        if(event.world.isRemote == false) {
            if (event.entity instanceof EntityPlayerMP) {
                VPP.network.sendTo(new WorldIdNotification(WorldIdHandler.getWorldId()), (EntityPlayerMP) event.entity);
            }
            else if (event.entity instanceof EntityPlayer) {
                ClientCache.instance.loadVeinCache(WorldIdHandler.getWorldId());
            }
        }
    }
}
