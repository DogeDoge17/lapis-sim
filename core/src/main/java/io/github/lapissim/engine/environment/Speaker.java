package io.github.lapissim.engine.environment;

import io.github.lapissim.Main;

public class Speaker extends SceneObject {
    public String actorName;

    public final static int RightAnchor = Main.SCREENWIDTH - 100;


    public Speaker(String id, String displayName, String emotion, int x, int y) {
        super(id, displayName, emotion, x, y);
    }
}
