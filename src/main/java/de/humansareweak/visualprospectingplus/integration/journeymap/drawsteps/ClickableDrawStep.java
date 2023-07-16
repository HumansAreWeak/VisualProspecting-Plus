package de.humansareweak.visualprospectingplus.integration.journeymap.drawsteps;

import de.humansareweak.visualprospectingplus.integration.model.locations.IWaypointAndLocationProvider;
import journeymap.client.render.draw.DrawStep;
import net.minecraft.client.gui.FontRenderer;

import java.util.List;

public interface ClickableDrawStep extends DrawStep {

    List<String> getTooltip();

    void drawTooltip(FontRenderer fontRenderer, int mouseX, int mouseY, int displayWidth, int displayHeight);

    boolean isMouseOver(int mouseX, int mouseY);

    void onActionKeyPressed();

    IWaypointAndLocationProvider getLocationProvider();
}
