package io.github.lapissim.game.scenes.prologue.speakers;

import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.engine.save.Flags;

public class LapisP extends Speaker {

    public LapisP( String displayName, String emotion, int x, int y) {
        super("Lapis", displayName, emotion, x, y);
    }

    @Override
    public void onClick()
    {
        double carti = Flags.flags.getDouble("findCarti");
        switch (SceneManager.activeScene.name) {

            case "Funland Arcade":
                if(carti == 4)
                    DialogueManager.beginInlineDialogue("DIA \"I don't like her very much\" lapis neutral\nDIA \"Why not\" steven neutral\nend");
                else
                    DialogueManager.beginDialogue("test/test2");
                    break;
            case "Big Donut":
                if(carti == 4)
                    DialogueManager.beginInlineDialogue("DIA \"Can we just get this other with\" lapis neutral\nend");
                else
                    DialogueManager.beginDialogue("test/test5");
                break;
            case "Funland Arcade Inside":
                if(carti == 4)
                    DialogueManager.beginInlineDialogue("DIA \"She's a bit fat, Steven\" lapis neutral\ndir steven -1\nDIA \"That's not nice\" steven neutral\nend");
                else
                    DialogueManager.beginDialogue("test/sadieFind");
                break;
        }
    }

}
