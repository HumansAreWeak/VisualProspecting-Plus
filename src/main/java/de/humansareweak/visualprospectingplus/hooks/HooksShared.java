package de.humansareweak.visualprospectingplus.hooks;

import de.humansareweak.visualprospectingplus.VPP;
import de.humansareweak.visualprospectingplus.Config;
import de.humansareweak.visualprospectingplus.Tags;
import de.humansareweak.visualprospectingplus.database.ServerCache;
import de.humansareweak.visualprospectingplus.database.WorldIdHandler;
import de.humansareweak.visualprospectingplus.database.cachebuilder.WorldAnalysis;
import de.humansareweak.visualprospectingplus.item.ProspectorsLog;
import de.humansareweak.visualprospectingplus.network.ProspectingNotification;
import de.humansareweak.visualprospectingplus.network.ProspectingRequest;
import de.humansareweak.visualprospectingplus.network.ProspectionSharing;
import de.humansareweak.visualprospectingplus.network.WorldIdNotification;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import gregapi.api.Abstract_Proxy;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.zip.DataFormatException;


public class HooksShared extends Abstract_Proxy {

	// preInit "Run before anything else. Read your config, create blocks, items,
	// etc, and register them with the GameRegistry."
	public void fmlLifeCycleEvent(FMLPreInitializationEvent event) 	{
		Config.syncronizeConfiguration(event.getSuggestedConfigurationFile());

		VPP.network = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MODID);
		int networkId = 0;
		VPP.network.registerMessage(ProspectingRequest.Handler.class, ProspectingRequest.class, networkId++, Side.SERVER);
		VPP.network.registerMessage(ProspectingNotification.Handler.class, ProspectingNotification.class, networkId++, Side.CLIENT);
		VPP.network.registerMessage(WorldIdNotification.Handler.class, WorldIdNotification.class, networkId++, Side.CLIENT);
		VPP.network.registerMessage(ProspectionSharing.ServerHandler.class, ProspectionSharing.class, networkId++, Side.SERVER);
		VPP.network.registerMessage(ProspectionSharing.ClientHandler.class, ProspectionSharing.class, networkId++, Side.CLIENT);

		ProspectorsLog.instance = new ProspectorsLog();
		GameRegistry.registerItem(ProspectorsLog.instance, ProspectorsLog.instance.getUnlocalizedName());
	}

	// load "Do your mod setup. Build whatever data structures you care about. Register recipes."
	public void fmlLifeCycleEvent(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new HooksEventBus());
		FMLCommonHandler.instance().bus().register(new HooksFML());
	}

	// postInit "Handle interaction with other mods, complete your setup based on this."
	public void fmlLifeCycleEvent(FMLPostInitializationEvent event) {
        // TODO: Add recipe for ProspectorsLog
        //CS.GT.mAfterPostInit.add(new VeinTypeCaching());
        /*
        CS.GT.mAfterPostInit.add(() -> GT_Values.RA.addAssemblerRecipe(
            new ItemStack[] {
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Wood, 2L),
                new ItemStack(Items.writable_book, 1, 0x7FFF),
                new ItemStack(Items.gold_nugget, 1, 0x7FFF)
            },
            Materials.Glue.getFluid(20L),
            new ItemStack(ProspectorsLog.instance, 1, 0),
            128,
            8)
        );
         */
	}

	public void fmlLifeCycleEvent(FMLServerAboutToStartEvent event) {

	}

	// register server commands in this event handler
	public void fmlLifeCycleEvent(FMLServerStartingEvent event) {
		final MinecraftServer minecraftServer = event.getServer();
		WorldIdHandler.load(minecraftServer.worldServers[0]);
		if(ServerCache.instance.loadVeinCache(WorldIdHandler.getWorldId()) == false || Config.recacheVeins) {
			try {
				WorldAnalysis world = new WorldAnalysis(minecraftServer.getEntityWorld().getSaveHandler().getWorldDirectory());
				world.cacheVeins();
			}
			catch (IOException | DataFormatException e) {
				VPP.info("Could not load world save files to build vein cache!");
				e.printStackTrace();
			}
		}
	}

	public void fmlLifeCycleEvent(FMLServerStartedEvent event) {

	}

	public void fmlLifeCycleEvent(FMLServerStoppingEvent event) {
		ServerCache.instance.saveVeinCache();
		ServerCache.instance.reset();
	}

	public void fmlLifeCycleEvent(FMLServerStoppedEvent event) {

	}
}
