package de.humansareweak.visualprospectingplus.integration.journeymap.render;

import de.humansareweak.visualprospectingplus.integration.journeymap.drawsteps.ClickableDrawStep;
import de.humansareweak.visualprospectingplus.integration.journeymap.drawsteps.OreVeinDrawStep;
import de.humansareweak.visualprospectingplus.integration.model.layers.OreVeinLayerManager;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;
import de.humansareweak.visualprospectingplus.integration.model.locations.OreVeinLocation;

import java.util.ArrayList;
import java.util.List;

public class OreVeinRenderer extends WaypointProviderLayerRenderer {

    public static final OreVeinRenderer instance = new OreVeinRenderer();

    public OreVeinRenderer() {
        super(OreVeinLayerManager.instance);
    }

    @Override
    public List<? extends ClickableDrawStep> mapLocationProviderToDrawStep(List<? extends ILocationProvider> visibleElements) {
        final List<OreVeinDrawStep> drawSteps = new ArrayList<>();
        visibleElements.stream()
                .map(element -> (OreVeinLocation) element)
                .forEach(location -> drawSteps.add(new OreVeinDrawStep(location)));
        return drawSteps;
    }
}
