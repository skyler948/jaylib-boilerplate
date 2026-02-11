package assets;

import java.util.HashMap;

import static com.raylib.Raylib.*;

public class AssetManager {

    private final Texture invalid;

    private HashMap<String, Texture> textures;
    private HashMap<String, Sound> sounds;
    private HashMap<String, Music> music;
    private HashMap<String[], Shader> shaders;
    private HashMap<String, Font> fonts;

    public AssetManager() {
        invalid = LoadTexture("resources/invalid.png");

        textures = new HashMap<>();
        sounds = new HashMap<>();
        music = new HashMap<>();
        shaders = new HashMap<>();
        fonts = new HashMap<>();
    }

    public Texture getTexture(String path) {
        return textures.get(path);
    }

    public Texture loadTexture(String path) {
        textures.put(path, LoadTexture(path));

        // TODO: Figure out a more robust way of finding out texture invalidity
        // Not sure if a valid input image can have a mipmap value of 0
        // So far, no. But IDK!
        if (textures.get(path).mipmaps() == 0) {
            return invalid;
        }
        return getTexture(path);
    }

    public Sound getSound(String path) {
        return sounds.get(path);
    }

    public Sound loadSound(String path) {
        sounds.put(path, LoadSound(path));

        return getSound(path);
    }

    public Music getMusic(String path) {
        return music.get(path);
    }

    public Music loadMusic(String path) {
        music.put(path, LoadMusicStream(path));

        return getMusic(path);
    }

    public Shader getShader(String vs, String fs) {
        return shaders.get(new String[]{vs, fs});
    }

    public Shader loadShader(String vs, String fs) {
        shaders.put(new String[]{vs, fs}, LoadShader(vs, fs));

        return getShader(vs, fs);
    }

    public Font getFont(String path) {
        return fonts.get(path);
    }

    public Font loadFont(String path) {
        fonts.put(path, LoadFont(path));

        return getFont(path);
    }

    public void unloadAssets() {
        UnloadTexture(invalid);

        for (Texture texture : textures.values()) {
            UnloadTexture(texture);
        }
        for (Sound sound : sounds.values()) {
            UnloadSound(sound);
        }
        for (Music m : music.values()) {
            UnloadMusicStream(m);
        }
        for (Shader shader : shaders.values()) {
            UnloadShader(shader);
        }
        for (Font font : fonts.values()) {
            UnloadFont(font);
        }

        textures.clear();
        sounds.clear();
        music.clear();
        shaders.clear();
        fonts.clear();
    }

}
