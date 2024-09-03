package io.github.lapissim.engine.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.lapissim.Main;
import io.github.lapissim.game.scenes.FunlandArcade;

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

    public void draw(SpriteBatch batch)
    {
        //draw (TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation)
        batch.draw(bgText, 0, 0, 1, 1, Main.SCREENWIDTH, Main.SCREENHEIGHT,1, 1, 0);

        if(objects != null)
        for(int i = 0; i< objects.length; i++){
            objects[i].draw(batch);
        }
    }

    public void update()
    {
    }

    public void start(){}

    public SceneObject getObject(String id)
    {
        if(objects.length == 0)
            return null;

        for(int i = 0; i < objects.length; i++) {
            if(objects[i].id == id)
                return objects[i];
        }

        return null;
    }

    public void addObject(SceneObject obj)
    {
        SceneObject[] newl = new SceneObject[objects.length+1];
        for(int i = 0; i < objects.length; i ++){
            newl[i] = objects[i];
        }
        newl[objects.length] = obj;
        objects = newl;
    }
}
