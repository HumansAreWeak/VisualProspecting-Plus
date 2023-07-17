package de.humansareweak.visualprospectingplus.integration.voxelmap;

import java.lang.reflect.Method;

import de.humansareweak.visualprospectingplus.VPP;
import com.thevoxelbox.voxelmap.interfaces.IWaypointManager;

public class IWaypointManagerReflection {

	private static Method getCurrentSubworldDescriptor;

	public static String getCurrentSubworldDescriptor(IWaypointManager obj, boolean arg) {
		try {
			return (String) getCurrentSubworldDescriptor.invoke(obj, arg);
		} catch (Exception e) {
			VPP.error("Could not invoke IWaypointManager#if. If it failed due to a NullPointerException, look for an error message starting with \"Getting the method IWaypointManager#if failed\" further up.");
			e.printStackTrace();
		}
		return "";
	}

	static {
		try {
			getCurrentSubworldDescriptor = IWaypointManager.class.getMethod("if", boolean.class);
		} catch (Exception e) {
			VPP.error("Getting the method IWaypointManager#if failed, any calls to IWaypointManagerReflection#getCurrentSubworldDescriptor will return an empty String.");
			e.printStackTrace();
		}
	}

}
