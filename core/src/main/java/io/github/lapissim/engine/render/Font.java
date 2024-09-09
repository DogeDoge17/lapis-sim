package io.github.lapissim.engine.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;

import java.nio.IntBuffer;
import java.util.HashMap;

public class Font {
    public String fontFamily;
    public HashMap<Character, Rectangle> textureMap;

    public int defaultSize = 126;

    public int padding = 10;

    public String internalString = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    public String internalPath;

    public TextureRegion atlas;

    public float lineSeparation;

    public static long usedTexture = 0;

    public static HashMap<String, Font> fontCache = new HashMap<>();

    public Font(String internalPath) {

        IntBuffer intBuffer = BufferUtils.newIntBuffer(16);
        Gdx.gl20.glGetIntegerv(GL20.GL_MAX_TEXTURE_SIZE, intBuffer);
        System.out.println(intBuffer.get());

        this.internalPath = internalPath;
        textureMap = new HashMap<>();
        atlas = new TextureRegion(new Texture("font/" + internalPath + ".png"));
        FileHandle hi = Gdx.files.internal("font/" + internalPath + ".fntdat");
        String rawData = hi.readString();
        String[] associatedData = rawData.split("\n");

        lineSeparation = (float)defaultSize*0.95f;

        for(int i = 0; i < internalString.length(); i++){
            if(i >= associatedData.length)
                continue;
            char c = internalString.charAt(i);
            //System.out.println(i);
            String[] lineVars = associatedData[i].split(" ");
            textureMap.putIfAbsent(c, new Rectangle(Float.parseFloat(lineVars[0].replace("\n", "")),Float.parseFloat(lineVars[1].replace("\n", "")),Float.parseFloat(lineVars[2].replace("\n", "")),Float.parseFloat(lineVars[3].replace("\n", ""))));
        }
    }

    public static void cacheFont(Font f){
        fontCache.putIfAbsent(f.internalPath, f);
    }

    @Override
    protected void finalize() {
    }
}
