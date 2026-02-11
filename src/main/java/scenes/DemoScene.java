package scenes;

import game.Game;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class DemoScene extends Scene {

    private Texture texture;

    public DemoScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        texture = game.getAssetManager().loadTexture("resources/logo.png");
    }

    @Override
    public void tick() {
        if (IsKeyDown(KEY_SPACE)) {
            System.out.println("Yup, it works!");
        }
        if (IsKeyPressed(KEY_UP)) {
            game.getSceneManager().setScene(new DemoScene(game));
        }
        if (IsKeyPressed(KEY_DOWN)) {
            game.getSettings().setVsync(!game.getSettings().isVsync());
            game.getSettings().writeSettings();
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

    }

}
