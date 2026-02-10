package scenes;

import display.Display;
import settings.Settings;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class DemoScene extends Scene {

    private Texture texture;

    public DemoScene(Settings settings, Display display, SceneManager sceneManager) {
        super(settings, display, sceneManager);
    }

    @Override
    public void init() {
        texture = LoadTexture("resources/logo.png");
    }

    @Override
    public void tick() {
        if (IsKeyDown(KEY_SPACE)) {
            System.out.println("Yup, it works!");
        }
        if (IsKeyPressed(KEY_UP)) {
            sceneManager.setScene(new DemoScene(settings, display, sceneManager));
        }
    }

    @Override
    public void render() {
        DrawTexture(texture, 100, 200, WHITE);
        DrawRectangle(300, 400, 100, 200, RED);
    }

    @Override
    public void ui() {
        DrawText("Scene ID: " + getId(), 5, 25, 20, WHITE);
    }

    @Override
    public void close() {
        UnloadTexture(texture);
    }

}
