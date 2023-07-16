package de.humansareweak.visualprospectingplus.integration.xaeroworldmap.renderers;

import de.humansareweak.visualprospectingplus.integration.model.layers.UndergroundFluidLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import de.humansareweak.visualprospectingplus.integration.model.locations.UndergroundFluidLocation;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.rendersteps.UndergroundFluidRenderStep;

import java.util.ArrayList;
import java.util.List;

public class UndergroundFluidRenderer extends LayerRenderer {
    public static UndergroundFluidRenderer instance = new UndergroundFluidRenderer();

    public UndergroundFluidRenderer() {
        super(UndergroundFluidLayerManager.instance);
    }

    @Override
    protected List<UndergroundFluidRenderStep> generateRenderSteps(List<? extends ILocationProvider> visibleElements) {
        final List<UndergroundFluidRenderStep> renderSteps = new ArrayList<>();
        visibleElements.stream()
                .map(element -> (UndergroundFluidLocation) element)
                .forEach(location -> renderSteps.add(new UndergroundFluidRenderStep(location)));
        return renderSteps;
    }
}
