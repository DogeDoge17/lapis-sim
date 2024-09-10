package io.github.lapissim.engine.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SceneManager {
    public static Scene activeScene;


    public static void setActiveScene(Scene scene){
        activeScene = scene;
    }

    /**
     *
     * @param batch requires the sprite batch to have already started.
     */
    public static void drawActive(SpriteBatch batch)
    {
        if(activeScene != null)
            activeScene.draw(batch);
    }

    public static void updateActive()
    {
        if(activeScene != null)
            activeScene.update();
    }

    public static void loadNewScene(String name, String backdrop, SceneObject[] objects)
    {
        loadNewScene(new Scene(name,backdrop,objects));
    }

    public static void loadNewScene(Scene scene)
    {
        activeScene = scene;
        activeScene.start();
    }
}
