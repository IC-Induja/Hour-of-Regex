package ca.induja.hourofregex;

import android.content.Context;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by induj on 2016-10-11.
 */

public class RegexExpression {
    Pattern Regex;


    public static boolean isBalanced(String regex) {
        int len = regex.length();
        ArrayList<Character> stack = new ArrayList<Character>();

        for (int i = 0; i < len; i++) {
            char c = regex.charAt(i);

            if (c == '{' || c == '[' || c == '(') {
                stack.add(c);
            }

            if (c == '}' || c == ']' || c == ')') {
                // Expression has more closing parentheses than opening ones
                if (stack.isEmpty()) {
                    return false;
                }
                // Opening and Closing Parentheses don't match
                Character removed_char = stack.remove(stack.size() -1);
                if (removed_char == '{' && c != '}' ||
                        removed_char == '[' && c != ']' ||
                        removed_char == '(' && c != ')') {
                    return false;
                }
            }
        }
        // Expression has more opening parenthesis than closing ones
        if (!stack.isEmpty()) {
            return false;
        }

        return true;
    }

    // returns true is expression is correct in everything but case
    public static boolean isWrongCase(String input, Exercise exercise,
                                      Context context) {
        String[] matchText = exercise.mSearchText;
        int numRows = matchText.length;
        Pattern caseInsensitivePattern =  Pattern.compile(input, Pattern
                .CASE_INSENSITIVE);
        Pattern correctPattern = Pattern.compile(exercise.mPossiblePattern);

        boolean sameMatch = true;
        for(int i = 0; i < numRows; ++i) {
            if(correctPattern.matcher(matchText[i]).find() !=
                    caseInsensitivePattern.matcher(matchText[i]).find()) {
                sameMatch = false;
                break;
            }
        }

        return sameMatch;
    }
}
