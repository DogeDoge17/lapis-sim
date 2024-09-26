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
import java.util.function.Supplier;

public class Doorway<T extends Scene> extends SceneObject {

    //public Scene destination;

    public static boolean speakerHover;

    public Class<T> destination;
    //public Supplier<T> destination;

    public Doorway(Class<T> dest, String text, int x, int y, int width, int height) {
        super("Doorway" + text.trim().replace(" ", ""), text, "doorway", x, y);
        this.destination = dest;
        this.width = width;
        this.height = height;
        centred = false;
        drawOrder = 2;
    }

    @Override
    protected void loadTexture(){
        texture = new TextureRegion(new Texture("square.png"));
    }

    @Override
    public void onClick()  {

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

    @Override
    public void earlyUpdate(){
        speakerHover = false;
    }

    @Override
    public void lateUpdate(){
        if(speakerHover)
            hovering = false;
    }

    @Override
    public void postDraw(SpriteBatch batch){
        if(!hovering)
            return;
        Vector2 textSize = TextRenderer.measureString(Font.fontCache.get("Comic Sans MS"), displayName, 16);

        if(textSize.x + Main.mouseRec.x <= Main.SCREENWIDTH - 15)
            TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), displayName, Main.mouseRec.x, Main.mouseRec.y, 16, Color.WHITE);
        else{
            TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), displayName, Main.mouseRec.x - textSize.x, Main.mouseRec.y, 16, Color.WHITE);
        }
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        if(!hovering)
            return;

        batch.setColor(0,0,0,0.25f);

        if(centred)
            batch.draw(texture, getX(),getY(),getWidth(),getHeight());
        else
            batch.draw(texture, getRawX(),getY(),getWidth(),getHeight());
        batch.setColor(1,1,1,1);

        Vector2 textSize = TextRenderer.measureString(Font.fontCache.get("Comic Sans MS"), displayName, 16);

        if(textSize.x + Main.mouseRec.x <= Main.SCREENWIDTH - 15)
            TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), displayName, Main.mouseRec.x, Main.mouseRec.y, 16, Color.WHITE);
        else{
            TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), displayName, Main.mouseRec.x - textSize.x, Main.mouseRec.y, 16, Color.WHITE);
        }

    }

}
