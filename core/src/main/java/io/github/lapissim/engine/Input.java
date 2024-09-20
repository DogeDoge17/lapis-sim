package io.github.lapissim.engine;

import com.badlogic.gdx.Gdx;

public class Input
{
    public static int[] lastFrame = new int[256];

    public static void lateUpdate(){
        for(int i = 0; i < lastFrame.length; i++)
        {
            if(getKeyDown(i))
                lastFrame[i] = 1;
            else if(getKey(i))
                lastFrame[i] = 2;
            else
                lastFrame[0] = 0;
        }
    }

    public static boolean getKeyUp(int keyCode){
        if(!getKey(keyCode) && (lastFrame[keyCode] == 1|| lastFrame[keyCode] == 2))
            return true;
        return false;
    }

    public static boolean getKeyDown(int keyCode){
        return Gdx.input.isKeyJustPressed(keyCode);
    }

    public static boolean getKey(int keyCode){
        return Gdx.input.isKeyPressed(keyCode);
    }


}
