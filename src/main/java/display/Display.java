package display;

import scenes.SceneManager;
import settings.Settings;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Display {

    private final int INTERNAL_WIDTH = 1280;
    private final int INTERNAL_HEIGHT = 720;

    private Settings settings;

    private float virtualRatio;

    private Camera2D worldCamera, screenCamera;

    private RenderTexture target;

    private Rectangle sourceRec, destRec;

    public Display(Settings settings) {
        this.settings = settings;

        virtualRatio = settings.getWidth() / (float) INTERNAL_WIDTH;

        worldCamera = new Camera2D()
                .zoom(1.0f);
        screenCamera = new Camera2D()
                .zoom(1.0f);
    }

    public void createDisplay() {
        InitWindow(settings.getWidth(), settings.getHeight(), "Jaylib Boilerplate");

        InitAudioDevice();

        if (settings.isVsync()) {
            SetWindowState(FLAG_VSYNC_HINT);
        } else {
            SetTargetFPS(settings.getFps());
        }

        target = LoadRenderTexture(INTERNAL_WIDTH, INTERNAL_HEIGHT);

        sourceRec = new Rectangle()
                .x(0.0f).y(0.0f)
                .width((float) target.texture().width())
                .height(-(float) target.texture().height());
        destRec = new Rectangle()
                .x(-virtualRatio).y(-virtualRatio)
                .width(settings.getWidth() + (virtualRatio * 2))
                .height(settings.getHeight() + (virtualRatio * 2));
    }

    public void updateDisplay(SceneManager sceneManager) {
        while (!WindowShouldClose()) {
            sceneManager.getCurrentScene().tick();

            BeginTextureMode(target);
                ClearBackground(BLACK);

                BeginMode2D(worldCamera);
                    sceneManager.getCurrentScene().render();
                    sceneManager.getCurrentScene().ui();

                    DrawFPS(5, 5);
                EndMode2D();
            EndTextureMode();

            BeginDrawing();
                ClearBackground(RED);

                BeginMode2D(screenCamera);
                    DrawTexturePro(target.texture(), sourceRec, destRec, new Vector2(), 0.0f, WHITE);
                EndMode2D();
            EndDrawing();
        }
    }

    public void closeDisplay() {
        CloseAudioDevice();

        UnloadRenderTexture(target);

        CloseWindow();
    }

    public Camera2D getCamera() {
        return worldCamera;
    }

}
