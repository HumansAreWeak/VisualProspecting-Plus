package de.humansareweak.visualprospectingplus.integration.xaeroworldmap.renderers;

import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;
import de.humansareweak.visualprospectingplus.integration.model.layers.LayerManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.rendersteps.RenderStep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class LayerRenderer extends de.humansareweak.visualprospectingplus.integration.model.layers.LayerRenderer {

    private final LayerManager manager;
    protected List<RenderStep> renderSteps = new ArrayList<>();
    protected List<RenderStep> renderStepsReversed = new ArrayList<>();

    public LayerRenderer(LayerManager manager) {
        super(manager, SupportedMods.XaeroWorldMap);
        this.manager = manager;
    }

    public List<RenderStep> getRenderSteps() {
        return renderSteps;
    }

    public List<RenderStep> getRenderStepsReversed() {
        return renderStepsReversed;
    }

    public boolean isLayerActive() {
        return manager.isLayerActive();
    }

    @Override
    public void updateVisibleElements(List<? extends ILocationProvider> visibleElements) {
        //noinspection unchecked
        renderSteps = (List<RenderStep>) generateRenderSteps(visibleElements);
        renderStepsReversed = new ArrayList<>(renderSteps);
        Collections.reverse(renderStepsReversed);
    }

    protected abstract List<? extends RenderStep> generateRenderSteps(List<? extends ILocationProvider> visibleElements);
}
