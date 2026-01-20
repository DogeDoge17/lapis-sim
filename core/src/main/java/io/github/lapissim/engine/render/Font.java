package io.github.lapissim.engine.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.IntBuffer;
import java.util.HashMap;

public class Font {
    public String fontFamily;
    public HashMap<Character, LineChar> textureMap;

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

        lineSeparation = (float)defaultSize*0.3f;

        for(int i = 0; i < internalString.length(); i++){
            if(i >= associatedData.length)
                continue;
            char c = internalString.charAt(i);
            //System.out.println(i);
            String[] lineVars = associatedData[i].split(" ");
            textureMap.putIfAbsent(c, new LineChar(readyCoord(lineVars[0]),readyCoord(lineVars[1]), readyCoord(lineVars[2]), readyCoord(lineVars[3]),readyCoord(lineVars[4])));
        }
        textureMap.entrySet().forEach(entry -> {
            Character key = entry.getKey();
            System.out.println("Key: " + key + ", Value: " + entry.getValue());
        });
    }

    int readyCoord(String s){
        return Integer.parseInt(s.replace("\n", "").replace("\r",""));
    }

    public static void cacheFont(Font f){
        fontCache.putIfAbsent(f.internalPath, f);
    }

    @Override
    protected void finalize() {
    }
}

class LineChar {
    public int xS, yS, width, height, x = 0;

    public LineChar(){}

    public LineChar(int xS, int yS, int width, int height, int x)
    {
        this.xS = xS;
        this.yS = yS;
        this.width = width;
        this.height = height;
        this.x = x;
    }

    public String toString(){
        return String.format("xS: %d, yS: %d, width: %d, height: %d, x: %d", xS, yS, width, height, x);
    }
}
