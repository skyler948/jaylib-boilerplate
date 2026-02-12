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
    private float internalRatio, displayRatio;

    private Camera2D worldCamera, screenCamera;

    private RenderTexture target;

    private Rectangle sourceRec, destRec;

    private Vector2 origin;

    public Display(Settings settings) {
        this.settings = settings;

        virtualRatio = settings.getWidth() / (float) INTERNAL_WIDTH;
        internalRatio = (float) INTERNAL_WIDTH / INTERNAL_HEIGHT;
        displayRatio = (float) settings.getWidth() / settings.getHeight();

        worldCamera = new Camera2D()
                .zoom(1.0f);
        screenCamera = new Camera2D()
                .zoom(1.0f);
    }

    public void createDisplay() {
        InitWindow(settings.getWidth(), settings.getHeight(), "Jaylib Boilerplate");

        InitAudioDevice();

        settings.setVsync(settings.isVsync());
        settings.setMonitor(settings.getMonitor());

        target = LoadRenderTexture(INTERNAL_WIDTH, INTERNAL_HEIGHT);

        setDisplayDimensions();
    }

    public void setDisplayDimensions() {
        origin = new Vector2();

        sourceRec = new Rectangle()
                .x(0.0f).y(0.0f)
                .width((float) target.texture().width())
                .height(-(float) target.texture().height());

        if (!settings.isLetterboxing() || displayRatio == internalRatio) {
            destRec = new Rectangle()
                    .x(-virtualRatio).y(-virtualRatio)
                    .width(settings.getWidth() + (virtualRatio * 2))
                    .height(settings.getHeight() + (virtualRatio * 2));
        } else {
            if (displayRatio < internalRatio) {
                long letterboxHeight = ((long) settings.getWidth() * INTERNAL_HEIGHT) / INTERNAL_WIDTH;

                destRec = new Rectangle()
                        .x(-virtualRatio).y(-virtualRatio)
                        .width(settings.getWidth() + (virtualRatio * 2))
                        .height(letterboxHeight + (virtualRatio * 2));

                origin.y((letterboxHeight / 2.0f) - (settings.getHeight() / 2.0f));
            } else if (displayRatio > internalRatio) {
                long letterboxWidth = ((long) INTERNAL_WIDTH * settings.getHeight()) / INTERNAL_HEIGHT;

                destRec = new Rectangle()
                        .x(-virtualRatio).y(-virtualRatio)
                        .width(letterboxWidth + (virtualRatio * 2))
                        .height(settings.getHeight() + (virtualRatio * 2));

                origin.x((letterboxWidth / 2.0f) - (settings.getWidth() / 2.0f));
            }
        }
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
                ClearBackground(BLACK);

                BeginMode2D(screenCamera);
                    DrawTexturePro(target.texture(), sourceRec, destRec, origin, 0.0f, WHITE);
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

    public int getInternalWidth() {
        return INTERNAL_WIDTH;
    }

    public int getInternalHeight() {
        return INTERNAL_HEIGHT;
    }

}
