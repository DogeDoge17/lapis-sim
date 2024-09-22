package io.github.lapissim.engine;

import com.badlogic.gdx.Gdx;

public class Time {
    public static float deltaTime = 0.16f;
    public static float gameTime = 0.16f;

    private static float gameTimeMulitpler = 1;

    public static long frames;

    static long last_time = System.currentTimeMillis();

    static int m_frameCounter = 0;
    static float m_timeCounter = 0.0f;
    static public float fps = 0.0f;
    static public float m_refreshTime = 0.5f;


    public static void setGTMulitplier(float multiplier){
        gameTimeMulitpler = multiplier;
    }

    public static void update(){

        long time = System.currentTimeMillis();
        //deltaTime = (time - last_time) * 100000000000L;
        deltaTime = (time - last_time) /1000f;
        gameTime = deltaTime * gameTimeMulitpler;
        last_time = time;

        if( m_timeCounter < m_refreshTime )
        {
            m_timeCounter += Time.deltaTime;
            m_frameCounter++;
        }
        else
        {
            //This code will break if you set your m_refreshTime to 0, which makes no sense.
            fps = (float)m_frameCounter/m_timeCounter;
            m_frameCounter = 0;
            m_timeCounter = 0.0f;
        }

        //deltaTime = Gdx.graphics.getDeltaTime();
        if(frames == Long.MAX_VALUE)  frames = 0;
        frames++;
    }


}
