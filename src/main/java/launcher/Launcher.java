package launcher;

import display.Display;
import scenes.DemoScene;
import scenes.SceneManager;
import settings.Settings;

public class Launcher {

    public static void main(String[] args) {
        Settings settings = new Settings();
        Display display = new Display(settings);

        display.createDisplay();

        SceneManager sceneManager = new SceneManager();
        sceneManager.setScene(new DemoScene(settings, display, sceneManager));

        display.updateDisplay(sceneManager);

        sceneManager.getCurrentScene().close();
        display.closeDisplay();
    }

}
