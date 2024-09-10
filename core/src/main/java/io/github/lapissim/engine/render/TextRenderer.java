package io.github.lapissim.engine.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import io.github.lapissim.dialogue.Line;

public class TextRenderer
{
    public static void drawString(SpriteBatch batch, Font fn, String inp, float x, float y, float fontSize, Color colour)
    {
        batch.setColor(colour);
        float charOffset = x;
        float lineOffset = 0;
        LineChar prevRec = new LineChar();
        float fontPercent = fontSize/fn.defaultSize;
        //char lC;

        for(int i = 0; i < inp.length(); i++){
            char c = inp.charAt(i);
            if (c == '\n' || c == '\r') {
                charOffset = 0;
                prevRec = new LineChar();
                lineOffset -= fn.lineSeparation;
                continue;
            }
            LineChar rec = fn.textureMap.get(c);
            fn.atlas.setRegion((int)rec.xS,(int)rec.yS,(int)rec.width, (int)rec.height);
            //int foo = (int)Math.floor(prevRec.x/fn.defaultSize);
            charOffset += (prevRec.width - prevRec.x) * fontPercent;

            //(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation)
            batch.draw(fn.atlas, charOffset, y+lineOffset, 0,0, fn.atlas.getRegionWidth(), fn.atlas.getRegionHeight(), fontPercent, fontPercent, 0);
            prevRec = rec;
            //lC = c;
        }
        batch.setColor(1,1,1,1);
    }
}
