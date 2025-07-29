package pl.michalsnella.mathlearning.model;

import java.util.Random;

public class SubtractionExercise {
    private final int topNumber;
    private final int bottomNumber;
    private final int result;

    public SubtractionExercise(int difficultyLevel) {
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

        int top = generateRandomNDigitNumber(random, topDigits);
        int minBottom = (int) Math.pow(10, bottomDigits - 1);
        int maxBottom = Math.min(top - 1, (int) Math.pow(10, bottomDigits) - 1);

        if (maxBottom < minBottom) {
            top = (int) Math.pow(10, bottomDigits);
            maxBottom = top - 1;
        }

        int bottom = random.nextInt(maxBottom - minBottom + 1) + minBottom;

        this.topNumber = top;
        this.bottomNumber = bottom;
        this.result = top - bottom;
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
