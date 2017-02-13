package ca.induja.hourofregex;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ca.induja.hourofregex.Constants.Lessons;

/**
 * Created by induj on 2016-10-11.
 */

public class LessonFragment extends Fragment {

    OnIndexChangedListener mCallback;

    final int NUM_LESSONS = 10;
    int mCurrentLessonId;
    TextView mLessonTextView;
    TableLayout mRegexExampleLayout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //Check that interface was implemented
        try {
            mCallback = (OnIndexChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must " +
                    "implement OnIndexChangedListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lesson, container, false);
    }


    // Set up the lessons before the fragment is actually visible to the user
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        mLessonTextView = (TextView)getView().findViewById(R.id.lesson_text_view);
        mRegexExampleLayout = (TableLayout)getView().findViewById(R.id.regex_example_layout);
        mCurrentLessonId = mCallback.getCurrentIndex();
        setLesson(mCurrentLessonId);
    }

    @Override
    public void onStart(){
        super.onStart();
    }


    public void setLesson(int lessonId) {
        mCurrentLessonId = lessonId;
        Lesson currentLesson = Lessons[lessonId];
        String[] searchTextIds = getResources().getStringArray(currentLesson
                .mSearchTextArrayId);

        // Lesson Setup
        mLessonTextView.setText(currentLesson.mLessonTextId);

        // Regex Example Setup
        mRegexExampleLayout.removeAllViews();
        // Inflate row from layout
        TableRow row = (TableRow)LayoutInflater.from(getActivity()).
                inflate(R.layout.regex_example_table_row, null);
        // Set row1 fields with proper text
        ((TextView)row.findViewById(R.id.example_pattern_text)).
                setText(currentLesson.mExamplePatternId);
        Spannable highlightedMatchText = highlightMatches(currentLesson.mExamplePatternId,
                searchTextIds[0]);
        ((TextView)row.findViewById(R.id.example_match_text)).setText(highlightedMatchText);
        mRegexExampleLayout.addView(row);

        // If there are any more examples, add them in too
        int numExamples = searchTextIds.length;
        for(int i = 1; i < numExamples; i++) {
            row = (TableRow)LayoutInflater.from(getActivity()).
                    inflate(R.layout.regex_example_table_row, null);
            ((TextView)row.findViewById(R.id.example_pattern_text)).setText("");
            highlightedMatchText = highlightMatches(currentLesson.mExamplePatternId,
                    searchTextIds[i]);
            ((TextView)row.findViewById(R.id.example_match_text)).setText(highlightedMatchText);
            mRegexExampleLayout.addView(row);
        }

    }

    public Spannable highlightMatches(int patternId, String matchText) {
        Pattern pattern = Pattern.compile(getString(patternId));
        Matcher matcher = pattern.matcher(matchText);
        Spannable matchedText = Spannable.Factory.getInstance().newSpannable(matchText);
        if(matcher.find()) {
            matchedText.setSpan(new BackgroundColorSpan(0xFFFFFF00), matcher.start(),
                    matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return matchedText;
    }
}
