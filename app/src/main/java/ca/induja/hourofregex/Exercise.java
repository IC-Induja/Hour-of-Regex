package ca.induja.hourofregex;

/**
 * Created by induj on 2016-10-11.
 */

public class Exercise {
    int mId, mInstructionsId, mPossiblePatternId;
    int[] mSearchTextIds;

    public Exercise(int id, int instructionsId, int[] searchTextIds, int possiblePatternId) {
        this.mId = id;
        this.mInstructionsId = instructionsId;
        this.mSearchTextIds = searchTextIds;
        this.mPossiblePatternId = possiblePatternId;
    }
}
