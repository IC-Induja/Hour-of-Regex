package ca.induja.hourofregex;

/**
 * Created by induj on 2017-02-13.
 */

public final class Constants {

    private Constants() {
        // restrict instantiation
    }

    public static final int NUM_LESSONS = 16;

    // constants for lesson_text array
    public static final int LESSON_LENGTH = 3;
    public static final int TEACH_TEXT = 0;
    public static final int PATTERN = 1;
    public static final int EXAMPLE_TEXT = 2;

    // constants for exercise_text array
    public static final int EXERCISE_LENGTH = 4;
    public static final int INSTRUCTIONS = 0;
    public static final int SEARCH_TEXT = 1;
    public static final int POSSIBLE_PATTERN = 2;
    public static final int HINT = 3;

}
