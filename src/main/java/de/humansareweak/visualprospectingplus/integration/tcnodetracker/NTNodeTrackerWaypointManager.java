package de.humansareweak.visualprospectingplus.integration.tcnodetracker;

import com.dyonovan.tcnodetracker.TCNodeTracker;
import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;
import de.humansareweak.visualprospectingplus.integration.model.layers.ThaumcraftNodeLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.waypoints.Waypoint;

public class NTNodeTrackerWaypointManager extends de.humansareweak.visualprospectingplus.integration.model.waypoints.WaypointManager {

    public NTNodeTrackerWaypointManager() {
        super(ThaumcraftNodeLayerManager.instance, SupportedMods.TCNodeTracker);
    }

    @Override
    public void clearActiveWaypoint() {
        TCNodeTracker.yMarker = -1;
    }

    @Override
    public void updateActiveWaypoint(Waypoint waypoint) {
        TCNodeTracker.xMarker = waypoint.blockX;
        TCNodeTracker.yMarker = waypoint.blockY;
        TCNodeTracker.zMarker = waypoint.blockZ;
    }
}
