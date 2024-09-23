package io.github.lapissim.engine.permissions;

import java.util.Arrays;

import static io.github.lapissim.engine.permissions.Systems.*;

public class PermissionManager {

    public static int[][] whitelists = new int[][]{
        {
            INPUT, //INPUT
            LOG,
            DIALOGUE,
            DIALOGUEINPUT,
            SCENEMANGER,
            SCENETRANSITION,
            DOORWAY,
            SCENEOBJECTCLICK
        },
        {
            LOG,
            INPUT,
            DIALOGUE,
        },
        {
            DIALOGUE,
            DIALOGUEINPUT,
            LOG,
            INPUT
        },
        {
            DIALOGUEINPUT,
            DIALOGUE,
            INPUT
        },
        {
            SCENEMANGER,
            SCENETRANSITION,
        },
        {
            SCENETRANSITION,
            SCENEMANGER,
        },
        {
            DOORWAY,
            INPUT
        },
        {
            SCENEOBJECTCLICK,
            INPUT
        },
        {
            MENUS,
            DIALOGUE,
        }
    };

    public static boolean[] actives = new boolean[9]; //MUST ACCOUNT FOR NEW SYSTEMS

    public static void activateWhitelist(int sys){
        setWhitelists(sys, true);
    }

    public static void deactivateWhitelist(int sys){
        setWhitelists(sys, false);
    }

    public static void setWhitelists(int sys, boolean val){
        if(sys < actives.length)
            actives[sys] = val;
    }

    public static boolean checkPermissions(int sys){

        //check to see if the system is active, and it isn't in its whitelist.


        for(int i  = 0; i < actives.length; i++){
            if(actives[i]){
                boolean found = false;
                for(int j = 0; j < whitelists[i].length; j++){
                    if(whitelists[i][j] == sys){
                        found = true;
                        break;
                    }
                }
                if(!found)
                    return false;
            }
        }
        return true;
    }
}
