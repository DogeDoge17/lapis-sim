package io.github.lapissim.game.prefabs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.Main;
import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.engine.environment.SceneObject;
import io.github.lapissim.engine.render.Font;
import io.github.lapissim.engine.render.TextRenderer;

public class FakeDoor extends SceneObject {
    String dia;


    public FakeDoor(String dialogue, String text, int x, int y, int width, int height) {
        super("FakeDoorway", text, "doorway", x, y);
        this.dia = dialogue;
        this.width = width;
        this.height = height;
        centred = false;
    }

    @Override
    public void onClick(){
        DialogueManager.beginDialogue(dia);
    }

    @Override
    protected void loadTexture(){
        texture = new TextureRegion(new Texture("square.png"));
    }

    @Override
    public void draw(SpriteBatch batch)
    {
        if(!hovering)
            return;

        batch.setColor(0,0,0,0.25f);

        if(centred)
            batch.draw(texture, getX(),getY(),getWidth(),getHeight());
        else
            batch.draw(texture, getRawX(),getY(),getWidth(),getHeight());
        batch.setColor(1,1,1,1);

        Vector2 textSize = TextRenderer.measureString(Font.fontCache.get("Comic Sans MS"), displayName, 16);

        if(textSize.x + Main.mouseRec.x <= Main.SCREENWIDTH - 15)
            TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), displayName, Main.mouseRec.x, Main.mouseRec.y, 16, Color.WHITE);
        else{
            TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), displayName, Main.mouseRec.x - textSize.x, Main.mouseRec.y, 16, Color.WHITE);
        }

    }
}
