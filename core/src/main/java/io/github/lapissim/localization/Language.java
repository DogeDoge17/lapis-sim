package io.github.lapissim.localization;

import com.badlogic.gdx.Gdx;
import org.hjson.JsonValue;
import org.hjson.JsonObject;

public class Language {
    private static String lang = null;

    private static JsonObject langCache;

    public static void setLang(String lang)
    {
        Language.lang = lang;
        String rawJson = Gdx.files.internal("localization/" + lang + ".hjson").readString();
        langCache = JsonValue.readHjson(rawJson).asObject();
    }

    public static String getEntry(String id)
    {
        if(langCache == null)
            return id;

        // Split the keyPath (e.g., "pro.foo.enter") into parts
        String[] keys = id.split("\\.");

        // Traverse the HJSON structure
        JsonValue currentValue = langCache;
        for (String key : keys) {
            if (currentValue.isObject()) {
                currentValue = currentValue.asObject().get(key);
            } else {
                return id; // If we can't go further, return null
            }
        }

        return currentValue != null ? currentValue.asString() : id;
    }
}
