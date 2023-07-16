package de.humansareweak.visualprospectingplus.integration.journeymap.buttons;

import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;
import de.humansareweak.visualprospectingplus.integration.model.buttons.*;
import journeymap.client.ui.theme.ThemeToggle;

public class LayerButton extends de.humansareweak.visualprospectingplus.integration.model.buttons.LayerButton {

    private ThemeToggle button;
    private final ButtonManager manager;

    private boolean isActive = false;

    public LayerButton(ButtonManager manager) {
        super(manager, SupportedMods.JourneyMap);
        this.manager = manager;
    }

    @Override
    public void updateState(boolean active) {
        isActive = active;
        if(button != null) {
            button.setToggled(active, false);
        }
    }

    public void setButton(ThemeToggle button) {
        this.button = button;
    }

    public String getButtonTextKey() {
        return manager.getButtonTextKey();
    }

    public String getIconName() {
        return manager.getIconName();
    }

    public boolean isActive() {
        return isActive;
    }

    public void toggle() {
        manager.toggle();
    }
}
