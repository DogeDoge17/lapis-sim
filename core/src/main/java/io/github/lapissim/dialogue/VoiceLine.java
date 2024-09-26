package io.github.lapissim.dialogue;

import com.badlogic.gdx.audio.Sound;
import io.github.lapissim.engine.Time;

public class VoiceLine {
    public Sound sound;
    public double soundDuration;
    public double soundTimer = 0.0;
    public boolean playing = false;
    public boolean finished = false;

    public void play(){
        sound.play();
        playing = true;
    }

    public void pause(){
        playing = false;
        sound.pause();
    }

    public void reset(){
        soundTimer = 0;
        playing = true;
        finished = true;
        sound.stop();
        sound.play();
    }

    public void update(){
        if(!playing)
            return;

        soundTimer += Time.deltaTime;

        if(soundTimer >= soundDuration) {
            finished = true;
            playing = false;
        }
    }
}
