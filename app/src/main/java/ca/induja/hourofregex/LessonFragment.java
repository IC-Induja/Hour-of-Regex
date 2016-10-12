package ca.induja.hourofregex;

import android.app.Activity;
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

/**
 * Created by induj on 2016-10-11.
 */

public class LessonFragment extends Fragment {

    OnIndexChangedListener mCallback;

    int mCurrentLessonId;
    TextView mLessonTextView;
    TableLayout mRegexExampleLayout;

    //TODO: Messy. Find a way to put this in new file
    public static Lesson[] mLessons = new Lesson[] {
            new Lesson(0, R.string.lesson1_title,
                    R.string.lesson1_text,
                    R.string.lesson1_pattern, new int[]{R.string.lesson1_example}),
            new Lesson(1, R.string.lesson2_title,
                    R.string.lesson2_text,
                    R.string.lesson2_pattern, new int[]{R.string.lesson2_example}),
            new Lesson(2, R.string.lesson3_title,
                    R.string.lesson3_text,
                    R.string.lesson3_pattern, new int[]{R.string
                    .lesson3_example}),
            new Lesson(3, R.string.lesson4_title, R.string.lesson4_text, R
                    .string.lesson4_pattern, new int[] {
                    R.string.lesson4_example,
                    R.string.lesson4_example2,
                    R.string.lesson4_example3}),
            new Lesson(4, R.string.lesson5_title, R.string.lesson5_text, R
                    .string.lesson5_pattern, new int[] {R.string
                    .lesson5_example}),
            new Lesson(5, R.string.lesson6_title, R.string.lesson6_text, R
                    .string.lesson6_pattern, new int[] {R.string
                    .lesson6_example, R.string.lesson6_example2}),
            new Lesson(6, R.string.lesson7_title, R.string.lesson7_text, R
                    .string.lesson7_pattern, new int[] {R.string
                    .lesson7_example}),
            new Lesson(7, R.string.lesson8_title, R.string.lesson8_text, R
                    .string.lesson8_pattern, new int[] {R.string
                    .lesson8_example}),
            new Lesson(8, R.string.lesson9_title, R.string.lesson9_text, R
                    .string.lesson9_pattern, new int[] {
                    R.string.lesson9_example,
                    R.string.lesson9_example2}),
            new Lesson(9, R.string.lesson10_title, R.string.lesson10_text,
                    R.string.lesson10_pattern, new int[] {
                    R.string.lesson10_example,
                    R.string.lesson10_example})
    };


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
        Lesson currentLesson = mLessons[lessonId];

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
                currentLesson.mSearchTextIds[0]);
        ((TextView)row.findViewById(R.id.example_match_text)).setText(highlightedMatchText);
        mRegexExampleLayout.addView(row);

        // If there are any more examples, add them in too
        int numExamples = currentLesson.mSearchTextIds.length;
        for(int i = 1; i < numExamples; i++) {
            row = (TableRow)LayoutInflater.from(getActivity()).
                    inflate(R.layout.regex_example_table_row, null);
            ((TextView)row.findViewById(R.id.example_pattern_text)).setText("");
            highlightedMatchText = highlightMatches(currentLesson.mExamplePatternId,
                    currentLesson.mSearchTextIds[i]);
            ((TextView)row.findViewById(R.id.example_match_text)).setText(highlightedMatchText);
            mRegexExampleLayout.addView(row);
        }

    }

    public Spannable highlightMatches(int patternId, int matchTextId) {
        Pattern pattern = Pattern.compile(getString(patternId));
        Matcher matcher = pattern.matcher(getString(matchTextId));
        Spannable matchedText = Spannable.Factory.getInstance().newSpannable(getString(matchTextId));
        if(matcher.find()) {
            matchedText.setSpan(new BackgroundColorSpan(0xFFFFFF00), matcher.start(),
                    matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return matchedText;
    }
}
