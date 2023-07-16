package de.humansareweak.visualprospectingplus.integration.journeymap.render;

import de.humansareweak.visualprospectingplus.integration.journeymap.drawsteps.ClickableDrawStep;
import de.humansareweak.visualprospectingplus.integration.journeymap.drawsteps.ThaumcraftNodeDrawStep;
import de.humansareweak.visualprospectingplus.integration.model.layers.ThaumcraftNodeLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import de.humansareweak.visualprospectingplus.integration.model.locations.ThaumcraftNodeLocation;

import java.util.ArrayList;
import java.util.List;

public class ThaumcraftNodeRenderer extends WaypointProviderLayerRenderer {

    public static ThaumcraftNodeRenderer instance = new ThaumcraftNodeRenderer();

    public ThaumcraftNodeRenderer() {
        super(ThaumcraftNodeLayerManager.instance);
    }

    @Override
    public List<? extends ClickableDrawStep> mapLocationProviderToDrawStep(List<? extends ILocationProvider> visibleElements) {
        final List<ThaumcraftNodeDrawStep> drawSteps = new ArrayList<>();
        visibleElements.stream()
                .map(element -> (ThaumcraftNodeLocation) element)
                .forEach(location -> drawSteps.add(new ThaumcraftNodeDrawStep(location)));
        return drawSteps;
    }
}
