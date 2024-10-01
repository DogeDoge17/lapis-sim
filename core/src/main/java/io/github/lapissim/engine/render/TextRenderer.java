package io.github.lapissim.engine.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.Main;

public class TextRenderer
{
    private static boolean wrap = false;
    private static int width = 0;

    private static boolean border = false;
    //private static float borderSize = 1.5f;
    private static Color borderColour = Color.BLACK;

    public static void enableWrapping(int width)
    {
        TextRenderer.width = width;
        wrap = true;
    }

    private static String configureWrap(String input){
        StringBuilder builder = new StringBuilder(input);

        int len = builder.length();

        for(int i = width; i < builder.length(); i += width){
            if(i < width)
                return builder.toString();
            for (int j = i; j > i - width; j--){
                char c = builder.charAt(j);
                if(c == ' '){
                    if(builder.length() > j) {
                        builder.deleteCharAt(j);
                        builder.insert(j, '\n');
                    }
                    break;
                }
            }

            //builder.insert(i, '\n');
        }

        return builder.toString();
    }

    public static Vector2 measureString( Font fn, String inp, float fontSize)
    {
        float charOffset = 0;
        float lineOffset = 0;
        LineChar prevRec = new LineChar();
        float fontPercent = fontSize/fn.defaultSize;
        float maxCharOffset = 0;

        if(wrap) {
            inp = configureWrap(inp);
        }

        for(int i = 0; i < inp.length(); i++){
            char c = inp.charAt(i);

            if (c == '\n' || c == '\r') {
                maxCharOffset = Math.max(maxCharOffset,charOffset);
                charOffset = 0;
                prevRec = new LineChar();
                lineOffset -= fn.lineSeparation;
                continue;
            }
            LineChar rec = fn.textureMap.get(c);
            if(rec == null)
                return new Vector2();
            fn.atlas.setRegion((int)rec.xS,(int)rec.yS,(int)rec.width, (int)rec.height);
            charOffset += (prevRec.width - prevRec.x) * fontPercent;

            prevRec = rec;
        }
        maxCharOffset = Math.max(maxCharOffset,charOffset);
        return new Vector2(maxCharOffset, lineOffset);
    }

    public static Vector2 drawString(SpriteBatch batch, Font fn, String inp, float x, float y, float fontSize, Color colour)
    {
        batch.setColor(colour);
        float charOffset = x;
        float lineOffset = 0;
        LineChar prevRec = new LineChar();
        float fontPercent = fontSize/fn.defaultSize;
        float maxCharOffset = 0;
        //char lC;

        if(wrap) {
            inp = configureWrap(inp);
            wrap = false;
        }


        for(int i = 0; i < inp.length(); i++){
            char c = inp.charAt(i);

            if (c == '\n' || c == '\r') {
                maxCharOffset = Math.max(maxCharOffset,charOffset);
                charOffset = x;
                prevRec = new LineChar();
                lineOffset -= fn.lineSeparation;
                continue;
            }
            LineChar rec = fn.textureMap.get(c);
            if(rec == null)
                return new Vector2();
            fn.atlas.setRegion((int)rec.xS,(int)rec.yS,(int)rec.width, (int)rec.height);
            //int foo = (int)Math.floor(prevRec.x/fn.defaultSize);
            charOffset += (prevRec.width - prevRec.x) * fontPercent;

            if(border) {
                Font borderFn = Font.fontCache.get(fn.internalPath + " Border");
                LineChar recB = borderFn.textureMap.get(c);
                borderFn.atlas.setRegion((int)recB.xS,(int)recB.yS,(int)recB.width, (int)recB.height);

                float borderX = charOffset + ((fn.atlas.getRegionWidth() * fontPercent) - (borderFn.atlas.getRegionWidth() * fontPercent )) / 2;
                float borderY = (y + lineOffset) + ((fn.atlas.getRegionHeight() * fontPercent) - (borderFn.atlas.getRegionHeight() * fontPercent)) / 2;

                batch.setColor(borderColour);
                batch.draw(borderFn.atlas, borderX, borderY, borderFn.atlas.getRegionWidth() * fontPercent , borderFn.atlas.getRegionHeight() * fontPercent);
            }
            //renderChar(fn.atlas.getTexture(), borderX, borderY, fn.atlas.getRegionWidth()*fontPercent * borderSize, fn.atlas.getRegionHeight()*fontPercent*borderSize);

            batch.setColor(colour);
            batch.draw(fn.atlas, charOffset, y+lineOffset, fn.atlas.getRegionWidth()*fontPercent, fn.atlas.getRegionHeight()*fontPercent);
            prevRec = rec;
            //lC = c;
        }
        //batch.setShader(Main.defaultShader);
        batch.setColor(1,1,1,1);
        maxCharOffset = Math.max(maxCharOffset,charOffset);
        border = false;
        return new Vector2(maxCharOffset, lineOffset);
    }


    public static void setBorder(){
        setBorder(Color.BLACK);
    }

    public static void setBorder(Color color){
        border = true;

        borderColour = color;
    }
}
