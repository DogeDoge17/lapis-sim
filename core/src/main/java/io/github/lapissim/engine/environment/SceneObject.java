package io.github.lapissim.engine.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.Main;
import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.engine.Time;

import java.lang.reflect.InvocationTargetException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Vector;

public abstract class SceneObject {
    protected String id;
    public String  displayName;
    public TextureRegion texture;
    public int x,y, width, height;

    public float scaleX, scaleY;

    public String emotion;

    public boolean visible = true;

    public boolean hovering;
    public float rotation = 0;

    ArrayList<MoveNode> moveKeyframes = new ArrayList<>();
    ArrayList<ScaleNode> scaleKeyframes = new ArrayList<>();
    ArrayList<RotationNode> rotationKeyframes = new ArrayList<>();


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
        hovering = hoveringOver(Main.mouseRec);

        if(hovering && visible){
            if(Gdx.input.justTouched()){
                onClick();
                System.out.println("pressed");
            }
        }

        if(moveKeyframes.size() > 0)  AnimateMove();
        if(scaleKeyframes.size() > 0)  AnimateScale();
        if(rotationKeyframes.size() > 0) AnimateRotation();

    }

    private void AnimateMove()
    {
        MoveNode m = moveKeyframes.get(0);

        m.progress += Time.deltaTime;
        m.progress = Math.max(0, Math.min(m.time, m.progress)); //clamps the thingy

        switch (m.style){
            case none:
                x = (int)m.destination.x;
                y = (int)m.destination.y;
                break;
            case lerp: {
                Vector2 v = new Vector2(x, y);

                v.lerp(m.destination, m.progress);

                x = (int) v.x;
                y = (int) v.y;
                break;
            }
            case linear: {
                Vector2 v = new Vector2(x, y);

                v.interpolate(m.destination, m.progress, Interpolation.linear);

                x = (int) v.x;
                y = (int) v.y;
                break;
            }
        }

        if(m.progress >= m.time){
            moveKeyframes.remove(0);
            return;
        }

    }

    private void AnimateScale()
    {

    }

    private void AnimateRotation()
    {

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
            batch.draw(texture, (x - ((dir * getWidth()) / 2)) + -dir * 5, y - 5, 1, 1, dir * width, height, scaleX, scaleY, rotation);
        }
        else{
            batch.setColor(0, 1, 0, 0.50f);
            batch.draw(texture, (x - ((dir * getWidth()) / 2)) + -dir * 7, y - 7, 1, 1, dir * width, height, scaleX, scaleY, rotation);
        }
        batch.setShader(Main.defaultShader);
        batch.setColor(1,1,1,1);
        batch.draw(texture, getX(),y, 1,1, dir*width, height, scaleX,scaleY,rotation);

    }
}

abstract class AnimationNode
{
    float time;
    float progress;
    TransformStyle style;

    public AnimationNode(float time, TransformStyle style){
        this.time = time;
        this.style = style;
    }
}

class MoveNode extends AnimationNode{
    Vector2 destination;

    public MoveNode(float time, TransformStyle style) {
        super(time, style);

    }
}

class ScaleNode extends AnimationNode{
    float newScale;

    public ScaleNode(float scale, float time, TransformStyle style) {
        super(time, style);
        newScale = scale;
    }
}

class RotationNode extends AnimationNode{
    float degrees;

    public RotationNode(float degrees, float time, TransformStyle style) {
        super(time, style);
        this.degrees = degrees;
    }
}

enum TransformStyle{
    none,
    lerp,
    linear
}
