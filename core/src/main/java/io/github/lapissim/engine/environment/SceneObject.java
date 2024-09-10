package io.github.lapissim.engine.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.Main;
import io.github.lapissim.dialogue.DialogueManager;

import java.lang.reflect.InvocationTargetException;
import java.security.Key;

public abstract class SceneObject {
    protected String id;
    public String  displayName;
    public TextureRegion texture;
    public int x,y, width, height;

    public float scaleX, scaleY;

    public String emotion;

    public boolean visible = true;

    public boolean hovering;

    public SceneObject(String id, String displayName, String emotion, int x, int y){
        this.id = id;
        this.displayName = displayName;
        this.emotion = emotion;
        loadTexture();//texture = new TextureRegion(new Texture("speakers/"+id+"/"+emotion + ".png"));

        width = texture.getRegionWidth();
        height = texture.getRegionHeight();
        this.x = x;
        this.y = y;
        scaleX = 1;
        scaleY = 1;
    }

    public int dir = 1;
    public void setDimensions(int w, int h){
        width = w;
        height = h;
    }

    public void setPosition(Vector2 position){
        x = (int)position.x;
        y = (int)position.y;
    }

    protected void loadTexture()
    {
        texture = new TextureRegion(new Texture("speakers/"+id+"/"+emotion + ".png"));
    }

    public void setEmotion(String emo){
        texture = new TextureRegion(new Texture("speakers/"+id+"/"+  emo + ".png"));
        this.emotion = emotion;
    }

    public void setVisibility(boolean val){
        this.visible = val;
    }

    public String getEmotion()
    {
        return emotion;
    }

    public float getWidth(){return width * scaleX;}
    public float getHeight(){return height * scaleY;}

    public float getX(){
        return x-((dir*getWidth())/2);
    }

    public float getX2()
    {
        return x-(getWidth()/2);
    }

    public float getY(){
        return y;
    }

    private boolean hoveringOver(Rectangle m){
        return (getX2() < m.x + m.width && getX2() + getWidth() > m.x && getY() < m.y + m.height && getY() + getHeight() > m.y) && !DialogueManager.visible;
    }

    public void update(){
        ///Gdx.input.getX   ()
        //System.out.println( + ":" + Gdx.input.getY());
        hovering = hoveringOver(Main.mouseRec);

        //if(id == "Steven" && !DialogueManager.visible)
        //    x = Main.SCREENWIDTH/2;

        if(hovering && visible){
            if(Gdx.input.justTouched()){
                onClick();
                System.out.println("pressed");
            }
        }
    }

    public void onClick() {

    }

    public void draw(SpriteBatch batch){
        if(!visible)
            return;

        batch.setShader(Main.outlineShader);
        if(!hovering)
        {
            batch.setColor(0, 0, 0, 0.50f);
            batch.draw(texture, (x - ((dir * getWidth()) / 2)) + -dir * 5, y - 5, 1, 1, dir * width, height, scaleX, scaleY, 0);
        }
        else{
            batch.setColor(0, 1, 0, 0.50f);
            batch.draw(texture, (x - ((dir * getWidth()) / 2)) + -dir * 7, y - 7, 1, 1, dir * width, height, scaleX, scaleY, 0);
        }
        batch.setShader(Main.defaultShader);
        batch.setColor(1,1,1,1);
        batch.draw(texture, getX(),y, 1,1, dir*width, height, scaleX,scaleY,0);

    }
}
