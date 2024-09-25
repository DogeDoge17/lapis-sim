package io.github.lapissim.engine.environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.Main;
import io.github.lapissim.dialogue.DialogueManager;

import java.util.ArrayList;

public class Speaker extends SceneObject {
    public boolean speaking;

    public final static int RightAnchor = Main.SCREENWIDTH - 100;

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        if(!visible)
            return;

        if(!speaking && DialogueManager.visible) {
            batch.setColor(0, 0, 0, 0.4f);
            batch.draw(texture, (x - ((dir * getWidth()) / 2)), y, 1, 1, dir * width, height, scaleX, scaleY, 0);

            batch.setColor(1, 1, 1, 1);
        }
    }

    public Speaker(String id, String displayName, String emotion, int x, int y) {
        super(id, displayName, emotion, x, y);
    }
}
