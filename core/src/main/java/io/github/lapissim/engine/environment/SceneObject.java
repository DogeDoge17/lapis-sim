package io.github.lapissim.engine.environment;

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
import io.github.lapissim.engine.animation.MoveNode;
import io.github.lapissim.engine.animation.RotationNode;
import io.github.lapissim.engine.animation.ScaleNode;
import io.github.lapissim.engine.permissions.PermissionManager;
import io.github.lapissim.engine.permissions.Systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public abstract class SceneObject {
    protected String id;
    public String  displayName;
    public TextureRegion texture;
    public int x,y, width, height;

    public float scaleX, scaleY;

    public String emotion;

    private HashMap<String, TextureRegion> spriteArchive = new HashMap<>();

    public boolean visible = true;

    public boolean hovering;
    public float rotation = 0;

    ArrayList<MoveNode> moveKeyframes = new ArrayList<>();
    ArrayList<ScaleNode> scaleKeyframes = new ArrayList<>();
    ArrayList<RotationNode> rotationKeyframes = new ArrayList<>();

    public boolean centred = true;

    public int drawOrder = 5;
    public int updateOrder = 5;


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

    public void setDrawOrder(int order){
        this.drawOrder = order;
    }

    public void setPosition(Vector2 position){
        x = (int)position.x;
        y = (int)position.y;
    }

    protected void loadTexture()
    {
        texture = new TextureRegion(new Texture("speakers/" + id.toLowerCase(Locale.ROOT) +"/"+emotion.toLowerCase(Locale.ROOT) + ".png"));
    }

    public void setEmotion(String emo)
    {
        if(Objects.equals(emo, emotion))
            return;

        if(!spriteArchive.containsKey(emo)) {
            texture = new TextureRegion(new Texture("speakers/" + id.toLowerCase(Locale.ROOT) + "/" + emo.toLowerCase(Locale.ROOT) + ".png"));
            spriteArchive.put(emo, texture);
            this.emotion = emo;
            return;
        }

        texture = spriteArchive.get(emo);
        this.emotion = emo;
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

    public float getRawX(){return x;}

    public float getY(){
        return y;
    }

    private boolean hoveringOver(Rectangle m){
        return centred ? (getX2() < m.x + m.width && getX2() + getWidth() > m.x && getY() < m.y + m.height && getY() + getHeight() > m.y) && !DialogueManager.visible : (getRawX() < m.x + m.width && getRawX() + getWidth()*dir > m.x && getY() < m.y + m.height && getY() + getHeight() > m.y) && !DialogueManager.visible ;
    }


    public void onHover(){

    }

    public void earlyUpdate() { }

    public void lateUpdate() { }

    public void update(){
        hovering = hoveringOver(Main.mouseRec);

        if(hovering)
            onHover();

        if(hovering && visible && PermissionManager.checkPermissions(Systems.SCENEOBJECTCLICK)){
            if(io.github.lapissim.engine.Input.getMouseDown(Input.Buttons.LEFT)){
                onClick();
                //System.out.println("pressed");
            }
        }

        if(!moveKeyframes.isEmpty())  AnimateMove();
        if(!scaleKeyframes.isEmpty())  AnimateScale();
        if(!rotationKeyframes.isEmpty()) AnimateRotation();

    }

    private void AnimateMove()
    {
        MoveNode m = moveKeyframes.get(0);

        m.progress += (float) (0.2 * Time.gameTime);
        m.progress = Math.max(0, Math.min(m.time, m.progress)); //clamps the thingy
        float t = (m.progress / m.time );

        if(t >= 1)
            t = 1;

        //System.out.println("x: " + x);

        //System.out.println(t);

        switch (m.style){
            case none:
                x = (int)m.destination.x;
                y = (int)m.destination.y;
                break;
            case lerp: {
                Vector2 v = new Vector2(x, y);

                v.lerp(m.destination, t);

                x = (int) v.x;
                y = (int) v.y;
                break;
            }
            case linear: {
                Vector2 v = new Vector2(x, y);

                v.interpolate(m.destination, t, Interpolation.linear);

                x = (int) v.x;
                y = (int) v.y;
                break;
            }
        }

        if(t >= .14f)
            m.progress = m.time;

        if(m.progress >= m.time){
            moveKeyframes.remove(0);
            initMoveKeyframe();
            return;
        }

    }

    public void addMoveKeyframe(MoveNode moveNode) {
        if(moveKeyframes == null)
            moveKeyframes = new ArrayList<>();

        moveKeyframes.add(moveNode);
        initMoveKeyframe();
    }

    private void initMoveKeyframe(){
        if(moveKeyframes.size() != 1)
            return;
        MoveNode keyFrame = moveKeyframes.get(0);

        keyFrame.start = new Vector2(x,y);

        if(!keyFrame.additive)
            return;

        keyFrame.destination = new Vector2(x + keyFrame.destination.x, y + keyFrame.destination.y );
    }

    private void AnimateScale()
    {
        ScaleNode m = scaleKeyframes.get(0);

        m.progress += 0.2 * Time.gameTime;
        m.progress = Math.max(0, Math.min(m.time, m.progress)); //clamps the thingy
        float t = (m.progress / m.time );

        if(t >= 1)
            t = 1;

        switch (m.style){
            case none:
                scaleX = (int)m.newScale.x;
                scaleY = (int)m.newScale.y;
                break;
            case lerp: {
                Vector2 v = new Vector2(scaleX, scaleY);

                v.lerp(m.oldScale, t);

                scaleX = (int) v.x;
                scaleY= (int) v.y;
                break;
            }
            case linear: {
                Vector2 v = new Vector2(scaleX, scaleY);

                v.interpolate(m.newScale, t, Interpolation.linear);

                scaleX = v.x;
                scaleY = v.y;
                break;
            }
        }

        if(t >= .14f)
            m.progress = m.time;

        if(m.progress >= m.time){
            scaleKeyframes.remove(0);
            initScaleKeyframe();
            return;
        }
    }

    public void addScaleKeyframe(ScaleNode scaleNode) {
        if(scaleKeyframes == null)
            scaleKeyframes = new ArrayList<>();

        scaleKeyframes.add(scaleNode);
        initScaleKeyframe();
    }

    private void initScaleKeyframe(){
        if(scaleKeyframes.size() != 1)
            return;
        ScaleNode keyFrame = scaleKeyframes.get(0);

        keyFrame.oldScale = new Vector2(scaleX, scaleY);

        keyFrame.newScale = new Vector2(keyFrame.newScale.x, keyFrame.newScale.y);
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
        if(centred)
            batch.draw(texture, getX(),y, 0,0, dir*width, height, scaleX,scaleY,rotation);
        else{
            batch.draw(texture, x,y, 0,0, dir*width, height, scaleX,scaleY,rotation);
        }
    }

    public void postDraw(SpriteBatch batch){}

}
