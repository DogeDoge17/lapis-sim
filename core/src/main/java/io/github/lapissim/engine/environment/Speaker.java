package io.github.lapissim.engine.environment;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.lapissim.Main;
import io.github.lapissim.dialogue.DialogueManager;

public class Speaker extends SceneObject {
    public String actorName;
    public boolean speaking;

    public final static int RightAnchor = Main.SCREENWIDTH - 100;

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        if(!speaking && DialogueManager.visible) {
            batch.setColor(0, 0, 0, 0.25f);
            batch.draw(texture, (x - ((dir * getWidth()) / 2)), y, 1, 1, dir * width, height, scaleX, scaleY, 0);
            batch.setColor(1, 1, 1, 1);
        }
    }

    public Speaker(String id, String displayName, String emotion, int x, int y) {
        super(id, displayName, emotion, x, y);
    }
}
