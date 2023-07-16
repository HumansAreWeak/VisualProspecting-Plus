package de.humansareweak.visualprospectingplus.integration.xaerominimap.waypoints;

import de.humansareweak.visualprospectingplus.Tags;
import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;
import de.humansareweak.visualprospectingplus.integration.model.layers.WaypointProviderManager;
import de.humansareweak.visualprospectingplus.integration.model.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointsManager;

import java.util.Hashtable;

public class WaypointManager extends de.humansareweak.visualprospectingplus.integration.model.waypoints.WaypointManager {

    private static final Hashtable<Integer, xaero.common.minimap.waypoints.Waypoint> xWaypointTable = WaypointsManager.getCustomWaypoints(Tags.MODID);

    private WaypointWithDimension xWaypoint;
    private final WaypointType waypointType;

    public WaypointManager(WaypointProviderManager layerManager, WaypointType waypointType) {
        super(layerManager, SupportedMods.XaeroMiniMap);
        this.waypointType = waypointType;
    }

    @Override
    public void clearActiveWaypoint() {
        xWaypoint = null;
        xWaypointTable.remove(waypointType.ordinal());
    }

    public boolean hasWaypoint() {
        return xWaypoint != null;
    }

    public WaypointWithDimension getXWaypoint() {
        return xWaypoint;
    }

    @Override
    public void updateActiveWaypoint(Waypoint waypoint) {
        if (!hasWaypoint() || waypoint.blockX != xWaypoint.getX() || waypoint.blockY != xWaypoint.getY() ||
                waypoint.blockZ != xWaypoint.getZ() || waypoint.dimensionId != xWaypoint.getDimID()) {
            xWaypoint = new WaypointWithDimension(waypoint.blockX, waypoint.blockY, waypoint.blockZ, waypoint.label, getSymbol(waypoint), 15, waypoint.dimensionId);
            xWaypointTable.put(waypointType.ordinal(), xWaypoint);
        }
    }

    protected String getSymbol(Waypoint waypoint) {
        return waypoint.label.substring(0, 1);
    }
}
