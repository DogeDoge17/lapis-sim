package io.github.lapissim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.bcel.internal.generic.FALOAD;
import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.dialogue.Log;
import io.github.lapissim.engine.Input;
import io.github.lapissim.engine.Time;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.engine.environment.SceneObject;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.engine.render.Font;
import io.github.lapissim.engine.render.TextRenderer;
import io.github.lapissim.engine.save.Flags;
import io.github.lapissim.engine.save.Save;
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

    public static boolean isFullscreen;

    private SpriteBatch batch;
    //private Texture image;
    public static Rectangle mouseRec;
    public static Texture whitesSqr;

    public static ShaderProgram outlineShader;
    //public static ShaderProgram textBorderShader;
    public static ShaderProgram defaultShader;

    public static OrthographicCamera camera = new OrthographicCamera();
    public static Viewport viewport = new FitViewport(SCREENWIDTH, SCREENHEIGHT, camera);



    @Override
    public void create() {
        batch = new SpriteBatch();
        //image = new Texture("libgdx.png");
        whitesSqr = new Texture("cursor.png");
        //SceneManager.loadNewScene(new FunlandArcadeP(new SceneObject[]{new LapisP("Lapis", "neutral", 200, 109),new StevenP( "Steven", "happy", Speaker.RightAnchor-100, 140), new Speaker("Carti", "Playboi Carti", "neutral",SCREENWIDTH/2, 140), new Doorway(new BigDonutP(),Speaker.RightAnchor, 100,)}));
        Font.cacheFont(new Font("Comic Sans MS"));
        Font.cacheFont(new Font("Comic Sans MS Border"));
        Input.setupMouse();
        defaultShader = SpriteBatch.createDefaultShader();
        outlineShader = new ShaderProgram(defaultShader.getVertexShaderSource(), Gdx.files.internal("shaders/outline.frag").readString());

        //textBorderShader = new ShaderProgram(defaultShader.getVertexShaderSource(), Gdx.files.internal("shaders/textBorder.frag").readString());

        Save.loadRecentSave();
    }


    @Override
    public void render() {
        update();
        realRender();
        postRender();
    }


    public void update() {
        if((Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ALT_RIGHT) || Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ALT_LEFT)) && Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ENTER)){
            toggleFullscreen(Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.CONTROL_RIGHT));
        }
        Time.update();

        Vector3 screenCoords = viewport.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        mouseRec = new Rectangle(screenCoords.x, screenCoords.y, 9, 9);

        SceneManager.updateActive();
        DialogueManager.updateDialogue();
        Log.update();

        Input.lateUpdate();
    }

    public void realRender() {
        ScreenUtils.clear(0,0,0, 1f);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        SceneManager.drawActive(batch);
        DialogueManager.drawDialogue(batch);
        batch.setColor(0,0,0,1);
        batch.draw(whitesSqr, mouseRec.x, mouseRec.y, mouseRec.width, mouseRec.height);
        //batch.draw(image, 140, 210, image.getWidth(), image.getHeight());

    }

    private void postRender()
    {
        // TextRenderer.setBorder();
        // TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"),  "hi",50,70,256, Color.WHITE);

        SceneManager.activeScene.postDraw(batch);
        SceneManager.drawTransition(batch);
        Log.render(batch);

       // bruhSquare();

        TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"),  Integer.toString(Math.round(Time.fps)),0,0,8, Color.WHITE);

        batch.end();
    }

    private void bruhSquare(){
        int ogX = 250;
        int ogY = 250;
        int ogScale = 2;
        int ogWidth = 300;
        int ogHeight = 200;


        batch.setColor(Color.GREEN);
        batch.draw(whitesSqr, ogX + (ogWidth*ogScale - (ogWidth*ogScale*1.2f))/2, ogY + (ogHeight*ogScale - (ogHeight*ogScale*1.2f))/2, ogWidth * ogScale*1.2f, ogHeight*ogScale*1.2f);

        batch.setColor(1,1,1,0.5f);
        batch.draw(whitesSqr, ogX, ogY, ogWidth * ogScale, ogHeight*ogScale);
    }


    public void toggleFullscreen(boolean borderless){

        isFullscreen = !isFullscreen;

        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        if(!isFullscreen) {
            if(borderless){
                Gdx.graphics.setUndecorated(true);
                Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);
            }else
            {
                Gdx.graphics.setFullscreenMode(displayMode);
            }
        }
        else {
            Gdx.graphics.setWindowedMode(SCREENWIDTH, SCREENHEIGHT);
            Gdx.graphics.setUndecorated(false);
        }
    }


    public void resize(int width, int height) {
       viewport.update(width,height);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        // image.dispose();
    }
}
