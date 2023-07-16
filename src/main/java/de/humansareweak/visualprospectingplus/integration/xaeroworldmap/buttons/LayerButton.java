package de.humansareweak.visualprospectingplus.integration.xaeroworldmap.buttons;

import de.humansareweak.visualprospectingplus.integration.model.SupportedMods;
import de.humansareweak.visualprospectingplus.integration.model.buttons.ButtonManager;
import net.minecraft.util.ResourceLocation;

public class LayerButton extends de.humansareweak.visualprospectingplus.integration.model.buttons.LayerButton {

    private final ButtonManager manager;
    private SizedGuiTexturedButton button;

    public final ResourceLocation textureLocation;

    public LayerButton(ButtonManager manager) {
        super(manager, SupportedMods.XaeroWorldMap);
        this.manager = manager;
        textureLocation = new ResourceLocation("xaeroworldmap", "textures/" + getIconName() + ".png");
    }

    @Override
    public void updateState(boolean active) {
        if (button != null) {
            button.setActive(active);
        }
    }

    public void setButton(SizedGuiTexturedButton button) {
        this.button = button;
        updateState(manager.isActive());
    }

    public String getButtonTextKey() {
        return manager.getButtonTextKey();
    }

    public String getIconName() {
        return manager.getIconName();
    }

    public void toggle() {
        manager.toggle();
    }
}
