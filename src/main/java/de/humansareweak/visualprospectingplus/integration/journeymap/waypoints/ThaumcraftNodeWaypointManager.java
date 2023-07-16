package de.humansareweak.visualprospectingplus.integration.journeymap.waypoints;

import de.humansareweak.visualprospectingplus.integration.model.layers.ThaumcraftNodeLayerManager;

public class ThaumcraftNodeWaypointManager extends WaypointManager {

    public static final ThaumcraftNodeWaypointManager instance = new ThaumcraftNodeWaypointManager();

    public ThaumcraftNodeWaypointManager() {
        super(ThaumcraftNodeLayerManager.instance);
    }
}
