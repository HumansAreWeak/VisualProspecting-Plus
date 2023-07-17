package de.humansareweak.visualprospectingplus;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import de.humansareweak.visualprospectingplus.hooks.HooksShared;
import gregapi.api.Abstract_Mod;
import gregapi.api.Abstract_Proxy;
import gregapi.network.NetworkHandler;


@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.7.10]", dependencies = "required-after:spongemixins@[1.1.0,);")
public class VPPMod extends Abstract_Mod {
    public static NetworkHandler NW_PJ;
    @SidedProxy(modId = Tags.MODID, clientSide= Tags.GROUPNAME + ".hooks.HooksClient", serverSide= Tags.GROUPNAME + ".hooks.HooksShared")
    public static HooksShared PROXY;

    @Override public String getModID() {return Tags.MODID;}
    @Override public String getModName() {return Tags.MODNAME;}
    @Override public String getModNameForLog() {return "VPPlus";}
    @Override public Abstract_Proxy getProxy() {return PROXY;}

    // Do not change these 7 Functions. Just keep them this way.
    @EventHandler public final void onPreLoad           (FMLPreInitializationEvent    aEvent) {onModPreInit(aEvent);}
    @EventHandler public final void onLoad              (FMLInitializationEvent       aEvent) {onModInit(aEvent);}
    @EventHandler public final void onPostLoad          (FMLPostInitializationEvent   aEvent) {onModPostInit(aEvent);}
    @EventHandler public final void onServerStarting    (FMLServerStartingEvent       aEvent) {onModServerStarting(aEvent);}
    @EventHandler public final void onServerStarted     (FMLServerStartedEvent        aEvent) {onModServerStarted(aEvent);}
    @EventHandler public final void onServerStopping    (FMLServerStoppingEvent       aEvent) {onModServerStopping(aEvent);}
    @EventHandler public final void onServerStopped     (FMLServerStoppedEvent        aEvent) {onModServerStopped(aEvent);}

    @Override
    public void onModPreInit2(FMLPreInitializationEvent aEvent) {
        VPP.debug("Registered sided proxy for: " + (Utils.isLogicalClient() ? "Client" : "Dedicated server"));
        VPP.debug("preInit()"+aEvent.getModMetadata().name);
        PROXY.fmlLifeCycleEvent(aEvent);
    }

    @Override
    public void onModInit2(FMLInitializationEvent aEvent) {
        VPP.debug("init()");
        PROXY.fmlLifeCycleEvent(aEvent);
    }

    @Override
    public void onModPostInit2(FMLPostInitializationEvent aEvent) {
        VPP.debug("postInit()");
        PROXY.fmlLifeCycleEvent(aEvent);
    }

    @Override
    public void onModServerStarting2(FMLServerStartingEvent aEvent) {
        VPP.debug("Server starting");
        PROXY.fmlLifeCycleEvent(aEvent);
    }

    @Override
    public void onModServerStarted2(FMLServerStartedEvent aEvent) {
        VPP.debug("Server started");
        PROXY.fmlLifeCycleEvent(aEvent);
    }

    @Override
    public void onModServerStopping2(FMLServerStoppingEvent aEvent) {
        VPP.debug("Server stopping");
        PROXY.fmlLifeCycleEvent(aEvent);
    }

    @Override
    public void onModServerStopped2(FMLServerStoppedEvent aEvent) {
        VPP.debug("Server stopped");
        PROXY.fmlLifeCycleEvent(aEvent);
    }
}
