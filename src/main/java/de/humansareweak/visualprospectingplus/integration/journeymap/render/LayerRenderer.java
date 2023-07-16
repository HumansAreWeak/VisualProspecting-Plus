package de.humansareweak.visualprospectingplus.integration.journeymap.render;

import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;
import de.humansareweak.visualprospectingplus.integration.model.layers.LayerManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import journeymap.client.render.draw.DrawStep;

import java.util.ArrayList;
import java.util.List;

public abstract class LayerRenderer extends de.humansareweak.visualprospectingplus.integration.model.layers.LayerRenderer {

    private final LayerManager manager;

    protected List<DrawStep> drawSteps = new ArrayList<>();

    public LayerRenderer(LayerManager manager) {
        super(manager, SupportedMods.JourneyMap);
        this.manager = manager;
    }

    public boolean isLayerActive() {
        return manager.isLayerActive();
    }

    public List<? extends DrawStep> getDrawStepsCachedForInteraction() {
        return drawSteps;
    }

    public List<? extends DrawStep> getDrawStepsCachedForRendering() {
        return drawSteps;
    }

    @Override
    public void updateVisibleElements(List<? extends ILocationProvider> visibleElements) {
        drawSteps = (List<DrawStep>) mapLocationProviderToDrawStep(visibleElements);
    }

    protected abstract List<? extends DrawStep> mapLocationProviderToDrawStep(List<? extends ILocationProvider> visibleElements);
}
