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

public class DialogueDoor extends Doorway{
    String dia;


    public DialogueDoor(String dialogue, String text, int x, int y, int width, int height) {
        super(text, x, y, width,height);
        this.dia = dialogue;
    }

    @Override
    public void onClick(){
        if(speakerHover)
            return;

        DialogueManager.beginDialogue(dia);
    }
}
