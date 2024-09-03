package io.github.lapissim.engine.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TextRenderer
{
    public static void drawString(SpriteBatch batch, Font fn, String inp, float x, float y, float fontSize, Color colour)
    {
        batch.setColor(colour);
        float lineOffset = x;
        for(int i = 0; i < inp.length(); i++){
            char c = inp.charAt(i);
            Rectangle rec = fn.textureMap.get(c);
            fn.atlas.setRegion((int)rec.x,(int)rec.y,(int)rec.width, (int)rec.height);
            lineOffset += rec.width - rec.x;
            batch.draw(fn.atlas, lineOffset,y);
        }
        batch.setColor(1,1,1,1);
    }
}
