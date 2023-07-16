package de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints;

import de.humansareweak.visualprospectingplus.integration.model.layers.ThaumcraftNodeLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.waypoints.Waypoint;

public class ThaumcraftNodeWaypointManager extends WaypointManager {

    public static ThaumcraftNodeWaypointManager instance = new ThaumcraftNodeWaypointManager();

    public ThaumcraftNodeWaypointManager() {
        super(ThaumcraftNodeLayerManager.instance, WaypointType.TC_NODES_WAYPOINT);
    }

    @Override
    protected String getSymbol(Waypoint waypoint) {
        return "@";
    }
}
