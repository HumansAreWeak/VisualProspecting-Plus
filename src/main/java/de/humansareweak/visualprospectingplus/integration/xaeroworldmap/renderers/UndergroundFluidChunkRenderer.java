package de.humansareweak.visualprospectingplus.integration.xaeroworldmap.renderers;

import de.humansareweak.visualprospectingplus.integration.model.layers.UndergroundFluidChunkLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import de.humansareweak.visualprospectingplus.integration.model.locations.UndergroundFluidChunkLocation;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.rendersteps.UndergroundFluidChunkRenderStep;

import java.util.ArrayList;
import java.util.List;

public class UndergroundFluidChunkRenderer extends LayerRenderer {
    public static UndergroundFluidChunkRenderer instance = new UndergroundFluidChunkRenderer();

    public UndergroundFluidChunkRenderer() {
        super(UndergroundFluidChunkLayerManager.instance);
    }

    @Override
    protected List<UndergroundFluidChunkRenderStep> generateRenderSteps(List<? extends ILocationProvider> visibleElements) {
        final List<UndergroundFluidChunkRenderStep> renderSteps = new ArrayList<>();
        visibleElements.stream()
                .map(element -> (UndergroundFluidChunkLocation) element)
                .forEach(location -> renderSteps.add(new UndergroundFluidChunkRenderStep(location)));
        return renderSteps;
    }
}
