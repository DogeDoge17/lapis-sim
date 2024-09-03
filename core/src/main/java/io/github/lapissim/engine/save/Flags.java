package io.github.lapissim.engine.save;

import java.util.HashMap;

public class Flags {
    private HashMap<String, Double> numberFlags;
    private HashMap<String, Boolean> boolFlags;
    private HashMap<String, String> stringFlags;

    public Double getDouble(String flag){
        if(numberFlags.containsKey(flag)){
            return numberFlags.getOrDefault(flag,0.0);
        }
        System.out.println("Could not find a flag with the name: " + flag);
        return 0.0;
    }

    public Boolean getBool(String flag){
        if(boolFlags.containsKey(flag)){
            return boolFlags.getOrDefault(flag,false);
        }
        System.out.println("Could not find a flag with the name: " + flag);

        return false;
    }

    public String getString(String flag){
        if(stringFlags.containsKey(flag)){
            return stringFlags.getOrDefault(flag,"");
        }
        System.out.println("Could not find a flag with the name: " + flag);

        return "";
    }

    public Object getFlag(String flag)
    {
        if(boolFlags.containsKey(flag))
            return boolFlags.getOrDefault(flag,false);
        else if(stringFlags.containsKey(flag))
            return stringFlags.getOrDefault(flag,"");
        else if(numberFlags.containsKey(flag))
            return numberFlags.getOrDefault(flag,0.0);

        System.out.println("Could not find a flag with the name: " + flag);
        return null;
    }

    public void setFlag(String flag, Object value){
        if(boolFlags.containsKey(flag)) {
            System.out.println("Flag " + flag + " already exists as a bool");
            return;
        }
        else if(stringFlags.containsKey(flag))
        {
            System.out.println("Flag " + flag + " already exists as a string");
            return;
        }
        else if(numberFlags.containsKey(flag))
        {
            System.out.println("Flag " + flag + " already exists as a double");
            return;
        }

        if(value instanceof Double || value instanceof Integer || value instanceof Float)
        {
            numberFlags.putIfAbsent(flag, (Double)value);
        }else if(value instanceof Boolean)
        {
            boolFlags.putIfAbsent(flag, (Boolean)value);
        }else if(value instanceof String) {
            stringFlags.putIfAbsent(flag, (String) value);
        }

        System.out.println("Type " + value.getClass().getName() + " could not be entered in as a flag. Please only input strings numbers or booleans.");

    }

}
