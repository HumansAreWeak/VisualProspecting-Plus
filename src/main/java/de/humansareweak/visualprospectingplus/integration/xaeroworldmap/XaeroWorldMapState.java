package de.humansareweak.visualprospectingplus.integration.xaeroworldmap;

import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.buttons.LayerButton;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.buttons.OreVeinButton;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.buttons.ThaumcraftNodeButton;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.buttons.UndergroundFluidButton;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.renderers.*;
import de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints.OreVeinWaypointManager;
import de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints.ThaumcraftNodeWaypointManager;
import de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints.WaypointManager;

import java.util.ArrayList;
import java.util.List;

import static de.humansareweak.visualprospectingplus.Utils.isTCNodeTrackerInstalled;

public class XaeroWorldMapState {
    public static XaeroWorldMapState instance = new XaeroWorldMapState();

    public final List<LayerButton> buttons = new ArrayList<>();
    public final List<LayerRenderer> renderers = new ArrayList<>();

    public XaeroWorldMapState() {
        buttons.add(OreVeinButton.instance);
        renderers.add(OreVeinRenderer.instance);

        buttons.add(UndergroundFluidButton.instance);
        renderers.add(UndergroundFluidChunkRenderer.instance);
        renderers.add(UndergroundFluidRenderer.instance);

        if (isTCNodeTrackerInstalled()) {
            buttons.add(ThaumcraftNodeButton.instance);
            renderers.add(ThaumcraftNodeRenderer.instance);
        }
    }
}
