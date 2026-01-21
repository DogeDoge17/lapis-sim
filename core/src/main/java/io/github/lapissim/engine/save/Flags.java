package io.github.lapissim.engine.save;

import java.util.HashMap;

public class Flags {

    public static Flags flags;

    private HashMap<String, Double> numberFlags = new HashMap<>();
    private HashMap<String, Boolean> boolFlags = new HashMap<>();
    private HashMap<String, String> stringFlags = new HashMap<>();

    public Double getDouble(String flag){
        return numberFlags.getOrDefault(flag,0.0);
    }

    public Boolean getBool(String flag){
        //if(boolFlags.containsKey(flag)){
        //}
        return boolFlags.getOrDefault(flag,false);
        //System.out.println("Could not find a flag with the name: " + flag);

        //return false;
    }

    public String getString(String flag){
        return stringFlags.getOrDefault(flag,"");
    }

    public Object getFlag(String flag)
    {
        if(boolFlags.containsKey(flag))
            return boolFlags.getOrDefault(flag,false);
        else if(numberFlags.containsKey(flag))
            return numberFlags.getOrDefault(flag,0.0);

        return stringFlags.getOrDefault(flag,"");
    }

    public void trySetFlag(String flag, String value){
        try{
            double val = Double.parseDouble(value);
            numberFlags.put(flag, val);
            return;
        }catch(Exception ex){

        }

        try{
            boolean val = Boolean.parseBoolean(value);
            boolFlags.put(flag, val);
            return;
        }catch(Exception ex){

        }


        stringFlags.put(flag, value);
    }

    public void setDouble(String flag, double value){
        numberFlags.put(flag, value);
    }

    public void setFlag(String flag, Object value){
        if(value instanceof Double)
        {
            numberFlags.put(flag, (Double)value);
            return;
        } else if (value instanceof Integer) {
            numberFlags.put(flag, ((Integer) value).doubleValue());
        }
        else if ( value instanceof Float) {
            numberFlags.put(flag, ((Float) value).doubleValue());
        }
        else if(value instanceof Boolean)
        {
            boolFlags.put(flag, (Boolean)value);
            return;
        }else if(value instanceof String) {
            stringFlags.put(flag, (String) value);
            return;
        }

        System.out.println("Type " + value.getClass().getName() + " could not be entered in as a flag. Please only input strings numbers or booleans.");
    }

    public static Object convertString(String value){
        try{
            double val = Double.parseDouble(value);

            return val;
        }catch(Exception ex){

        }

        try{
            boolean val = Boolean.parseBoolean(value);

            return val;

        }catch(Exception ex){

        }

       return value;
    }

    public void loadSave()
    {
        //Flags.flags = new Flags();

    }

}
