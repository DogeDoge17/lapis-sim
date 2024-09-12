package io.github.lapissim.engine.aniamation;

import com.badlogic.gdx.math.Vector2;

public class MoveNode extends AnimationNode {
    public Vector2 destination;
    public Vector2 start;

    public boolean additive;

    public MoveNode(Vector2 destination, float time, TransformStyle style) {
        super(time, style);
        this.destination = destination;

    }
}
