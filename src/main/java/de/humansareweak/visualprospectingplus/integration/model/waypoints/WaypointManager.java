package de.humansareweak.visualprospectingplus.integration.model.waypoints;

import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;
import de.humansareweak.visualprospectingplus.integration.model.layers.WaypointProviderManager;

public abstract class WaypointManager {

    public WaypointManager(WaypointProviderManager layerManager, SupportedMods map) {
        layerManager.registerWaypointManager(map, this);
    }

    public abstract void clearActiveWaypoint();

    public abstract void updateActiveWaypoint(Waypoint waypoint);
}
