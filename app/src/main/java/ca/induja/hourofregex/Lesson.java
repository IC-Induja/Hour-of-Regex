package ca.induja.hourofregex;

/**
 * Created by induj on 2016-10-11.
 */

public class Lesson {
    int mId;
    String mTeachText, mPattern;
    String[] mSearchText;

    public Lesson(int lessonId, String teachText, String pattern,
                  String [] searchText) {
        this.mId = lessonId;
        this.mTeachText = teachText;
        this.mPattern = pattern;
        this.mSearchText = searchText;
        }
    }

