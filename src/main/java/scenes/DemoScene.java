package scenes;

import game.Game;
import time.Timer;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class DemoScene extends Scene {

    private Texture texture;

    private Sound sound;

    private Timer timer;

    public DemoScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        texture = game.getAssetManager().getTexture("logo");

        sound = game.getAssetManager().getSound("coin");

        timer = new Timer(5);
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
        if (IsKeyPressed(KEY_L)) { // Letterboxing will do seemingly nothing if display aspect ratio is 16:9
            game.getSettings().setLetterboxing(!game.getSettings().isLetterboxing());
            game.getSettings().writeSettings();
        }
        if (IsKeyPressed(KEY_C)) {
            PlaySound(sound);
        }
        timer.start();
        if (timer.isDone()) {
            System.out.println("Done!");
        }
    }

    @Override
    public void render() {
        DrawRectangle(0, 0, game.getDisplay().getInternalWidth(), game.getDisplay().getInternalHeight(), DARKGRAY);
        DrawTexture(texture, 100, 200, WHITE);
        DrawRectangle(300, 400, 100, 200, RED);
    }

    @Override
    public void ui() {
        DrawText(String.format("Timer: %.2fs", timer.getElapsed()), 200, 100, 20, WHITE);
        DrawText("Scene ID: " + getId(), 5, 25, 20, WHITE);
    }

    @Override
    public void close() {

    }

}
