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

import ca.induja.hourofregex.Constants;

/**
 * Created by induj on 2016-10-11.
 */

public class LessonFragment extends Fragment {

    OnIndexChangedListener mCallback;

    private String[] mLessonText;
    private Lesson mCurrentLesson;
    private TextView mLessonTextView;
    private TableLayout mRegexExampleLayout;

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

        mLessonText = getResources().getStringArray(R.array.lesson_text);
        mLessonTextView = (TextView)getView().findViewById(R.id.lesson_text_view);
        mRegexExampleLayout = (TableLayout)getView().findViewById(R.id.regex_example_layout);
        int lessonId = mCallback.getCurrentIndex();
        setLesson(lessonId);
    }

    @Override
    public void onStart(){
        super.onStart();
    }


    public void setLesson(int lessonId) {
        int curIndex = lessonId * Constants.LESSON_LENGTH;
        String teachText = mLessonText[curIndex + Constants.TEACH_TEXT];
        String pattern = mLessonText[curIndex + Constants.PATTERN];
        String[] exampleText = mLessonText[curIndex + Constants.EXAMPLE_TEXT].split("~");
        mCurrentLesson = new Lesson(lessonId, teachText, pattern, exampleText);

        // Lesson Setup
        mLessonTextView.setText(mCurrentLesson.mTeachText);

        // Regex Example Setup
        mRegexExampleLayout.removeAllViews();

//        // TODO: remove repeated code
//        // Inflate row from layout
//        TableRow row = (TableRow)LayoutInflater.from(getActivity()).
//                inflate(R.layout.regex_example_table_row, null);
//        // Set row1 fields with proper text
//        ((TextView)row.findViewById(R.id.example_pattern_text)).
//                setText(mCurrentLesson.mPattern);
//        Spannable highlightedMatchText = highlightMatches(mCurrentLesson.mPattern,
//                mCurrentLesson.mSearchText[0]);
//        ((TextView)row.findViewById(R.id.example_match_text)).setText(highlightedMatchText);
//        mRegexExampleLayout.addView(row);

        // If there are any more examples, add them in too
        int numExamples = mCurrentLesson.mSearchText.length;
        for(int i = 0; i < numExamples; i++) {
            // Inflate row from layout
            TableRow row = (TableRow)LayoutInflater.from(getActivity()).
                    inflate(R.layout.regex_example_table_row, null);
            // Set row fields with proper text
            String examplePattern;
            if(i == 0) {
                examplePattern = mCurrentLesson.mPattern;
            } else {
                examplePattern = "";
            }
            ((TextView)row.findViewById(R.id.example_pattern_text)).setText(examplePattern);
            Spannable highlightedMatchText = highlightMatches(mCurrentLesson.mPattern,
                    mCurrentLesson.mSearchText[i]);
            ((TextView)row.findViewById(R.id.example_match_text)).setText(highlightedMatchText);
            mRegexExampleLayout.addView(row);
        }

    }

    public Spannable highlightMatches(String patternText, String matchText) {
        Pattern pattern = Pattern.compile(patternText);
        Matcher matcher = pattern.matcher(matchText);
        Spannable matchedText = Spannable.Factory.getInstance().newSpannable(matchText);
        if(matcher.find()) {
            matchedText.setSpan(new BackgroundColorSpan(0xFFFFFF00), matcher.start(),
                    matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return matchedText;
    }
}
