package de.humansareweak.visualprospectingplus.integration.journeymap;

import de.humansareweak.visualprospectingplus.VPP;
import journeymap.client.render.map.GridRenderer;
import journeymap.client.ui.fullscreen.Fullscreen;

import java.lang.reflect.Field;

public class Reflection {

    private static Field gridRenderer;

    static {
        try {
            gridRenderer = Fullscreen.class.getDeclaredField("gridRenderer");
            gridRenderer.setAccessible(true);
        }
        catch (Exception e) {
            VPP.error("Failed to access private fields in JourneyMap!");
            e.printStackTrace();
        }
    }

    public static GridRenderer getJourneyMapGridRenderer() {
        try {
            return (GridRenderer) gridRenderer.get(null);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
