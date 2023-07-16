package de.humansareweak.visualprospectingplus.integration.xaeroworldmap.buttons;

import de.humansareweak.visualprospectingplus.integration.model.buttons.UndergroundFluidButtonManager;

public class UndergroundFluidButton extends LayerButton {

    public static final UndergroundFluidButton instance = new UndergroundFluidButton();

    public UndergroundFluidButton() {
        super(UndergroundFluidButtonManager.instance);
    }
}
