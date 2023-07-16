package de.humansareweak.visualprospectingplus.integration.model;

import de.humansareweak.visualprospectingplus.integration.model.buttons.*;
import de.humansareweak.visualprospectingplus.integration.model.layers.*;
import de.humansareweak.visualprospectingplus.integration.tcnodetracker.NTNodeTrackerWaypointManager;

import java.util.ArrayList;
import java.util.List;

import static de.humansareweak.visualprospectingplus.Utils.isTCNodeTrackerInstalled;

public class MapState {
    public static final MapState instance = new MapState();

    public final List<ButtonManager> buttons = new ArrayList<>();
    public final List<LayerManager> layers = new ArrayList<>();

    public MapState() {
        if(isTCNodeTrackerInstalled()) {
            buttons.add(ThaumcraftNodeButtonManager.instance);
            layers.add(ThaumcraftNodeLayerManager.instance);
            new NTNodeTrackerWaypointManager();
        }

        buttons.add(UndergroundFluidButtonManager.instance);
        layers.add(UndergroundFluidLayerManager.instance);
        layers.add(UndergroundFluidChunkLayerManager.instance);

        buttons.add(OreVeinButtonManager.instance);
        layers.add(OreVeinLayerManager.instance);
    }
}
