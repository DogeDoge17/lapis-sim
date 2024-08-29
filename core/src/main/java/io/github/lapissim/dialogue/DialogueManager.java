package io.github.lapissim.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;
import java.util.List;

public class DialogueManager
{
    public static List<String> rawLines;
    public static String rawDialogue;
    public static int linePointer;

    public static void beginDialogue(String path)
    {
        {
            FileHandle hi = Gdx.files.internal(path);
            rawDialogue = hi.readString();
            rawLines = Arrays.stream(rawDialogue.split("\n")).toList();
            for(int i = 0; i < rawLines.size();i++){

            }


        }

    }
}
