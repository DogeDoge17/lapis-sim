package io.github.lapissim.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.lapissim.engine.Time;
import io.github.lapissim.engine.environment.SceneManager;
import io.github.lapissim.engine.environment.Speaker;
import io.github.lapissim.engine.render.Font;
import io.github.lapissim.engine.render.TextRenderer;

import java.util.ArrayList;
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

    private static ArrayList<Integer> stack = new ArrayList<>();
    private static int compareValue;
    private static boolean retaining = false;

    private static Texture nameplate;
    private static Texture neNameplatePas;

    private static StringBuilder displayBuilder;
    private static int displayPtr;


    /**
     * milliseconds
     */
    private static int printSpeed = 20;
    private static float countDown = 0;

    private static float skipCountdown = 100;
    private static float skipTimer = 0;

    private static Vector2 boxPos = new Vector2(0,0);

    private static FilterEntry[] diaFilter = new FilterEntry[]{new FilterEntry("\\n", "\n"), new FilterEntry("\\q", "\"")};
    public static void beginDialogue(String path)
    {
        if(nameplate == null || neNameplatePas == null){
            nameplate = new Texture("ui/dialogue-box-nameplate.png");
            neNameplatePas = new Texture("ui/dialogue-box.png");
        }

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

                    String contents = lineArgs[1];
                    for (int j = 0; j < diaFilter.length; j++) {
                        contents = contents.replace(diaFilter[j].in, diaFilter[j].out);
                    }

                    line.blockade = true;
                    line.contents = contents;
                    line.speakerId = lineArgs[2];
                    line.portrait = lineArgs[3];
                    visible = true;
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
                case "position":
                    if(lineArgs.length < 4) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.POSITION;
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
                case "visible":
                    if(lineArgs.length < 3) { System.out.println("\033[0;31mInvalid argument amount in line: " + rawLines[i] + "\033[0m"); continue; }

                    line.lineType = LineType.VISIBLE;
                    line.speakerId = lineArgs[1];
                    line.visible = Boolean.parseBoolean(lineArgs[2]);
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
                        lines[i] = line;
                        continue;
                    }
                    System.out.println("\033[0;31mInvalid instruction: " + lineArgs[0] + "\033[0m");
                    continue;
            }
            lines[i] = line;
        }
        linePointer = -1;
        NextDialogue();
    }

    public static void NextDialogue()
    {
        Line l = getLine();
        if(l != null) {
            if (displayPtr < l.contents.length()-1){
                while(addChar(l));
                skipTimer = 0;
                return;
            }
        }

        while(true){
            linePointer++;
            Line line = getLine();
            if(line == null){
                EndDialogue();
                return;
            }

            switch(line.lineType){
                case DIA:
                    displayPtr = 0;
                    displayBuilder = new StringBuilder(line.contents.length() +6);
                    return;
                case END:
                    EndDialogue();
                    break;
                case VISIBLE:
                    SceneManager.activeScene.getSpeaker(line.speakerId).setVisibility(line.visible);
                    break;
                case JMP:
                    stack.add(linePointer);
                    linePointer = labels.get(line.destination);
                    break;
                case RETURN:
                    linePointer = stack.get(stack.size()-1) +1;
                    stack.remove(stack.size()-1);
                    break;
                default:
                    continue;
            }
        }
    }

    public static void EndDialogue(){
        visible = false;
        stack = new ArrayList<>();
    }

    private static void insertNewline(){

        //Line l = getLine();
        for(int i = displayPtr; i > 0; i--)
        {
            char c = displayBuilder.charAt(i-1);
            if(c == ' '){
                if(displayBuilder.length() > i) {
                    if(displayPtr - i > 50) //stops words longer than 50 chars from wrapping and still looking bad
                        break;

                    displayBuilder.deleteCharAt(i-1);
                    displayBuilder.insert(i-1, '\n');
                }
                return;
            }
        }
        displayBuilder.append('\n');
        displayPtr++;
    }

    private static boolean addChar(Line line)
    {
        if(displayPtr >= line.contents.length()){
            return false;
        }

        displayBuilder.append(line.contents.charAt(displayPtr));
        displayPtr++;

        if(displayPtr % 50 == 0){
            insertNewline();
            //displayPtr++;
        }
        return true;
    }

    public static void updateDialogue(){
        if(!visible)
            return;

        countDown -= Time.deltaTime * 1000;
        skipTimer -= Time.deltaTime * 1000;

        if(Gdx.input.isKeyJustPressed(Input.Keys.Z) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            NextDialogue();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && skipTimer <= 0)
        {
            NextDialogue();
            skipTimer = skipCountdown;
        }

        Line line = getLine();
        if(line != null)

        if(line != null && (countDown <= 0 && displayPtr < line.contents.length())){
            addChar(line);
            countDown = printSpeed;
        }
    }

    public static void drawDialogue(SpriteBatch batch)
    {
        if(!visible)
            return;

        Line line = getLine();

        if(line.lineType != LineType.DIA && line.lineType != LineType.CHOICE)
            return;

        for (Speaker s : SceneManager.activeScene.getSpeakers())
        {
            s.speaking = false;
        }

        Speaker speaker = SceneManager.activeScene.getSpeaker(line.speakerId);
        speaker.speaking = true;

        if(speaker.emotion != line.portrait)
            speaker.setEmotion(line.portrait);

        if(line.speakerId != null) {
            batch.draw(nameplate, 0,0);
            String name = line.speakerId;
            if(speaker.displayName != null)
                name = speaker.displayName;
            TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), name, 173,270, 24, Color.WHITE);
        }
        else{
            batch.draw(neNameplatePas,0,0);
        }

        if(displayBuilder == null)
            TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), line.contents, 100,190, 24, Color.WHITE);
        else
            TextRenderer.drawString(batch, Font.fontCache.get("Comic Sans MS"), displayBuilder.toString(), 100,190, 24, Color.WHITE);
    }

    public static Line getLine(){
        if(linePointer < lines.length-1 && linePointer >= 0)
            return lines[linePointer];
        else
            return null;
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
}
class FilterEntry {
    public final String in;
    public final String out;
    public FilterEntry(String in, String out){
        this.in = in;
        this.out = out;
    }
}
