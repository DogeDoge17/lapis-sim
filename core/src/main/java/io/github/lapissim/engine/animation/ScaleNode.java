package io.github.lapissim.engine.animation;

public class ScaleNode extends AnimationNode {
    public float newScale;

    public ScaleNode(float scale, float time, TransformStyle style) {
        super(time, style);
        newScale = scale;
    }
}
