package io.github.lapissim.game.scenes.prologue.speakers;

import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.engine.environment.Speaker;

public class StevenP extends Speaker {

    public StevenP( String displayName, String emotion, int x, int y) {
        super("Steven", displayName, emotion, x, y);
    }

    @Override
    public void onClick()
    {
        switch (SceneManager.activeScene.name){
            case "Funland Arcade":
                DialogueManager.beginDialogue("test/test3");
                break;
            case "Big Donut":
                DialogueManager.beginDialogue("test/test4");
                break;
            case "Funland Arcade Inside":
                DialogueManager.beginDialogue("test/sadieFind");
                break;
        }
    }

}
