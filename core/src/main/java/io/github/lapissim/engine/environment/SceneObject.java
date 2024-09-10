package io.github.lapissim.engine.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.dialogue.DialogueManager;

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
        texture = new TextureRegion(new Texture("speakers/"+id+"/"+emotion + ".png"));

        width = texture.getRegionWidth();
        height = texture.getRegionHeight();
        this.x = x;
        this.y = y;
        scaleX = 1;
        scaleY = 1;
        this.emotion = emotion;
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

    private boolean hoveringOver(Rectangle m){
        return (this.x < m.x + m.width && this.x + this.width > m.x && this.y < m.y + m.height && this.y + this.height > m.y) && !DialogueManager.visible;
    }

    public void update(){
        ///Gdx.input.getX   ()
        //System.out.println( + ":" + Gdx.input.getY());
        hovering = hoveringOver(new Rectangle(Gdx.input.getX(),  Gdx.input.getY(), 9, 9));

        if(hovering){
            System.out.println("hovering over " + id);
        }
    }

    public void onClick(){

    }

    public void draw(SpriteBatch batch){
        if(!visible)
            return;

        batch.setColor(0,0,0,0.50f);
        batch.draw(texture, (x-((dir*getWidth())/2))+ -dir*5,y-5, 1,1, dir*width, height, scaleX,scaleY,0);
        batch.setColor(1,1,1,1);
        batch.draw(texture, x-((dir*getWidth())/2),y, 1,1, dir*width, height, scaleX,scaleY,0);

    }
}
