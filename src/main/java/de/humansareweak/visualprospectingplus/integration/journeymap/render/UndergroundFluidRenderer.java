package de.humansareweak.visualprospectingplus.integration.journeymap.render;

import de.humansareweak.visualprospectingplus.integration.journeymap.drawsteps.UndergroundFluidDrawStep;
import de.humansareweak.visualprospectingplus.integration.model.layers.UndergroundFluidLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import de.humansareweak.visualprospectingplus.integration.model.locations.UndergroundFluidLocation;
import journeymap.client.render.draw.DrawStep;

import java.util.ArrayList;
import java.util.List;

public class UndergroundFluidRenderer extends LayerRenderer {

    public static final UndergroundFluidRenderer instance = new UndergroundFluidRenderer();

    public UndergroundFluidRenderer() {
        super(UndergroundFluidLayerManager.instance);
    }

    @Override
    public List<? extends DrawStep> mapLocationProviderToDrawStep(List<? extends ILocationProvider> visibleElements) {
        final List<UndergroundFluidDrawStep> drawSteps = new ArrayList<>();
        visibleElements.stream()
                .map(element -> (UndergroundFluidLocation) element)
                .forEach(location -> drawSteps.add(new UndergroundFluidDrawStep(location)));
        return drawSteps;
    }
}
