package ca.induja.hourofregex;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import ca.induja.hourofregex.Constants;
/**
 * Created by induj on 2016-10-11.
 */

public class ExerciseFragment extends Fragment {
    private OnIndexChangedListener mCallback;
    private OnExerciseCompleteListener mExerciseComplete;

    private String[] mExerciseText;
    private Exercise mCurrentExercise;
    private TextView mInstructions;
    private TableLayout mSearchText;
    private EditText mRegexInput;
    private Button mEnterButton;


    public interface OnExerciseCompleteListener {
        void showNextButton();
    }

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

        try {
            mExerciseComplete = (OnExerciseCompleteListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must " +
                    "implement OnExerciseCompleteListener");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        mExerciseText = getResources().getStringArray(R.array.exercise_text);
        mInstructions = (TextView)getView().findViewById(R.id.instructions);
        mSearchText = (TableLayout)getView().findViewById(R.id.search_text);
        mRegexInput = (EditText)getView().findViewById(R.id.regex_input);
        mRegexInput.getBackground().setColorFilter(getResources()
                .getColor(R.color.light_green), PorterDuff.Mode.SRC_IN);
        int exerciseId = mCallback.getCurrentIndex();
        setExercise(exerciseId);

        mEnterButton = (Button)getView().findViewById(R.id.exercise_enter_button);
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

    }

    public void setExercise(int exerciseId) {
        int curIndex = exerciseId * Constants.EXERCISE_LENGTH;
        String instructions = mExerciseText[curIndex + Constants.INSTRUCTIONS];
        String[] searchText = mExerciseText[curIndex + Constants.SEARCH_TEXT].split("~");
        String possiblePattern = mExerciseText[curIndex + Constants.POSSIBLE_PATTERN];
        mCurrentExercise = new Exercise(exerciseId, instructions, searchText,
                possiblePattern);

        // Instructions Setup
        mInstructions.setText(instructions);

        // Regex Input Setup
        mRegexInput.setText(null);

        // Search Text Setup
        mSearchText.removeAllViews();
        int numSearchTexts = searchText.length;
        for(int i = 0; i < numSearchTexts; i++) {
            // Inflate Row from layout
            TableRow row = (TableRow)LayoutInflater.from(getActivity()).
                    inflate(R.layout.search_text_table_row, null);

            // Set row fields
            ((TextView)row.findViewById(R.id.row_search_text))
                    .setText(searchText[i]);
            mSearchText.addView(row);

        }
    }

    public void checkAnswer() {

        String regexInput = mRegexInput.getText().toString();
        boolean allCorrect = true;
        String[] searchTextArray = mCurrentExercise.mSearchText;

        int numRows = mSearchText.getChildCount();
        for(int i = 0; i < numRows; i++) {

            Matcher inputMatcher;
            boolean inputIsMatch;

            Matcher sampleMatcher;
            boolean sampleIsMatch;

            TextView row_text = (TextView) mSearchText.getChildAt(i)
                    .findViewById
                            (R.id.row_search_text);

            String text = searchTextArray[i];
            row_text.setText(text);
            ImageView row_icon = (ImageView) mSearchText.getChildAt(i)
                    .findViewById(R.id.row_result_icon);

            try {
                Pattern inputPattern = Pattern.compile(regexInput);
                inputMatcher = inputPattern.matcher(row_text.getText());
                Pattern samplePattern = Pattern
                        .compile(mCurrentExercise.mPossiblePattern);
                sampleMatcher = samplePattern.matcher(row_text.getText());
                inputIsMatch = inputMatcher.find();
                sampleIsMatch = sampleMatcher.find();

                if (inputIsMatch) {
                    highlightText(inputMatcher.start(), inputMatcher.end(),
                            row_text, i);
                }

                if (inputIsMatch == sampleIsMatch) {
                    if (!sampleIsMatch) {
                        // set right icon
                        Drawable icon = getResources().getDrawable(R.drawable
                                .right_icon);
                        row_icon.setImageDrawable(icon);
                    } else if (inputMatcher.start() == sampleMatcher.start() &&
                            inputMatcher.end() == sampleMatcher.end()) {
                        // set right icon
                        Drawable icon = getResources().getDrawable(R.drawable
                                .right_icon);
                        row_icon.setImageDrawable(icon);
                    } else {
                        // set wrong icon
                        Drawable icon = getResources().getDrawable(R.drawable
                                .wrong_icon);
                        row_icon.setImageDrawable(icon);

                        allCorrect = false;
                    }
                } else {
                    // set wrong icon
                    Drawable icon = getResources().getDrawable(R.drawable
                            .wrong_icon);
                    row_icon.setImageDrawable(icon);

                    allCorrect = false;
                }

            } catch (PatternSyntaxException e) {
                if (!RegexExpression.isBalanced(regexInput)) {
                    Toast.makeText(getActivity(), R.string.bad_brackets,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.bad_syntax,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }

        if(allCorrect) {
            mExerciseComplete.showNextButton();
        } else if(RegexExpression.isWrongCase(regexInput,
                mCurrentExercise, (Context) getActivity())) {
            Toast.makeText(getActivity(), R.string.bad_case,
                    Toast.LENGTH_SHORT).show();
        }



    }

    private void highlightText(int start, int end, TextView textView, int
            index) {
        Spannable spannable = Spannable.Factory.getInstance()
                .newSpannable(textView.getText());
        spannable.setSpan(new BackgroundColorSpan(getResources().getColor(R
                        .color.highlight)), start,
                end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }

}
