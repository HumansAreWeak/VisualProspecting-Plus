package de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints;

import de.humansareweak.visualprospectingplus.integration.model.layers.OreVeinLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.waypoints.Waypoint;

public class OreVeinWaypointManager extends WaypointManager {

    public static OreVeinWaypointManager instance = new OreVeinWaypointManager();

    public OreVeinWaypointManager() {
        super(OreVeinLayerManager.instance, WaypointType.ORE_VEINS_WAYPOINT);
    }

    @Override
    protected String getSymbol(Waypoint waypoint) {
        return "!";
    }
}
