package io.github.lapissim.game.scenes.prologue.speakers;

import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.engine.save.Flags;

public class SadieP extends Speaker {

    String going2Donut = "dia \"Come on Steven. Let's go to the Big donut to find your friend\" sadie neutral\nend";

    public SadieP(String emotion, int x, int y) {
        super("sadie", "Donut Girl", emotion, x, y);
    }

    @Override
    public void onClick(){
        if(Flags.flags.getDouble("foundCarti") < 5)
            DialogueManager.beginInlineDialogue(going2Donut);

    }
}
