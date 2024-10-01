package io.github.lapissim.engine.animation;


import com.badlogic.gdx.math.Vector2;

public class RotationNode extends AnimationNode {
    public Vector2 degrees;

    public Vector2 start;

    public RotationNode(Vector2 degrees, float time, TransformStyle style) {
        super(time, style);
        this.degrees = degrees;
    }
}
