package de.humansareweak.visualprospectingplus.integration.model.locations;

import de.humansareweak.visualprospectingplus.integration.model.waypoints.Waypoint;

public interface IWaypointAndLocationProvider extends ILocationProvider {

    Waypoint toWaypoint();

    boolean isActiveAsWaypoint();

    void onWaypointCleared();

    void onWaypointUpdated(Waypoint waypoint);
}
