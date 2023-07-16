package de.humansareweak.visualprospectingplus.integration.journeymap.waypoints;

import de.humansareweak.visualprospectingplus.integration.model.layers.OreVeinLayerManager;

public class OreVeinWaypointManager extends WaypointManager {

    public static final OreVeinWaypointManager instance = new OreVeinWaypointManager();

    public OreVeinWaypointManager() {
        super(OreVeinLayerManager.instance);
    }
}
