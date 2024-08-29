package io.github.lapissim.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class DialogueManager
{
    public static String[] rawLines;
    public static Line[] lines;
    public static String rawDialogue;
    public static int linePointer;

    public static void beginDialogue(String path)
    {
        {
            FileHandle hi = Gdx.files.internal(path);
            rawDialogue = hi.readString();
            rawLines = rawDialogue.split("\n");
            for(int i = 0; i < rawLines.length; i++)
            {

            }


        }

    }
}
