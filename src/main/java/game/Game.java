package game;

import display.Display;
import scenes.DemoScene;
import scenes.SceneManager;
import settings.Settings;

public class Game {

    private Settings settings;
    private Display display;

    private SceneManager sceneManager;

    public Game() {
        settings = new Settings();
        display = new Display(settings);

        display.createDisplay();

        sceneManager = new SceneManager();
        sceneManager.setScene(new DemoScene(this));

        display.updateDisplay(sceneManager);

        sceneManager.getCurrentScene().close();
        display.closeDisplay();
    }

    public Settings getSettings() {
        return settings;
    }

    public Display getDisplay() {
        return display;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

}
