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

/**
 * Created by induj on 2016-10-11.
 */

public class ExerciseFragment extends Fragment {
    private OnIndexChangedListener mCallback;
    private OnExerciseCompleteListener mExerciseComplete;

    private int mCurrentExerciseId;
    private Exercise mCurrentExercise;
    private TextView mInstructions;
    private TableLayout mSearchText;
    private EditText mRegexInput;
    private Button mEnterButton;


    //TODO: Messy. Find a way to put this in new file
    public Exercise[] mExercises = new Exercise[] {
            new Exercise(0, R.string.exercise1_instructions,
                    new int[]{R.string.exercise1_search_text1},
                    R.string.exercise1_possible_pattern),
            new Exercise(1, R.string.exercise2_instructions,
                    new int[] {
                            R.string.exercise2_search_text1,
                            R.string.exercise2_search_text2,
                            R.string.exercise2_search_text3,
                            R.string.exercise2_search_text4,
                            R.string.exercise2_search_text5
                    },
                    R.string.exercise2_possible_pattern),
            new Exercise(2,
                    R.string.exercise3_instructions,
                    new int[] {
                            R.string.exercise3_search_text1,
                            R.string.exercise3_search_text2,
                            R.string.exercise3_search_text3,
                            R.string.exercise3_search_text4,
                            R.string.exercise3_search_text5
                    },
                    R.string.exercise3_possible_pattern),
            new Exercise(3,
                    R.string.exercise4_instructions,
                    new int[] {
                            R.string.exercise4_search_text1,
                            R.string.exercise4_search_text2,
                            R.string.exercise4_search_text3,
                            R.string.exercise4_search_text4,
                            R.string.exercise4_search_text5,
                            R.string.exercise4_search_text6
                    },
                    R.string.exercise4_possible_pattern),
            new Exercise(4,
                    R.string.exercise5_instructions,
                    new int[] {
                            R.string.exercise5_search_text1,
                            R.string.exercise5_search_text2,
                            R.string.exercise5_search_text3,
                            R.string.exercise5_search_text4,
                            R.string.exercise5_search_text5,
                    },
                    R.string.exercise5_possible_pattern),
            new Exercise(5,
                    R.string.exercise6_instructions,
                    new int[] {
                            R.string.exercise6_search_text1,
                            R.string.exercise6_search_text2,
                            R.string.exercise6_search_text3,
                            R.string.exercise6_search_text4,
                            R.string.exercise6_search_text5,
                    },
                    R.string.exercise6_possible_pattern),
            new Exercise(6,
                    R.string.exercise7_instructions,
                    new int[] {
                            R.string.exercise7_search_text1,
                            R.string.exercise7_search_text2,
                            R.string.exercise7_search_text3,
                            R.string.exercise7_search_text4
                    },
                    R.string.exercise7_possible_pattern),
            new Exercise(7,
                    R.string.exercise8_instructions,
                    new int[] {
                            R.string.exercise8_search_text1,
                            R.string.exercise8_search_text2,
                            R.string.exercise8_search_text3,
                            R.string.exercise8_search_text4
                    },
                    R.string.exercise8_possible_pattern),
            new Exercise(8,
                    R.string.exercise9_instructions,
                    new int[] {
                            R.string.exercise9_search_text1,
                            R.string.exercise9_search_text2,
                            R.string.exercise9_search_text3,
                            R.string.exercise9_search_text4
                    },
                    R.string.exercise9_possible_pattern),
            new Exercise(9,
                    R.string.exercise10_instructions,
                    new int[] {
                            R.string.exercise10_search_text1,
                            R.string.exercise10_search_text2,
                            R.string.exercise10_search_text3,
                            R.string.exercise10_search_text4
                    },
                    R.string.exercise10_possible_pattern)
    };

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

        mInstructions = (TextView)getView().findViewById(R.id.instructions);
        mSearchText = (TableLayout)getView().findViewById(R.id.search_text);
        mRegexInput = (EditText)getView().findViewById(R.id.regex_input);
        mRegexInput.getBackground().setColorFilter(getResources().getColor(R.color
                        .light_green),
                PorterDuff.Mode.SRC_IN);
        mCurrentExerciseId = mCallback.getCurrentIndex();
        setExercise(mCurrentExerciseId);

        mEnterButton = (Button)getView().findViewById(R.id
                .exercise_enter_button);
        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

    }

    public void setExercise(int exerciseId) {
        mCurrentExercise = mExercises[exerciseId];

        // Instructions Setup
        mInstructions.setText(getText(mCurrentExercise.mInstructionsId));

        // Regex Input Setup
        mRegexInput.setText(null);

        // Search Text Setup
        mSearchText.removeAllViews();
        int numSearchTexts = mCurrentExercise.mSearchTextIds.length;
        for(int i = 0; i < numSearchTexts; i++) {
            // Inflate Row from layout
            TableRow row = (TableRow)LayoutInflater.from(getActivity()).
                    inflate(R.layout.search_text_table_row, null);

            // Set row fields
            ((TextView)row.findViewById(R.id.row_search_text))
                    .setText(mCurrentExercise.mSearchTextIds[i]);
            mSearchText.addView(row);

        }
    }

    public void checkAnswer() {

        String regexInput = mRegexInput.getText().toString();
        boolean allCorrect = true;

        int numRows = mSearchText.getChildCount();
        for(int i = 0; i < numRows; i++) {

            Matcher inputMatcher;
            boolean inputIsMatch;

            Matcher sampleMatcher;
            boolean sampleIsMatch;

            TextView row_text = (TextView) mSearchText.getChildAt(i)
                    .findViewById
                            (R.id.row_search_text);

            String text = getString(mCurrentExercise.mSearchTextIds[i]);
            row_text.setText(text);
            ImageView row_icon = (ImageView)mSearchText.getChildAt(i)
                    .findViewById(R.id.row_result_icon);

            try {
                Pattern inputPattern = Pattern.compile(regexInput);
                inputMatcher = inputPattern.matcher(row_text.getText());
                Pattern samplePattern = Pattern.compile(getString
                        (mCurrentExercise.mPossiblePatternId));
                sampleMatcher = samplePattern.matcher(row_text.getText());
                inputIsMatch = inputMatcher.find();
                sampleIsMatch = sampleMatcher.find();

                if(inputIsMatch) {
                    highlightText(inputMatcher.start(), inputMatcher.end(),
                            row_text, i);
                }

                if(inputIsMatch == sampleIsMatch){
                    if(!sampleIsMatch) {
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
                if(!RegexExpression.isBalanced(regexInput)) {
                    Toast.makeText(getActivity(), R.string.bad_brackets,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), R.string.bad_syntax,
                            Toast.LENGTH_SHORT).show();
                }
            }

            if(allCorrect) {
                mExerciseComplete.showNextButton();
            } else if(RegexExpression.isWrongCase(regexInput,
                    mCurrentExercise.mSearchTextIds, (Context) getActivity())) {
                Toast.makeText(getActivity(), R.string.bad_case,
                        Toast.LENGTH_SHORT).show();
            }
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
