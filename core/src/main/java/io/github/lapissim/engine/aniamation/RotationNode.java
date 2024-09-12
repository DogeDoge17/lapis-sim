package io.github.lapissim.engine.aniamation;


public class RotationNode extends AnimationNode {
    public float degrees;

    public RotationNode(float degrees, float time, TransformStyle style) {
        super(time, style);
        this.degrees = degrees;
    }
}
