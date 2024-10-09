package io.github.lapissim.game.prefabs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.Main;
import io.github.lapissim.engine.environment.Scene;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.engine.environment.SceneObject;
import io.github.lapissim.engine.render.Font;
import io.github.lapissim.engine.render.TextRenderer;

import java.lang.reflect.InvocationTargetException;

public class SceneDoorway<T extends Scene> extends Doorway {

    //public Scene destination;



    public Class<T> destination;
    //public Supplier<T> destination;

    public SceneDoorway(Class<T> dest, String text, int x, int y, int width, int height) {
       // super("Doorway" + text.trim().replace(" ", ""), text, "doorway", x, y);
        super(text, x, y, width,height);
        this.destination = dest;
    }

    @Override
    public void onClick()  {

        if(speakerHover)
            return;

        try {
            SceneManager.loadNewScene(destination.getDeclaredConstructor().newInstance());
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        super.onClick();
    }


}
