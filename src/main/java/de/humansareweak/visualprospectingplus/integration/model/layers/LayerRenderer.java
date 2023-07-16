package de.humansareweak.visualprospectingplus.integration.model.layers;

import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;
import de.humansareweak.visualprospectingplus.integration.model.locations.ILocationProvider;

import java.util.List;

public abstract class LayerRenderer {

    public LayerRenderer(LayerManager manager, SupportedMods map) {
        manager.registerLayerRenderer(map, this);
    }

    public abstract void updateVisibleElements(List<? extends ILocationProvider> visibleElements);
}
