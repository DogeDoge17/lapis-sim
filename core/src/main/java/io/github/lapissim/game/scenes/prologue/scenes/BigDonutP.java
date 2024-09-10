package io.github.lapissim.game.scenes.prologue.scenes;

import io.github.lapissim.Main;
import io.github.lapissim.engine.environment.Scene;
import io.github.lapissim.engine.environment.SceneObject;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.game.prefabs.Doorway;
import io.github.lapissim.game.scenes.prologue.speakers.LapisP;
import io.github.lapissim.game.scenes.prologue.speakers.StevenP;

public class BigDonutP extends Scene {
    public BigDonutP(SceneObject[] objs) {
        super("Big Donut", "beach-city/BigDonut", objs);
    }
    public BigDonutP()
    {
        super("Big Donut", "beach-city/BigDonut");
        LapisP lapis = new LapisP("Lapis", "neutral", Speaker.RightAnchor-200, 109);
        lapis.dir = -1;
        addObject(lapis);
        addObject(new StevenP( "Steven", "neutral", 250, 140));
        addObject(new Doorway<>(FunlandArcadeP.class, "Arcade", 0, 100,200, 500));


    }

}
