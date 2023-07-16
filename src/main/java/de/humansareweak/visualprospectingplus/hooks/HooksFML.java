package de.humansareweak.visualprospectingplus.hooks;

import de.humansareweak.visualprospectingplus.database.ClientCache;
import de.humansareweak.visualprospectingplus.task.TaskManager;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;

public class HooksFML {

    @SubscribeEvent
    public void onEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        ClientCache.instance.reset();
    }

    @SubscribeEvent
    public void onEvent(TickEvent event) {
        TaskManager.instance.onTick();
    }
}
