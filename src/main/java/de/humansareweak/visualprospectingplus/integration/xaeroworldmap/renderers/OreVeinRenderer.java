package de.humansareweak.visualprospectingplus.integration.xaeroworldmap.renderers;

import de.humansareweak.visualprospectingplus.integration.model.layers.OreVeinLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import de.humansareweak.visualprospectingplus.integration.model.locations.OreVeinLocation;
import de.humansareweak.visualprospectingplus.integration.xaeroworldmap.rendersteps.OreVeinRenderStep;

import java.util.ArrayList;
import java.util.List;

public class OreVeinRenderer extends InteractableLayerRenderer {
    public static OreVeinRenderer instance = new OreVeinRenderer();

    public OreVeinRenderer() {
        super(OreVeinLayerManager.instance);
    }

    @Override
    protected List<OreVeinRenderStep> generateRenderSteps(List<? extends ILocationProvider> visibleElements) {
        final List<OreVeinRenderStep> renderSteps = new ArrayList<>();
        visibleElements.stream()
                .map(element -> (OreVeinLocation) element)
                .forEach(location -> renderSteps.add(new OreVeinRenderStep(location)));
        return renderSteps;
    }
}
