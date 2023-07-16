package de.humansareweak.visualprospectingplus.integration.xaeroworldmap.renderers;

import de.humansareweak.visualprospectingplus.integration.model.layers.WaypointProviderManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.rendersteps.InteractableRenderStep;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.rendersteps.RenderStep;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;

public abstract class InteractableLayerRenderer extends LayerRenderer {
    private double mouseXForRender;
    private double mouseYForRender;
    protected WaypointProviderManager manager;
    protected InteractableRenderStep hovered;

    public InteractableLayerRenderer(WaypointProviderManager manager) {
        super(manager);
        this.manager = manager;
        hovered = null;
    }

    @Override
    protected abstract List<? extends InteractableRenderStep> generateRenderSteps(List<? extends ILocationProvider> visibleElements);

    public void updateHovered(double mouseX, double mouseY, double cameraX, double cameraZ, double scale) {
        mouseXForRender = mouseX - cameraX;
        mouseYForRender = mouseY - cameraZ;
        for (RenderStep step : renderStepsReversed) {
            if (step instanceof InteractableRenderStep && ((InteractableRenderStep) step).isMouseOver(mouseXForRender, mouseYForRender, scale)) {
                hovered = (InteractableRenderStep) step;
                return;
            }
        }
        hovered = null;
    }

    public void drawTooltip(GuiScreen gui, double scale, int scaleAdj) {
        if (hovered != null) {
            hovered.drawTooltip(gui, mouseXForRender, mouseYForRender, scale, scaleAdj);
        }
    }

    public void doActionKeyPress() {
        if (manager.isLayerActive() && hovered != null) {
            hovered.onActionButton();
            manager.forceRefresh();
        }
    }

    public void doDoubleClick() {
        if (hovered != null) {
            if (hovered.getLocationProvider().isActiveAsWaypoint()) {
                manager.clearActiveWaypoint();
            } else {
                manager.setActiveWaypoint(hovered.getLocationProvider().toWaypoint());
            }
        }
    }
}
