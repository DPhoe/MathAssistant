package project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityValidation {

    //this class might be overdone by amount of validations, and far from being optimized
    //main goal was to be as certain as possible that equation is correct

    private void checkFirstParentheses(String input) throws Exception {
        try {
            if (input.indexOf(")") < input.indexOf("(")) {
            throw new Exception("Seems like you're either missing one or added extra parentheses");
            }
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Seems like you're either missing one or added extra parentheses");
        }
    }

    private void checkIfFirstSymbolAreParentheses (String input) throws Exception {
        if (input.indexOf(")") == 0) {
            throw new Exception("Your equation starts with closing parentheses which has no sense");
        }
    }

    private void checkForExtraMathSigns(String input) throws Exception {
        input = input.replaceAll("[(]","");
        input = input.replaceAll("[)]","");
        Pattern pattern = Pattern.compile("[=+*/-][=+*/]");
        Matcher check = pattern.matcher(input);
        if (check.find() || input.contains("---")) {
            throw new Exception("Seems like there are sequential math signs in yor equation, which is not expected");
        }
    }

    private void checkForSequentialRoots(String input) throws Exception {
        input = input.replaceAll("[(]","");
        input = input.replaceAll("[)]","");
        Pattern pattern = Pattern.compile("[xX][xX]");
        Matcher check = pattern.matcher(input);
        if (check.find()) {
            throw new Exception("Seems like you have sequential root in your equation, which is not expected");
        }
    }

    private void checkForNumberOfParentheses (String input) throws Exception {
        long countOpen = input.chars().filter(ch -> ch == '(').count();
        long countClose = input.chars().filter(ch -> ch == ')').count();
        if (countOpen > countClose) {
            throw new Exception("You're missing closing parentheses");
        }
        else if (countOpen < countClose) {
            throw new Exception("You're missing opening parentheses");
        }
    }

    private void checkForMissingMathSingsBeforeClosingParentheses (String input) throws Exception {
        Pattern pattern = Pattern.compile("[0-9xX][(]");
        Matcher check = pattern.matcher(input);
        if (check.find()) {
            throw new Exception("Seems like the math sign just before one of parentheses has gone missing");
        }

    }

    private void checkForAnyUnexpectedSymbols(String input) throws Exception {
        Pattern pattern = Pattern.compile("[0-9+\\-*/()]");
        Matcher check = pattern.matcher(input);
        if (!check.find()) {
            throw new Exception("Seems like there are unexpected characters in equation");
        }
    }

    private void checkForMissingMathSingsAfterOpeningParentheses (String input) throws Exception {
        Pattern pattern = Pattern.compile("[)][0-9xX]");
        Matcher check = pattern.matcher(input);
        if (check.find()) {
            throw new Exception("Seems like the math sign just after one of parentheses has gone missing");
        }

    }

    private void checkForEmptyParentheses (String input) throws Exception {
        if (input.contains("()")) {
            throw new Exception("There is an empty parentheses in expression");
        }
    }

    private void checkForParenthesesSymmetry (String input) throws Exception {
        if (input.contains(")") || input.contains("(")) {
            if ((input.indexOf(")") > input.indexOf("("))) {
                while ((input.indexOf(")") > input.indexOf("("))) {
                    StringBuilder stringBuilder = new StringBuilder(input);
                    stringBuilder.setCharAt(input.indexOf("("), ' ');
                    stringBuilder.setCharAt(input.indexOf(")"), ' ');
                    input = stringBuilder.toString();
                    if ((input.indexOf(")") < input.indexOf("("))) {
                        throw new Exception("Your parentheses are not symmetric");
                    }
                }
            }
        }
    }

    public boolean runCheck (String input) throws Exception {
        try {
            checkForAnyUnexpectedSymbols(input);
            checkFirstParentheses(input);
            checkIfFirstSymbolAreParentheses(input);
            checkForEmptyParentheses(input);
            checkForExtraMathSigns(input);
            checkForParenthesesSymmetry(input);
            checkForNumberOfParentheses(input);
            checkForSequentialRoots(input);
            checkForMissingMathSingsAfterOpeningParentheses(input);
            checkForMissingMathSingsBeforeClosingParentheses(input);
            System.out.println("Equation seems to be correct, saving it in the DB");
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }
}
