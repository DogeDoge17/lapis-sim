package io.github.lapissim.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.lapissim.Main;
import io.github.lapissim.engine.environment.Prop;
import io.github.lapissim.engine.environment.Scene;
import io.github.lapissim.engine.environment.SceneObject;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.engine.render.Font;
import io.github.lapissim.engine.render.TextRenderer;
import sun.font.TrueTypeFont;

public class FunlandArcade extends Scene {

    Font sans;

    public FunlandArcade(SceneObject[] objs) {
        super("Funland Arcade", "beach-city/funland-arcade", objs);
    }

    @Override
    public void start(){
         sans = new Font("Comic Sans MS");
    }

    int lapisTexture = 0;
    String[] lapemos = {"bored","bored-cracked","happy","happy-cracked","neutral","neutral-cracked","tired","tired-cracked"};

    @Override
    public void update(){
        Speaker lappy = (Speaker)getObject("lapis");
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

        steven.x += steven.dir;

    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        TextRenderer.drawString(batch, sans, "Aeek", 0,100, 126, new Color(1,1,1,1));

        //BitmapFont fnt = new BitmapFont();
        //fnt.getData().setScale(126,126);
        //fnt.draw(batch, "hi mom", 0,0);

        //Texture tex = new Texture("font/Comic Sans MS.png");

        //batch.setColor(1,0,0,1);
        //batch.draw(tex, 1,1);


    }
}
