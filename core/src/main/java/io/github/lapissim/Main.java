package io.github.lapissim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.lapissim.dialogue.DialogueManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
        DialogueManager.beginDialogue("test/test");
    }

    @Override
    public void render() {
        update();
        realRender();
    }

    public void update()
    {

    }

    public void realRender()
    {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210, image.getWidth(), image.getHeight());

        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
