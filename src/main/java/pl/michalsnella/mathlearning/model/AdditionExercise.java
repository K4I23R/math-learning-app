package pl.michalsnella.mathlearning.model;

import java.util.Random;

public class AdditionExercise {
    private final int topNumber;
    private final int bottomNumber;
    private final int result;

    public AdditionExercise(int difficultyLevel) {
        Random random = new Random();

        int topDigits, bottomDigits;

        switch (difficultyLevel) {
            case 0 -> { topDigits = 2; bottomDigits = 2; }
            case 1 -> { topDigits = 3; bottomDigits = 2; }
            case 2 -> { topDigits = 3; bottomDigits = 3; }
            case 3 -> { topDigits = 4; bottomDigits = 3; }
            case 4 -> { topDigits = 4; bottomDigits = 4; }
            case 5 -> { topDigits = 5; bottomDigits = 4; }
            case 6 -> { topDigits = 5; bottomDigits = 5; }
            default -> { topDigits = 3; bottomDigits = 1; }
        }

        topNumber = generateRandomNDigitNumber(random, topDigits);
        bottomNumber = generateRandomNDigitNumber(random, bottomDigits);
        result = topNumber + bottomNumber;
    }


    private int generateRandomNDigitNumber(Random random, int digits) {
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        return random.nextInt(max - min + 1) + min;
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
