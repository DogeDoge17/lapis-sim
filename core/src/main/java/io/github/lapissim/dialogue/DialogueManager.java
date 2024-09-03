package io.github.lapissim.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialogueManager
{
    public static String[] rawLines;
    public static Line[] lines;
    public static String rawDialogue;
    public static int linePointer;

    public static boolean visible;


    public static HashMap<String, Integer> labels;

    private static ArrayList<Integer> stack;
    private static int compareValue;
    private static boolean retaining = false;

    public static void beginDialogue(String path)
    {
        {
            FileHandle hi = Gdx.files.internal("dialogue/" + path + ".dd");
            rawDialogue = hi.readString();
        }
        String[] unFilteredLines = rawDialogue.split("\n");
        rawLines = new String[unFilteredLines.length];
        labels = new HashMap<>();
        int count = -1;
        for(int i = 0; i < unFilteredLines.length; i++)
        {
            if(unFilteredLines[i].trim().isEmpty())
                continue;
            count++;
            rawLines[count] =  unFilteredLines[i];
        }

        lines = new Line[rawLines.length];

        for(int i = 0; i < rawLines.length; i++)
        {
            if(rawLines[i] == null) break;

            Line line = new Line();
            String[] lineArgs = splitArguments(rawLines[i]);

            line.position = i;

            switch(lineArgs[0].toLowerCase()){
                case "dia":
                    line.lineType = LineType.DIA;

                    if(lineArgs.length < 4) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.blockade = true;
                    line.contents = lineArgs[1];
                    line.speakerId = lineArgs[2];
                    line.portrait = lineArgs[3];
                    break;
                case "retain":
                    line.lineType = LineType.RETAIN;
                    break;
                case "detain":
                    line.lineType = LineType.DETAIN;
                    break;
                case "lerp":
                    line.lineType = LineType.LERP;
                    break;
                case "linear":
                    line.lineType = LineType.LINEAR;
                    break;
                case "scale":
                    if(lineArgs.length < 4) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.SCALE;
                    line.speakerId = lineArgs[1];
                    line.vector = new Vector2(Float.parseFloat(lineArgs[2]),Float.parseFloat(lineArgs[3]));
                    break;
                case "translate":
                    if(lineArgs.length < 4) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.TRANSLATE;
                    line.speakerId = lineArgs[1];
                    line.vector = new Vector2(Float.parseFloat(lineArgs[2]),Float.parseFloat(lineArgs[3]));
                    break;
                case "move":
                    if(lineArgs.length < 4) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.MOVE;
                    line.speakerId = lineArgs[1];
                    line.vector = new Vector2(Float.parseFloat(lineArgs[2]),Float.parseFloat(lineArgs[3]));
                    break;
                case "rotate":
                    if(lineArgs.length < 4) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.ROTATE;
                    line.speakerId = lineArgs[1];
                    line.vector = new Vector2(Float.parseFloat(lineArgs[2]),Float.parseFloat(lineArgs[3]));
                    break;
                case "jmp":
                    if(lineArgs.length < 2) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.JMP;
                    line.destination = lineArgs[1];
                    break;
                case "end":
                    line.lineType = LineType.END;
                    break;
                case "ret":
                    line.lineType = LineType.RETURN;
                    break;
                case "choice":
                    line.lineType = LineType.CHOICE;

                    if((lineArgs.length-1)%2 != 0){System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m");continue;}

                    float optionAmount = (lineArgs.length -1.0f)/2.0f;

                    line.choices = new String[(int)optionAmount];
                    line.destinations = new String[(int)optionAmount];
                    for(int j = 0; j < optionAmount; j++)
                    {
                        line.choices[j] = lineArgs[1+j];
                        line.destinations[j] = lineArgs[1+(int)optionAmount+j];
                    }

                    break;
                case "dir":
                    if(lineArgs.length < 2) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }
                    line.lineType = LineType.DIR;
                    line.speakerId = lineArgs[1];
                    line.flipDirection = Integer.parseInt(lineArgs[2]);
                    break;
                case "flag":
                    if(lineArgs.length < 2) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.FLAG;
                    line.flag = lineArgs[1];
                    line.value = lineArgs[2];
                    break;
                case "name":
                    if(lineArgs.length < 3) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.NAME;
                    line.speakerId = lineArgs[1];
                    line.value = lineArgs[2];
                    break;
                case "cmp":
                    if(lineArgs.length < 2) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.CMP;
                    line.flag = lineArgs[1];
                    line.value = lineArgs[2];
                    break;
                case "je":
                    if(lineArgs.length < 1) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.JE;
                    line.destination = lineArgs[1];
                    break;
                case "jne":
                    if(lineArgs.length < 1) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.JNE;
                    line.destination = lineArgs[1];
                    break;
                case "jge":
                    if(lineArgs.length < 1) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.JGE;
                    line.destination = lineArgs[1];
                    break;
                case"jle":
                    if(lineArgs.length < 1) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.JLE;
                    line.destination = lineArgs[1];
                    break;
                case "call":
                    line.lineType = LineType.CALL;
                    line.value = lineArgs[1];
                    break;
                default:
                    if(lineArgs[0].contains(":")) {
                        line.lineType = LineType.LABEL;
                        String lblName = lineArgs[0].replace(":","");
                        line.labelName = lblName;
                        labels.put(lblName, i);
                        continue;
                    }
                    System.out.println("\033[0;31mInvalid instruction: " + lineArgs[0] + "\033[0m");
                    continue;
            }
            lines[i] = line;
        }
        linePointer = -1;
    }

    public static void NextDialogue(){

    }

    public static Line getLine(){
        return lines[linePointer];
    }

    public static String[] splitArguments(String command) {
        ArrayList<String> argsList = new ArrayList<>();

        command = command.trim();

        Matcher m = Pattern.compile("([^\"]\\S*|\"[^\"]*\")\\s*").matcher(command);
        while (m.find()) {
            String argument = m.group(1).replace("\"", "").trim();
            if (!argument.isEmpty()) {
                argsList.add(argument);
            }
        }

        return argsList.toArray(new String[0]);
    }
    private static String cleanDialogue(String input){
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(input);

        StringBuilder cleanedString = new StringBuilder();

        int lastIndex = 0;

        while (matcher.find()) {
            String beforeQuote = input.substring(lastIndex, matcher.start());
            cleanedString.append(beforeQuote.replaceAll("[ \t]+", " ").trim()).append(" ");

            cleanedString.append(matcher.group()).append(" ");

            lastIndex = matcher.end();
        }
        String afterLastQuote = input.substring(lastIndex);
        cleanedString.append(afterLastQuote.replaceAll("[ \t]+", " ").trim());

        System.out.println("Original: " + input);
        System.out.println("Cleaned: " + cleanedString.toString().trim());

        return cleanedString.toString().trim();
    }
}
