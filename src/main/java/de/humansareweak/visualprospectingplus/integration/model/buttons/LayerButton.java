package de.humansareweak.visualprospectingplus.integration.model.buttons;

import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;

public abstract class LayerButton {

    public LayerButton(ButtonManager manager, SupportedMods map) {
        manager.registerButton(map, this);
        // Grab lang key and texture information from manager in extended constructor
    }

    public abstract void updateState(boolean active);
}
