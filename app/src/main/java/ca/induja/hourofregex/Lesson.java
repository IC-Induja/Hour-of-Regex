package ca.induja.hourofregex;

/**
 * Created by induj on 2016-10-11.
 */

public class Lesson {
    int mId, mLessonTitleId, mLessonTextId, mExamplePatternId;
    int [] mSearchTextIds;

    public Lesson(int lessonId, int lessonTitleId, int lessonTextId, int patternId, int[] searchTextIds) {
        this.mId = lessonId;
        this.mLessonTitleId = lessonTitleId;
        this.mLessonTextId = lessonTextId;
        this.mExamplePatternId = patternId;
        this.mSearchTextIds = searchTextIds;
        }
    }

