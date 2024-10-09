package io.github.lapissim.game.scenes.prologue.scenes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.lapissim.Main;
import io.github.lapissim.engine.environment.Scene;
import io.github.lapissim.engine.environment.SceneObject;
import io.github.lapissim.engine.render.Font;
import io.github.lapissim.game.prefabs.SceneDoorway;
import io.github.lapissim.game.scenes.prologue.speakers.LapisP;
import io.github.lapissim.game.scenes.prologue.speakers.SadieP;
import io.github.lapissim.game.scenes.prologue.speakers.StevenP;

public class FunlandArcadeInsideP extends Scene {

    Font sans;

    public FunlandArcadeInsideP(SceneObject[] objs) {
        super("Funland Arcade Inside", "beach-city/funland-arcade-inside", objs);
    }

    public FunlandArcadeInsideP()
    {
        super("Funland Arcade Inside", "beach-city/funland-arcade-inside");
        addObject(new LapisP("Lapis", "neutral", 200, 109)) ;
        addObject(new StevenP( "Steven", "happy", 500, 140) {{dir = -1;}});
        addObject(new SadieP("neutral",  Main.SCREENWIDTH +300, 100));
        addObject(new SceneDoorway(FunlandArcadeP.class, "Leave Arcade", 40, 0, Main.SCREENWIDTH-80, 100) { { centred = false;}});
        //TODO: test dialogue starting on scene entrance and flags
        //addObject(new Doorway(FunlandArcadeInsideP.class,"Enter Arcade", Speaker.RightAnchor+45, 100,140, 500));
    }


    @Override
    public void start(){
        getObject("steven").dir = -1;
    }

    int lapisTexture = 0;
    //String[] lapemos = {"bored","bored-cracked","happy","happy-cracked","neutral","neutral-cracked","tired","tired-cracked"};

    @Override
    public void update(){
        /*Speaker lappy = (Speaker)getObject("lapis");
        Speaker steven = (Speaker)getObject("steven");


        int edge = Main.SCREENWIDTH - (lappy.width/2);
        int edgeS = Main.SCREENWIDTH - ((int)steven.getWidth()/2);

        if(lappy.x >= edge){
            lappy.x = edge;
            lappy.dir = -1;

            lapisTexture++;
            if(lapisTexture > lapemos.length-1) {
                lapisTexture = 0;
            }
            lappy.setEmotion(lapemos[lapisTexture]);
        }else if(lappy.x <= 0 + (lappy.getWidth()/2)){
            lappy.x = 0 + ((int)lappy.getWidth()/2);
            lappy.dir = 1;

            lapisTexture++;
            if(lapisTexture > lapemos.length-1) {
                lapisTexture = 0;
            }
            lappy.setEmotion(lapemos[lapisTexture]);
        }

        lappy.x += lappy.dir;


        if(steven.x >= edgeS){
            steven.x = edgeS;
            steven.dir = -1;
        }else if(steven.x <= steven.getWidth()/2){
            steven.x = (int)steven.getWidth()/2;
            steven.dir = 1;
        }

        steven.x += steven.dir;*/
        super.update();
    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);




        //BitmapFont fnt = new BitmapFont();
        //fnt.getData().setScale(126,126);
        //fnt.draw(batch, "hi mom", 0,0);

        //Texture tex = new Texture("font/Comic Sans MS.png");

        //batch.setColor(1,0,0,1);
        //batch.draw(tex, 1,1);
    }
}
