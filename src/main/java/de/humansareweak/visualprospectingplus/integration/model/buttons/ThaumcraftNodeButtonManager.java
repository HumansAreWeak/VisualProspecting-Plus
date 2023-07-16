package de.humansareweak.visualprospectingplus.integration.model.buttons;

public class ThaumcraftNodeButtonManager extends ButtonManager {

    public static final ThaumcraftNodeButtonManager instance = new ThaumcraftNodeButtonManager();

    public ThaumcraftNodeButtonManager() {
        super("visualprospecting.button.nodes", "nodes");
    }
}
