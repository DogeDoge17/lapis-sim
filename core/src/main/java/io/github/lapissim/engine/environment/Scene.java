package io.github.lapissim.engine.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.lapissim.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Scene {
    public String backDrop;
    public String name;

    private TextureRegion bgText;
    private SceneObject[] objects;


    public Scene(String sceneName,  String backDrop, SceneObject[] objs){
        this.backDrop = backDrop;
        this.name = sceneName;

        bgText = new TextureRegion( new Texture("backdrops/"+backDrop + ".png"));
        objects = objs;
        start();
    }

    public Scene(String sceneName,  String backDrop){
        this.backDrop = backDrop;
        this.name = sceneName;

        bgText = new TextureRegion( new Texture("backdrops/"+backDrop + ".png"));
        objects = new SceneObject[0];
    }


    public void draw(SpriteBatch batch)
    {
        //draw (TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation)
        batch.setColor(1,1,1,1);
        batch.draw(bgText, 0, 0, 1, 1, Main.SCREENWIDTH, Main.SCREENHEIGHT,1, 1, 0);

        if(objects != null)
        for(int i = 0; i< objects.length; i++){
            objects[i].draw(batch);
        }
        batch.setColor(1,1,1,1);
    }

    public void postDraw(SpriteBatch batch){
        for(int i = 0; i < objects.length; i++){
            objects[i].postDraw(batch);
        }
    }

    public void update()
    {
        Arrays.sort(objects, Comparator.comparingInt(o -> o.drawOrder));
        for(int i = 0; i < objects.length; i++){
            objects[i].earlyUpdate();
        }
        for(int i = 0; i < objects.length; i++){
            objects[i].update();
        }
        for(int i = 0; i < objects.length; i++){
            objects[i].lateUpdate();
        }
    }

    public void start(){}

    public Speaker getSpeaker(String id)
    {
        if(objects == null)
            return null;

        SceneObject obj = getObject(id);
        if(obj instanceof Speaker)
            return (Speaker)obj;

        return null;
    }

    public Speaker[] getSpeakers()
    {
        if(objects == null)
            return null;

        ArrayList<Speaker> list = new ArrayList<>();

        for(int i =0; i < objects.length; i++ )
        {
            if(objects[i] instanceof Speaker)
                list.add((Speaker) objects[i]);
        }
        return list.toArray(new Speaker[0]);
    }

    public SceneObject getObject(String id)
    {
        if(objects == null)
            return null;

        if(objects.length == 0)
            return null;

        for(int i = 0; i < objects.length; i++) {
            if(objects[i].id.equalsIgnoreCase(id))
                return objects[i];
        }

        return null;
    }

    public void addObject(SceneObject obj)
    {
        if(objects == null)
            objects = new SceneObject[0];

        SceneObject[] newl = new SceneObject[objects.length+1];
        for(int i = 0; i < objects.length; i ++){
            newl[i] = objects[i];
        }
        newl[objects.length] = obj;
        objects = newl;
    }

    public void exit(){

    }
}
