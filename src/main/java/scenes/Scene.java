package scenes;

import display.Display;
import settings.Settings;

public abstract class Scene {

    private static long currentId = 0L;

    private final long id;

    protected Settings settings;
    protected Display display;
    protected SceneManager sceneManager;

    public Scene(Settings settings, Display display, SceneManager sceneManager) {
        this.settings = settings;
        this.display = display;
        this.sceneManager = sceneManager;
        this.id = currentId++;
    }

    public abstract void init();

    public abstract void tick();

    public abstract void render();

    public abstract void ui();

    public abstract void close();

    public long getId() {
        return id;
    }

}
