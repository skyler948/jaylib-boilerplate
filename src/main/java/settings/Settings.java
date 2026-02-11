package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import static com.raylib.Raylib.*;
import static com.raylib.Raylib.FLAG_VSYNC_HINT;
import static com.raylib.Raylib.SetTargetFPS;

public class Settings {

    private final String ROOT = ".application/";
    private final String SETTINGS_PATH = ROOT + "config.cfg";

    private File rootPath;
    private File settingsFile;

    private int width, height;
    private int fps;
    private byte monitor;
    private boolean vsync;
    private boolean letterboxing;

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
            writer.write("monitor=0\n");
            writer.write("vsync=false\n");
            writer.write("letterboxing=false");

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeSettings() {
        try {
            FileWriter writer = new FileWriter(settingsFile, false);

            writer.write("width=" + width + "\n");
            writer.write("height=" + height + "\n");
            writer.write("fps=" + fps + "\n");
            writer.write("monitor=" + monitor + "\n");
            writer.write("vsync=" + vsync + "\n");
            writer.write("letterboxing=" + letterboxing);

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Settings saved successfully!");
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
                    case "monitor":
                        monitor = Byte.parseByte(data[1]);
                        if (monitor < 0) monitor = 0;
                        break;
                    case "vsync":
                        vsync = Boolean.parseBoolean(data[1]);
                        break;
                    case "letterboxing":
                        letterboxing = Boolean.parseBoolean(data[1]);
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

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;

        if (!vsync) {
            SetTargetFPS(fps);
        }
    }

    public byte getMonitor() {
        return monitor;
    }

    public void setMonitor(byte monitor) {
        this.monitor = monitor;

        SetWindowMonitor(monitor);
    }

    public boolean isVsync() {
        return vsync;
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;

        if (vsync) {
            SetWindowState(FLAG_VSYNC_HINT);
        } else {
            ClearWindowState(FLAG_VSYNC_HINT);
            SetTargetFPS(fps);
        }
    }

    public boolean isLetterboxing() {
        return letterboxing;
    }

    public void setLetterboxing(boolean letterboxing) {
        this.letterboxing = letterboxing;
    }

}
