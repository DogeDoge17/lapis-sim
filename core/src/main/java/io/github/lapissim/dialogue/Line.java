package io.github.lapissim.dialogue;

import io.github.lapissim.engine.math.Vector2;

public class Line
{
    public LineType lineType;
    public int position;

    public Vector2 vector;

    public String charaName;
    public String contents;
    public String portrait;

    public int destination;

    public Direction flipDirection;


}

enum Direction{
    Left,
    Right
}


