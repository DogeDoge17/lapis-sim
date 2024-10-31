package io.github.lapissim.localization;

import com.badlogic.gdx.Gdx;
import org.hjson.JsonValue;
import org.hjson.JsonObject;

public class Language {
    private static String lang = Languages.en;

    private static JsonObject langCache;

    public static void setLang(String lang)
    {
        Language.lang = lang;
        String rawJson = Gdx.files.internal("localization/"+lang+".hjson").readString();
        langCache = JsonValue.readHjson(rawJson).asObject();
    }

    public static String getEntry(String id)
    {
        if(langCache == null)
            return id;

            return langCache.get(lang).asString();
    }
}
