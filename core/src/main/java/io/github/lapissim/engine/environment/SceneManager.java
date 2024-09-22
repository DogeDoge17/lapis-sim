package io.github.lapissim.engine.environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.lapissim.Main;
import io.github.lapissim.engine.Time;

import static io.github.lapissim.Main.*;

public class SceneManager {
    public static Scene activeScene;
    private static Scene sceneQueue;
    private static  float transitionA;
    public static boolean transitioning = false;
    private static int dir = 1;

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

    public static void loadNewScene(String name, String backdrop, SceneObject[] objects, boolean fade)
    {
        loadNewScene(new Scene(name,backdrop,objects), fade);
    }

    public static void loadNewScene(Scene scene)
    {
         loadNewScene(scene, true);
    }

    public static void loadNewScene(Scene scene, boolean fade)
    {
        if(fade)
        {
            transitioning = true;
            transitionA = 0.01f;
            dir = 1;
            sceneQueue = scene;
            return;
        }
        if(activeScene != null)
            activeScene.exit();
        activeScene = scene;
        activeScene.start();
    }

    public static void drawTransition(SpriteBatch batch){

        if(transitioning){

            if(Time.gameTime == 0){
                transitioning = false;
                loadNewScene(sceneQueue, false);
                transitionA =0;
                sceneQueue = null;
            }

            transitionA += dir *3* Time.gameTime;
            if(transitionA > 1)
            {
                dir = -1;
                transitionA = 0.99f;
                loadNewScene(sceneQueue, false);
                sceneQueue = null;
            }
            if(transitionA <= 0){
                transitionA =0;
                transitioning = false;
            }
        }

        batch.setColor(0,0,0, transitionA);
        batch.draw(Main.whitesSqr, 0,0, SCREENWIDTH, SCREENHEIGHT);
        batch.setColor(1,1,1, 1);
    }
}
