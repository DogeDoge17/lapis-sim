package io.github.lapissim.dialogue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.Main;
import io.github.lapissim.engine.Input;
import io.github.lapissim.engine.Time;
import io.github.lapissim.engine.permissions.PermissionManager;
import io.github.lapissim.engine.permissions.Systems;
import io.github.lapissim.engine.render.Font;
import io.github.lapissim.engine.render.TextRenderer;

import java.util.ArrayList;

public class Log
{
    public static ArrayList<Line> logList = new ArrayList<>();
    public static boolean open;

    private static float scrollAmount = 0f;
    private static float maxScroll;

    public static void update(){
        if(!PermissionManager.checkPermissions(Systems.LOG))
            return;

        if(Input.getKeyDown(com.badlogic.gdx.Input.Keys.TAB) || Input.getMouseDown(com.badlogic.gdx.Input.Buttons.BACK)) {
            open = !open;
            if(open)
                Time.setGTMulitplier(0);
            else
                Time.setGTMulitplier(1);

            PermissionManager.setWhitelists(Systems.LOG, open);
        }

        scrollAmount += Input.getScroll().y * 2000 * Time.deltaTime;
        scrollAmount -= Input.getKey(com.badlogic.gdx.Input.Keys.UP) ? 7 : 0 * Time.deltaTime;
        scrollAmount += Input.getKey(com.badlogic.gdx.Input.Keys.DOWN) ? 7 : 0 * Time.deltaTime;
    }

    public static void render(SpriteBatch batch){
        if(!open)
            return;

        batch.setColor(0,0,0,0.85f);
        batch.draw(Main.whitesSqr, 0,0, Main.SCREENWIDTH, Main.SCREENHEIGHT);
        batch.setColor(1,1,1,1f);

        TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), "LOG", 20, Main.SCREENHEIGHT-100, 36, Color.WHITE);

        Vector2 lastBounds = new Vector2();
        float drawOffset = 130 + scrollAmount;
        Font fnt = Font.fontCache.get("Comic Sans MS");
        //for(int i = 0; i < logList.size(); i++){
        for(int i = logList.size()-1; i > -1; i--){
            //int j = logList.size()-i;
            Color color = Color.PINK;
            if(i %2==0)
            color = Color.CYAN;

            drawOffset -= lastBounds.y;
            TextRenderer.enableWrapping(40);
            if(i-1 > -1)
                lastBounds = TextRenderer.measureString(fnt, "   " + logList.get(i-1).contents, 24);
            TextRenderer.drawString(batch, fnt, "   " + logList.get(i).contents, 210, drawOffset /*70 + (100*j)*/, 24, color);
            drawOffset += 45;
            TextRenderer.drawString(batch, fnt, logList.get(i).speakerId, 210, drawOffset/*115 + (100*j)*/, 28, color);
            batch.setColor(.5f,.5f,.5f,0.5f);
            drawOffset += 50;
            batch.draw(Main.whitesSqr, 200, drawOffset/*165 + (100*j)*/, 700, 2);
        }
        maxScroll = drawOffset - scrollAmount;
        scrollAmount = Math.min(maxScroll, scrollAmount);

    }
}
