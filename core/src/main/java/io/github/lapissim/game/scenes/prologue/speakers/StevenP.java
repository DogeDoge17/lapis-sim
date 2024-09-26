package io.github.lapissim.game.scenes.prologue.speakers;

import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.engine.save.Flags;

public class StevenP extends Speaker {

    public StevenP( String displayName, String emotion, int x, int y) {
        super("Steven", displayName, emotion, x, y);
    }

    @Override
    public void onClick()
    {
        double carti = Flags.flags.getDouble("findCarti");

        switch (SceneManager.activeScene.name){
            case "Funland Arcade":
                if(carti == 4)
                    DialogueManager.beginInlineDialogue("DIA \"I'm really excited to see carti!\" steven happy\nDIA \"This will be so cool...\" steven happy\nend");
                else
                DialogueManager.beginDialogue("test/test3");
                break;
            case "Big Donut":
                if(carti == 4)
                    DialogueManager.beginInlineDialogue("DIA \"Be prepared to see an aura beast of a lifetime everyone\" steven happy\nDIA \"We're ready to see him, Steven\" sadie neutral\nend");
                else
                    DialogueManager.beginDialogue("test/test4");

                break;
            case "Funland Arcade Inside":
                if(carti == 4)
                    DialogueManager.beginInlineDialogue("DIA \"I'm thinkin' 'bout dyin' my hair red just to look like a pint of red\" steven happy\nDIA \"Snrk what was that, Steven\" lapis happy\ndir steven -1\ndia \"Heh, just a little carti for you\" steven happy \nend");
                else
                    DialogueManager.beginDialogue("test/sadieFind");
                break;
        }
    }

}
