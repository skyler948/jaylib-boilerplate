package launcher;

import display.Display;
import settings.Settings;

public class Launcher {

    public static void main(String[] args) {
        Settings settings = new Settings();
        Display display = new Display(settings);

        display.createDisplay();

        display.updateDisplay();

        display.closeDisplay();
    }

}
