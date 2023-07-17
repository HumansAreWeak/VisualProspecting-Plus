package de.humansareweak.visualprospectingplus.integration.journeymap.buttons;

import de.humansareweak.visualprospectingplus.integration.model.buttons.ThaumcraftNodeButtonManager;

public class ThaumcraftNodeButton extends LayerButton {

    public static final ThaumcraftNodeButton instance = new ThaumcraftNodeButton();

    public ThaumcraftNodeButton() {
        super(ThaumcraftNodeButtonManager.instance);
    }
}