package de.humansareweak.visualprospectingplus.mixinplugin;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import java.util.Arrays;
import java.util.List;

import static de.humansareweak.visualprospectingplus.mixinplugin.TargetedMod.*;

public enum Mixin {

    //
    // IMPORTANT: Do not make any references to any mod from this file. This file is loaded quite early on and if
    // you refer to other mods you load them as well. The consequence is: You can't inject any previously loaded classes!
    // Exception: Tags.java, as long as it is used for Strings only!
    //


    GT_TileEntityBase03MultiTileEntitiesMixin("gregtech.TileEntityBase03MultiTileEntitiesMixin", GREGTECH),
    // GT_Block_Ores_AbstractMixin("gregtech.GT_Block_Ores_AbstractMixin", GREGTECH),
    // GT_MetaTileEntity_AdvSeismicProspectorMixin("gregtech.GT_MetaTileEntity_AdvSeismicProspectorMixin", GREGTECH),
    // GT_MetaTileEntity_ScannerMixin("gregtech.GT_MetaTileEntity_ScannerMixin", GREGTECH),
    // GT_WorldGenContainerMixin("gregtech.WorldGenContainerMixin", GREGTECH),

    FullscreenMixin("journeymap.FullscreenMixin", Side.CLIENT, JOURNEYMAP),
    FullscreenActionsMixin("journeymap.FullscreenActionsMixin", Side.CLIENT, JOURNEYMAP),
    MiniMapMixin("journeymap.MiniMapMixin", Side.CLIENT, JOURNEYMAP),
    RenderWaypointBeaconMixin("journeymap.RenderWaypointBeaconMixin", Side.CLIENT, JOURNEYMAP),
    WaypointManagerMixin("journeymap.WaypointManagerMixin", Side.CLIENT, JOURNEYMAP),

    GuiMainMixin("tcnodetracker.GuiMainMixin", Side.CLIENT, TCNODETRACKER),

    GuiMapMixin("xaerosworldmap.GuiMapMixin", Side.CLIENT, XAEROWORLDMAP),
    WaypointsIngameRendererMixin("xaerosminimap.WaypointsIngameRendererMixin", Side.CLIENT, XAEROMINIMAP),
    // MinimapRendererMixin("xaerosminimap.MinimapRendererMixin", Side.CLIENT, XAEROMINIMAP, XAEROWORLDMAP),
    // used to enable the stencil buffer for on-minimap rendering
    ForgeHooksClientMixin("minecraft.ForgeHooksClientMixin", Side.CLIENT, XAEROMINIMAP, XAEROWORLDMAP),

    ItemEditableBookMixin("minecraft.ItemEditableBookMixin", VANILLA);

    public final String mixinClass;
    public final List<TargetedMod> targetedMods;
    private final Side side;

    Mixin(String mixinClass, Side side, TargetedMod... targetedMods) {
        this.mixinClass = mixinClass;
        this.targetedMods = Arrays.asList(targetedMods);
        this.side = side;
    }

    Mixin(String mixinClass, TargetedMod... targetedMods) {
        this.mixinClass = mixinClass;
        this.targetedMods = Arrays.asList(targetedMods);
        this.side = Side.BOTH;
    }

    public boolean shouldLoad(List<TargetedMod> loadedMods) {
        return (side == Side.BOTH
                || side == Side.SERVER && FMLLaunchHandler.side().isServer()
                || side == Side.CLIENT && FMLLaunchHandler.side().isClient())
                && loadedMods.containsAll(targetedMods);
    }
}

enum Side {
    BOTH,
    CLIENT,
    SERVER;
}
