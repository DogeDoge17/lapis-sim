package io.github.lapissim.engine.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

import java.util.HashMap;

public class Font {
    public String fontFamily;
    public HashMap<Character, Rectangle> textureMap;

    public int defaultSize = 126;

    public int padding = 10;

    public String internalString = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    public TextureRegion atlas;

    public Font(String internalPath) {
        textureMap = new HashMap<>();
        atlas = new TextureRegion(new Texture("font/" + internalPath + ".png"));
        FileHandle hi = Gdx.files.internal("font/" + internalPath + ".fntdat");
        String rawData = hi.readString();
        String[] associatedData = rawData.split("\r\n");

        for(int i = 0; i < internalString.length(); i++){
            if(i >= associatedData.length)
                continue;
            char c = internalString.charAt(i);
            String[] lineVars = associatedData[i].split(" ");
            textureMap.putIfAbsent(c, new Rectangle(Float.parseFloat(lineVars[0]),Float.parseFloat(lineVars[1]),Float.parseFloat(lineVars[2]),Float.parseFloat(lineVars[3])));
        }
    }
    @Override
    protected void finalize() {
    }
}