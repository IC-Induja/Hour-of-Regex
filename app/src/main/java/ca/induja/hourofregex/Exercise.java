package ca.induja.hourofregex;

/**
 * Created by induj on 2016-10-11.
 */

public class Exercise {
    int mId;
    String mInstructions, mPossiblePattern;
    String[] mSearchText;

    public Exercise(int id, String instructions, String[] searchText,
                    String possiblePattern) {
        this.mId = id;
        this.mInstructions = instructions;
        this.mSearchText = searchText;
        this.mPossiblePattern = possiblePattern;
    }
}
