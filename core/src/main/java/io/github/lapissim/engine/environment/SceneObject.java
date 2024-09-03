package io.github.lapissim.engine.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class SceneObject {
    protected String id;
    public String  displayName;
    public TextureRegion texture;
    public int x,y, width, height;

    public float scaleX, scaleY;

    public String emotion;
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

    public String getEmotion()
    {
        return emotion;
    }

    public float getWidth(){return width * scaleX;}
    public float getHeight(){return height * scaleY;}

    public void draw(SpriteBatch batch){
        batch.setColor(0,0,0,0.50f);
        batch.draw(texture, (x-((dir*getWidth())/2))+ -dir*10,y-10, 1,1, dir*width, height, scaleX,scaleY,0);
        batch.setColor(1,1,1,1);
        batch.draw(texture, x-((dir*getWidth())/2),y, 1,1, dir*width, height, scaleX,scaleY,0);

    }
}
