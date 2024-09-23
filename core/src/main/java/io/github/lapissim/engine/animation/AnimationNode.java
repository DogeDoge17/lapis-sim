package io.github.lapissim.engine.animation;


public abstract class AnimationNode
{
    public float time;
    public float progress;
    public TransformStyle style;

    public AnimationNode(float time, TransformStyle style){
        this.time = time;
        this.style = style;
    }
}
