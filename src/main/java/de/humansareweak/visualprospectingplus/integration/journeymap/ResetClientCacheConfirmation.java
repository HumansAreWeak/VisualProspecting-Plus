package de.humansareweak.visualprospectingplus.integration.journeymap;

import de.humansareweak.visualprospectingplus.database.ClientCache;
import journeymap.client.ui.UIManager;
import journeymap.client.ui.component.Button;
import journeymap.client.ui.component.ButtonList;
import journeymap.client.ui.component.JmUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class ResetClientCacheConfirmation extends JmUI {
    private Button confirmButton;
    private Button cancelButton;

    public ResetClientCacheConfirmation() {
        super(I18n.format("visualprospecting.resetprogress.title"));
    }

    public void func_73866_w_() {
        buttonList.clear();
        confirmButton = new Button(I18n.format("visualprospecting.resetprogress.confirm"));
        cancelButton = new Button(I18n.format("visualprospecting.resetprogress.cancel"));
        buttonList.add(confirmButton);
        buttonList.add(cancelButton);
    }

    protected void layoutButtons() {
        if (buttonList.isEmpty()) {
            func_73866_w_();
        }
        final int x = width / 2;
        final int y = height / 2;
        drawCenteredString(getFontRenderer(), I18n.format("visualprospecting.resetprogress.prompt"), x, y, 0xFFFFFF);
        final ButtonList row = new ButtonList(confirmButton, cancelButton);
        row.layoutCenteredHorizontal(x, y + 18, true, 4);
    }

    protected void func_146284_a(GuiButton guibutton) {
        if(guibutton == confirmButton) {
            ClientCache.instance.resetPlayerProgression();
            final IChatComponent confirmation = new ChatComponentTranslation("visualprospecting.resetprogress.confirmation");
            confirmation.getChatStyle().setItalic(true);
            Minecraft.getMinecraft().thePlayer.addChatMessage(confirmation);
            UIManager.getInstance().openFullscreenMap();
        }
        else if(guibutton == cancelButton) {
            UIManager.getInstance().openMapActions();
        }
    }

    protected void func_73869_a(char c, int i) {
        switch(i) {
            case 1:
                UIManager.getInstance().openMapActions();
            default:
        }
    }
}
