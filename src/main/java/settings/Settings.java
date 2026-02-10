package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Settings {

    private final String ROOT = ".application/";
    private final String SETTINGS_PATH = ROOT + "config.cfg";

    private File rootPath;
    private File settingsFile;

    private int width, height;
    private int fps;
    private boolean vsync;

    public Settings() {
        rootPath = new File(ROOT);
        settingsFile = new File(SETTINGS_PATH);

        checkDirectory();

        readSettings();
    }

    private void checkDirectory() {
        try {
            if (rootPath.mkdir()) {
                System.out.println(".application/ directory created.");
            }

            if (settingsFile.createNewFile()) {
                System.out.println("config.cfg created.");

                createDefaultSettings();
            } else {
                System.out.println("config.cfg found.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createDefaultSettings() {
        try {
            FileWriter writer = new FileWriter(settingsFile);

            writer.write("width=1280\n");
            writer.write("height=720\n");
            writer.write("fps=240\n");
            writer.write("vsync=false");

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readSettings() {
        try {
            Scanner scanner = new Scanner(settingsFile);

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split("=");

                data[0] = data[0].trim().toLowerCase(Locale.ROOT);
                data[1] = data[1].trim();

                switch (data[0]) {
                    case "width":
                        width = Integer.parseInt(data[1]);
                        break;
                    case "height":
                        height = Integer.parseInt(data[1]);
                        break;
                    case "fps":
                        fps = Integer.parseInt(data[1]);
                        break;
                    case "vsync":
                        vsync = Boolean.parseBoolean(data[1]);
                        break;
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFps() {
        return fps;
    }

    public boolean isVsync() {
        return vsync;
    }

}
