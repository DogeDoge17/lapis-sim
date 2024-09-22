package io.github.lapissim.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.engine.environment.SceneManager;

import java.util.Vector;

public class Input
{
    private static byte[] lastFrame = new byte [256];

    private static byte[] lastMouse = new byte[6];

    private static Vector2 scroll = new Vector2();
    public static void setupMouse(){
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(button < lastMouse.length)
                    lastMouse[button] = 1;

                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (button < lastMouse.length)
                    lastMouse[button] = 3;

                return false;
            }

            @Override
            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                scroll = new Vector2(amountX, amountY);
                return true;
            }

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }
        });
    }


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

        for (byte i = 0; i < lastMouse.length; i++){
            switch (lastMouse[i]){
                case 1:
                    lastMouse[i] = 2;
                    break;
                case 3:
                    lastMouse[i] = 0;
                    break;
            }
        }

        scroll = new Vector2();
    }

    public static boolean getKeyUp(int keyCode){
        //if(!getKey(keyCode) && (lastFrame[keyCode] == 1|| lastFrame[keyCode] == 2))
        return (!getKey(keyCode) && (lastFrame[keyCode] == 1|| lastFrame[keyCode] == 2)) && !disableInput();
    }

    public static boolean getKeyDown(int keyCode){
        return Gdx.input.isKeyJustPressed(keyCode) && !disableInput();
    }

    public static boolean getMouseDown(int mouseCode){
        return lastMouse[mouseCode] == 1 && !disableInput();
    }

    public static boolean getMouseUp(int mouseCode){
        return lastMouse[mouseCode] == 3 && !disableInput();
    }

    public static boolean getMouse(int mouseCode){
        return lastMouse[mouseCode] == 2 || lastMouse[mouseCode] == 1 && !disableInput();
    }

    public static boolean getKey(int keyCode){
        return Gdx.input.isKeyPressed(keyCode) && !disableInput();
    }

    public static Vector2 getScroll(){
        if(disableInput())
            return new Vector2();
        return scroll;
    }

    private static boolean disableInput(){
        return SceneManager.transitioning;
    }


}
