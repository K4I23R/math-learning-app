package pl.michalsnella.mathlearning.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultiplicationExercise {
    private final int topNumber;
    private final int bottomNumber;
    private final int result;

    public MultiplicationExercise(int difficultyLevel) {
        Random random = new Random();

        int topDigits, bottomDigits;

        switch (difficultyLevel) {
            case 0 -> { topDigits = 2; bottomDigits = 1; }
            case 1 -> { topDigits = 3; bottomDigits = 1; }
            case 2 -> { topDigits = 4; bottomDigits = 1; }
            case 3 -> { topDigits = 2; bottomDigits = 2; }
            case 4 -> { topDigits = 3; bottomDigits = 2; }
            case 5 -> { topDigits = 4; bottomDigits = 2; }
            case 6 -> { topDigits = 3; bottomDigits = 3; }
            case 7 -> { topDigits = 4; bottomDigits = 3; }
            default -> { topDigits = 1; bottomDigits = 1; }
        }

        topNumber = generateRandomNDigitNumber(random, topDigits);
        bottomNumber = generateRandomNDigitNumber(random, bottomDigits);
        result = topNumber * bottomNumber;
    }


    private int generateRandomNDigitNumber(Random random, int digits) {
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        return random.nextInt(max - min + 1) + min;
    }


    private List<Integer> calculatePartialResults(int top, int bottom) {
        List<Integer> results = new ArrayList<>();
        String bottomStr = new StringBuilder(String.valueOf(bottom)).reverse().toString();

        for (int i = 0; i < bottomStr.length(); i++) {
            int digit = Character.getNumericValue(bottomStr.charAt(i));
            int partial = top * digit * (int) Math.pow(10, i);
            results.add(partial);
        }

        return results;
    }


    public List<String> getPartialResults() {
        List<String> partials = new ArrayList<>();
        String topStr = String.valueOf(topNumber);
        String bottomStr = String.valueOf(bottomNumber);
        int maxLength = topStr.length() + bottomStr.length();

        for (int i = bottomStr.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(bottomStr.charAt(i));
            int partial = topNumber * digit;
            String shifted = String.valueOf(partial) + "0".repeat(bottomStr.length() - 1 - i);
            String padded = String.format("%" + maxLength + "s", shifted);
            partials.add(padded);
        }

        return partials;
    }


    public int getTopNumber() {
        return topNumber;
    }


    public int getBottomNumber() {
        return bottomNumber;
    }


    public int getResult() {
        return result;
    }


    public String getResultAsString() {
        return String.valueOf(result);
    }
}
