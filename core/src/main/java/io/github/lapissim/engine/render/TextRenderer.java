package io.github.lapissim.engine.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TextRenderer
{
    public static void drawString(SpriteBatch batch, Font fn, String inp, float x, float y, float fontSize, Color colour)
    {
        batch.setColor(colour);
        float charOffset = x;
        float lineOffset = 0;
        Rectangle prevRec = new Rectangle();
        float fontPercent = fontSize/fn.defaultSize;

        for(int i = 0; i < inp.length(); i++){
            char c = inp.charAt(i);
            if (c == '\n' || c == '\r') {
                charOffset = 0;
                prevRec = new Rectangle();
                lineOffset -= fn.lineSeparation;
                continue;
            }
            Rectangle rec = fn.textureMap.get(c);
            fn.atlas.setRegion((int)rec.x,(int)rec.y,(int)rec.width, (int)rec.height);
            charOffset += (prevRec.width - prevRec.x) * fontPercent;

            //(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation)
            batch.draw(fn.atlas, charOffset, y+lineOffset, 0,0, fn.atlas.getRegionWidth(), fn.atlas.getRegionHeight(), fontPercent, fontPercent, 0);
            prevRec = rec;
        }
        batch.setColor(1,1,1,1);
    }
}
