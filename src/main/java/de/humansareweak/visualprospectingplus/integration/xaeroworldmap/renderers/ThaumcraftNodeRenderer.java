package de.humansareweak.visualprospectingplus.integration.xaeroworldmap.renderers;

import de.humansareweak.visualprospectingplus.integration.model.layers.ThaumcraftNodeLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import de.humansareweak.visualprospectingplus.integration.model.locations.ThaumcraftNodeLocation;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.rendersteps.ThaumcraftNodeRenderStep;

import java.util.ArrayList;
import java.util.List;

public class ThaumcraftNodeRenderer extends InteractableLayerRenderer {
    public static ThaumcraftNodeRenderer instance = new ThaumcraftNodeRenderer();

    public ThaumcraftNodeRenderer() {
        super(ThaumcraftNodeLayerManager.instance);
    }

    @Override
    protected List<ThaumcraftNodeRenderStep> generateRenderSteps(List<? extends ILocationProvider> visibleElements) {
        final List<ThaumcraftNodeRenderStep> renderSteps = new ArrayList<>();
        visibleElements.stream()
                .map(element -> (ThaumcraftNodeLocation) element)
                .forEach(location -> renderSteps.add(new ThaumcraftNodeRenderStep(location)));
        return renderSteps;
    }
}
