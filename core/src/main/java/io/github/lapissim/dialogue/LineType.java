package io.github.lapissim.dialogue;

public enum LineType
{
    /**
     *Takes in three inputs: Content, SpeakerID, expression
     */
    DIA,
    /**
    * Takes no arguments.
    * Returns to position before previous jump
    */
    RETURN,
    /**
     * Takes in at least two arguments.
     * All Choices go before labels
     */
    CHOICE,
    /**
     * Type anything in followed by a colon to create a label
     */
    LABEL,
    /**
     * Takes in no arguments. Makes time scaling persist across transforms.
     */
    RETAIN,
    DETAIN,
    LERP,
    SLERP,
    LINEAR,
    /**
     * Takes in three arguments. SpeakerID, X-Scale, Y-Scale. Sets the scale based off of 1 the sprite. Can be affected by time scaling.
     */
    SCALE,
    /**
     * Takes in three arguments. SpeakerID, X-Coord, Y-Coord. Adds the desired position to the sprite. Can be affected by time scaling.
     */
    TRANSLATE,
    /**
     * Takes in three arguments. SpeakerID, X-Coord, Y-Coord. Sets the absolute position of the sprite. Can be affected by time scaling.
     */
    MOVE,
    /**
     * Takes in two arguments. SpeakerID, degrees. Sets the absolute rotation of the sprite. Can be affected by time scaling.
     */
    ROTATE,
    /**
     * Takes in two arguments. SpeakerID, direction. Flips the sprite of the speaker to face desired way. -1 for flipped, 1 for not flipped. (it is recommended to have all sprites face the right).
     */
    DIR,
    /**
     * Takes in one argument: Label. Sets the pointer line pointer to the desired label.
     */
    JMP,
    /**
     * Takes in no arguments. Ends the dialogue once reached.
     */
    END,
    /**
     * Takes in two arguments: Flag name, Flag Value. Sets the global flag to have the desired value.
     */
    FLAG,
    /**
     * Takes in two arguments: SpeakerID, New Name. Replaces the existing display name with the new display name provided
     */
    NAME,
    /**
     * Takes in two arguments: Flag name, value. Compares the flag and the inline value. Typically used with either JE or JNE.
     */
    CMP,
    /**
     * Takes in one argument: destination. Jumps if the comparison is equal.
     */
    JE,
    /**
     * Takes in one argument: destination. Jumps if the comparison is NOT equal.
     */
    JNE,
}
