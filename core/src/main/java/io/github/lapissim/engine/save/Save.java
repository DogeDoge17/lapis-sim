package io.github.lapissim.engine.save;

import io.github.lapissim.dialogue.DialogueManager;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.game.scenes.prologue.scenes.FunlandArcadeP;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Save {

    public static String getSaveDirectory(){
        return "";
    }

    public static void loadRecentSave(){
        newGame();
    }

    public static void newGame(){
        Flags.flags = new Flags();
        SceneManager.loadNewScene(new FunlandArcadeP(), false);
        Flags.flags.setFlag("scene", SceneManager.activeScene.getClass().getName());
        DialogueManager.beginDialogue("test/test");
    }

    public static void loadSave(int saveFile){
        byte[] data = new byte[2];
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
             ObjectInputStream objIn = new ObjectInputStream(byteIn)) {
            Flags.flags = (Flags)objIn.readObject();
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.out.println("\033[0;31mFAILED TO LOAD SAVE " + saveFile + "\033[0m");
        } catch (ClassNotFoundException e) {
            System.out.println("\033[0;31mFAILED TO LOAD SAVE " + saveFile + "\033[0m");
            //throw new RuntimeException(e);
        }
    }

    public static void createSave(int saveFile){

    }
}
