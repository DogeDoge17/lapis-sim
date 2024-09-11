package io.github.lapissim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.engine.Time;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.engine.environment.SceneObject;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.engine.render.Font;
import io.github.lapissim.engine.render.TextRenderer;
import io.github.lapissim.game.prefabs.Doorway;
import io.github.lapissim.game.scenes.prologue.scenes.BigDonutP;
import io.github.lapissim.game.scenes.prologue.scenes.FunlandArcadeP;
import io.github.lapissim.game.scenes.prologue.speakers.LapisP;
import io.github.lapissim.game.scenes.prologue.speakers.StevenP;

import java.math.BigDecimal;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {

    public static final Integer SCREENWIDTH = 1280;
    public static final Integer SCREENHEIGHT = 720;

    private SpriteBatch batch;
    //private Texture image;
    public static Rectangle mouseRec;
    public Texture cursor;

    public static ShaderProgram outlineShader;
    public static ShaderProgram defaultShader;


    @Override
    public void create() {
        batch = new SpriteBatch();
        //image = new Texture("libgdx.png");
        cursor = new Texture("cursor.png");
        DialogueManager.beginDialogue("test/test");
        SceneManager.loadNewScene(new FunlandArcadeP());
        //SceneManager.loadNewScene(new FunlandArcadeP(new SceneObject[]{new LapisP("Lapis", "neutral", 200, 109),new StevenP( "Steven", "happy", Speaker.RightAnchor-100, 140), new Speaker("Carti", "Playboi Carti", "neutral",SCREENWIDTH/2, 140), new Doorway(new BigDonutP(),Speaker.RightAnchor, 100,)}));
        Font.cacheFont(new Font("Comic Sans MS"));

        defaultShader = SpriteBatch.createDefaultShader();
        outlineShader = new ShaderProgram(defaultShader.getVertexShaderSource(), Gdx.files.internal("shaders/outline.frag").readString());

    }

    @Override
    public void render() {
        update();
        realRender();
    }

    public void update() {
        Time.update();
        mouseRec = new Rectangle(Gdx.input.getX(), SCREENHEIGHT- Gdx.input.getY(), 9, 9);
        SceneManager.updateActive();
        DialogueManager.updateDialogue();
    }

    public void realRender() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        SceneManager.drawActive(batch);
        DialogueManager.drawDialogue(batch);
        batch.setColor(0,0,0,1);
        batch.draw(cursor, mouseRec.x, mouseRec.y, mouseRec.width, mouseRec.height);
        //batch.draw(image, 140, 210, image.getWidth(), image.getHeight());

        TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"),  Integer.toString(Math.round(Time.fps)),0,0,8, Color.WHITE);

        batch.end();
    }



    @Override
    public void dispose() {
        batch.dispose();
        // image.dispose();
    }
}
