package ca.induja.hourofregex;

/**
 * Created by induj on 2017-02-13.
 */

public final class Constants {

    private Constants() {
        // restrict instantiation
    }

    //TODO: Messy. Find a way to do this where code is not repeated.
    public static final Lesson[] Lessons = new Lesson[] {
            new Lesson(0, R.string.lesson1_title,
                    R.string.lesson1_text,
                    R.string.lesson1_pattern,
                    R.array.lesson1_examples),
            new Lesson(1, R.string.lesson2_title,
                    R.string.lesson2_text,
                    R.string.lesson2_pattern,
                    R.array.lesson2_examples),
            new Lesson(2, R.string.lesson3_title,
                    R.string.lesson3_text,
                    R.string.lesson3_pattern,
                    R.array.lesson3_examples),
            new Lesson(3, R.string.lesson4_title,
                    R.string.lesson4_text,
                    R.string.lesson4_pattern,
                    R.array.lesson4_examples),
            new Lesson(4, R.string.lesson5_title,
                    R.string.lesson5_text,
                    R.string.lesson5_pattern,
                    R.array.lesson5_examples),
            new Lesson(5, R.string.lesson6_title,
                    R.string.lesson6_text,
                    R.string.lesson6_pattern,
                    R.array.lesson6_examples),
            new Lesson(6, R.string.lesson7_title,
                    R.string.lesson7_text,
                    R.string.lesson7_pattern,
                    R.array.lesson7_examples),
            new Lesson(7, R.string.lesson8_title,
                    R.string.lesson8_text,
                    R.string.lesson8_pattern,
                    R.array.lesson8_examples),
            new Lesson(8, R.string.lesson9_title,
                    R.string.lesson9_text,
                    R.string.lesson9_pattern,
                    R.array.lesson9_examples),
            new Lesson(9, R.string.lesson10_title,
                    R.string.lesson10_text,
                    R.string.lesson10_pattern,
                    R.array.lesson10_examples)
    };
}
