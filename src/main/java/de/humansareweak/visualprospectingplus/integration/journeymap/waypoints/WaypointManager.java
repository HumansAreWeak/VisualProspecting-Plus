package de.humansareweak.visualprospectingplus.integration.journeymap.waypoints;

import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;
import de.humansareweak.visualprospectingplus.integration.model.layers.WaypointProviderManager;
import de.humansareweak.visualprospectingplus.integration.model.waypoints.Waypoint;

import java.awt.*;

public class WaypointManager extends de.humansareweak.visualprospectingplus.integration.model.waypoints.WaypointManager {

    private journeymap.client.model.Waypoint jmWaypoint;

    public WaypointManager(WaypointProviderManager layerManager) {
        super(layerManager, SupportedMods.JourneyMap);
    }

    @Override
    public void clearActiveWaypoint() {
        jmWaypoint = null;
    }

    public boolean hasWaypoint() {
        return jmWaypoint != null;
    }

    public journeymap.client.model.Waypoint getJmWaypoint() {
        return jmWaypoint;
    }

    @Override
    public void updateActiveWaypoint(Waypoint waypoint) {
        if(hasWaypoint() == false
                || waypoint.blockX != jmWaypoint.getX()
                || waypoint.blockY != jmWaypoint.getY()
                || waypoint.blockZ != jmWaypoint.getZ()
                || jmWaypoint.getDimensions().contains(waypoint.dimensionId) == false) {
            jmWaypoint = new journeymap.client.model.Waypoint(
                    waypoint.label,
                    waypoint.blockX, waypoint.blockY, waypoint.blockZ,
                    new Color(waypoint.color),
                    journeymap.client.model.Waypoint.Type.Normal,
                    waypoint.dimensionId);
        }
    }
}
