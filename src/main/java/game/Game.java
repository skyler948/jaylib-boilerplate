package game;

import assets.AssetManager;
import display.Display;
import scenes.DemoScene;
import scenes.SceneManager;
import settings.Settings;

public class Game {

    private Settings settings;
    private Display display;

    private SceneManager sceneManager;

    private AssetManager assetManager;

    public Game() {
        settings = new Settings();
        display = new Display(settings);

        display.createDisplay();

        assetManager = new AssetManager();

        sceneManager = new SceneManager();
        sceneManager.setScene(new DemoScene(this));

        display.updateDisplay(sceneManager);

        sceneManager.getCurrentScene().close();
        assetManager.unloadAssets();
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

    public AssetManager getAssetManager() {
        return assetManager;
    }

}
