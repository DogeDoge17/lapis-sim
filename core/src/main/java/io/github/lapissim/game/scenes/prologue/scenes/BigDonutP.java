package io.github.lapissim.game.scenes.prologue.scenes;

import io.github.lapissim.engine.environment.Scene;
import io.github.lapissim.engine.environment.SceneObject;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.engine.save.Flags;
import io.github.lapissim.game.prefabs.SceneDoorway;
import io.github.lapissim.game.prefabs.DialogueDoor;
import io.github.lapissim.game.scenes.prologue.speakers.LapisP;
import io.github.lapissim.game.scenes.prologue.speakers.StevenP;
import io.github.lapissim.game.scenes.prologue.speakers.SadieP;
import io.github.lapissim.localization.Language;
import io.github.lapissim.Main;

public class BigDonutP extends Scene {
    public BigDonutP(SceneObject[] objs) {
        super("Big Donut", "beach-city/BigDonut", objs);
    }

    public BigDonutP()
    {
        super("Big Donut", "beach-city/BigDonut");

        LapisP lapis = new LapisP(Language.getEntry("pro.chars.lapis.displayDef"), "neutral", Speaker.RightAnchor-200, 109);
        lapis.dir = -1;

        addObject(lapis);

        addObject(new StevenP( Language.getEntry("pro.chars.steven.displayDef"), "neutral", 250, 140));
        addObject(new SadieP("neutral", Main.SCREENWIDTH/2, 140) {{
            this.setVisibility(Flags.flags.getDouble("findCarti") > 3);
        }});

        addObject(new SceneDoorway(FunlandArcadeP.class, Language.getEntry("pro.arcade.doorway"), 0, 100,200, 500){ { centred = true;}});

        addObject(new DialogueDoor("test/bigDonutDoor", Language.getEntry("pro.big-donut.enter"), 634, 127,203, 160){ { centred = true;}});

        Flags.flags.setDouble("lapisTestArcadeLeave" , 2);
    }

}
