package project;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.util.ArrayList;
import java.util.List;

public class MorphAndCalculator {

    private String[] splitStringOnEqualSign (String input) {
        return input.split("=");
    }

    private String replaceXWithGivenRoot (String equation, String givenRoot) {
        equation = equation.replaceAll("X", givenRoot);
        return equation.replaceAll("x", givenRoot);
    }

    //Actual calculation is handled by Javaluator
    private boolean checkRootOfEquation (String[] input) {
        try {
        Double result1 = new DoubleEvaluator().evaluate(input[0]);
        Double result2 = new DoubleEvaluator().evaluate(input[1]);
        double scale = Math.pow(10, 9);
        result1 = Math.round(result1 * scale) / scale;
        result2 = Math.round(result2 * scale) / scale;
        return result1.equals(result2);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Seems something wrong with equation input, please try again");
        }
        return false;
    }

    public ArrayList<String> evaluateRoot (String input, String givenRoot) {
        List<String> roots = List.of(givenRoot.split(" "));
        ArrayList<String> correctRoots = new ArrayList<>();
        for (int i = 0; i < roots.toArray().length; i++) {
            String root = roots.get(i);
            String stringToEvaluate = replaceXWithGivenRoot(input, root);
            String[] dividedEquation = splitStringOnEqualSign(stringToEvaluate);
            if (checkRootOfEquation(dividedEquation)) {
                System.out.println("Root " + root + " is correct");
                correctRoots.add(root);
            } else {
                System.out.println("Root " + root + " is not correct, try another one");
            }
        }
        return correctRoots;
    }
}
