package de.humansareweak.visualprospectingplus.integration.xaeroworldmap.rendersteps;

import de.humansareweak.visualprospectingplus.integration.model.locations.IWaypointAndLocationProvider;
import net.minecraft.client.gui.GuiScreen;

public interface InteractableRenderStep extends RenderStep {

    boolean isMouseOver(double mouseX, double mouseY, double scale);

    void drawTooltip(GuiScreen gui, double mouseX, double mouseY, double scale, int scaleAdj);

    void onActionButton();

    IWaypointAndLocationProvider getLocationProvider();

}
