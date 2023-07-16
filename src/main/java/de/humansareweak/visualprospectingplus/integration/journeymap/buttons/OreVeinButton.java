package de.humansareweak.visualprospectingplus.integration.journeymap.buttons;

import de.humansareweak.visualprospectingplus.integration.model.buttons.OreVeinButtonManager;

public class OreVeinButton extends LayerButton {

    public static final OreVeinButton instance = new OreVeinButton();

    public OreVeinButton() {
        super(OreVeinButtonManager.instance);
    }
}
