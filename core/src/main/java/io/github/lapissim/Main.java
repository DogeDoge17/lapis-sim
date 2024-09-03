package io.github.lapissim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.engine.environment.SceneObject;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.game.scenes.FunlandArcade;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {

    public static final Integer SCREENWIDTH = 1280;
    public static final Integer SCREENHEIGHT = 720;

    private SpriteBatch batch;
    //private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //image = new Texture("libgdx.png");
        DialogueManager.beginDialogue("test/test");
        SceneManager.loadNewScene(new FunlandArcade(new SceneObject[]{new Speaker("lapis", "Lapis", "neutral", Speaker.RightAnchor, 109),new Speaker("steven", "Steven", "happy", 0, 109)}));


    }

    @Override
    public void render() {
        update();
        realRender();
    }

    public void update() {
        SceneManager.updateActive();
    }

    public void realRender() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        SceneManager.drawActive(batch);
        //batch.draw(image, 140, 210, image.getWidth(), image.getHeight());

        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        // image.dispose();
    }
}
