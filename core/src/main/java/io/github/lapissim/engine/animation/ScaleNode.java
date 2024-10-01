package io.github.lapissim.engine.animation;

import com.badlogic.gdx.math.Vector2;

public class ScaleNode extends AnimationNode {
    public Vector2 newScale;
    public Vector2 oldScale;

    public ScaleNode(Vector2 scale, float time, TransformStyle style) {
        super(time, style);
        newScale = scale;
    }
}
