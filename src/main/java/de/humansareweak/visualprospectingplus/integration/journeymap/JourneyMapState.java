package de.humansareweak.visualprospectingplus.integration.journeymap;

import de.humansareweak.visualprospectingplus.integration.journeymap.buttons.LayerButton;
import de.humansareweak.visualprospectingplus.integration.journeymap.buttons.OreVeinButton;
import de.humansareweak.visualprospectingplus.integration.journeymap.buttons.ThaumcraftNodeButton;
import de.humansareweak.visualprospectingplus.integration.journeymap.buttons.UndergroundFluidButton;
import de.humansareweak.visualprospectingplus.integration.journeymap.render.*;
import de.humansareweak.visualprospectingplus.integration.journeymap.waypoints.OreVeinWaypointManager;
import de.humansareweak.visualprospectingplus.integration.journeymap.waypoints.ThaumcraftNodeWaypointManager;
import de.humansareweak.visualprospectingplus.integration.journeymap.waypoints.WaypointManager;
import journeymap.client.render.map.GridRenderer;

import java.util.ArrayList;
import java.util.List;

import static de.humansareweak.visualprospectingplus.Utils.isTCNodeTrackerInstalled;
import static de.humansareweak.visualprospectingplus.integration.journeymap.Reflection.getJourneyMapGridRenderer;

public class JourneyMapState {

    public static JourneyMapState instance = new JourneyMapState();

    public final List<LayerButton> buttons = new ArrayList<>();
    public final List<LayerRenderer> renderers = new ArrayList<>();
    public final List<WaypointManager> waypointManagers = new ArrayList<>();

    public JourneyMapState() {
        if(isTCNodeTrackerInstalled()) {
            buttons.add(ThaumcraftNodeButton.instance);
            renderers.add(ThaumcraftNodeRenderer.instance);
            waypointManagers.add(ThaumcraftNodeWaypointManager.instance);
        }

        buttons.add(UndergroundFluidButton.instance);
        renderers.add(UndergroundFluidRenderer.instance);
        renderers.add(UndergroundFluidChunkRenderer.instance);

        buttons.add(OreVeinButton.instance);
        renderers.add(OreVeinRenderer.instance);
        waypointManagers.add(OreVeinWaypointManager.instance);
    }

    public void openJourneyMapAt(int blockX, int blockZ) {
        final GridRenderer gridRenderer = getJourneyMapGridRenderer();
        assert gridRenderer != null;

        gridRenderer.center(gridRenderer.getMapType(), blockX, blockZ, gridRenderer.getZoom());
    }

    public void openJourneyMapAt(int blockX, int blockZ, int zoom) {
        final GridRenderer gridRenderer = getJourneyMapGridRenderer();
        assert gridRenderer != null;

        gridRenderer.center(gridRenderer.getMapType(), blockX, blockZ, zoom);
    }
}
