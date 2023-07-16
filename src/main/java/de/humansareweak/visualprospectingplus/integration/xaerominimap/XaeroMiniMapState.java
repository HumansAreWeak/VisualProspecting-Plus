package de.humansareweak.visualprospectingplus.integration.xaerominimap;

import de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints.OreVeinWaypointManager;
import de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints.ThaumcraftNodeWaypointManager;
import de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints.WaypointManager;

import java.util.ArrayList;
import java.util.List;

import static de.humansareweak.visualprospectingplus.Utils.isTCNodeTrackerInstalled;

public class XaeroMiniMapState {

    public static XaeroMiniMapState instance = new XaeroMiniMapState();

    public final List<WaypointManager> waypointManagers = new ArrayList<>();

    public XaeroMiniMapState() {
        waypointManagers.add(OreVeinWaypointManager.instance);

        if (isTCNodeTrackerInstalled()) {
            waypointManagers.add(ThaumcraftNodeWaypointManager.instance);
        }
    }
}
