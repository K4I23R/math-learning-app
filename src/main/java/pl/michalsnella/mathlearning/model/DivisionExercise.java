package pl.michalsnella.mathlearning.model;

import java.util.Random;

public class DivisionExercise {
    private final int dividend;
    private final int divisor;
    private final int result;

    public DivisionExercise(int difficultyLevel) {
        Random random = new Random();

        int resultDigits, divisorDigits;

        switch (difficultyLevel) {
            case 0 -> { resultDigits = 2; divisorDigits = 1; }
            case 1 -> { resultDigits = 3; divisorDigits = 1; }
            case 2 -> { resultDigits = 4; divisorDigits = 1; }
            case 3 -> { resultDigits = 2; divisorDigits = 2; }
            case 4 -> { resultDigits = 3; divisorDigits = 2; }
            case 5 -> { resultDigits = 4; divisorDigits = 2; }
            default -> { resultDigits = 1; divisorDigits = 1; }
        }

        result = generateRandomNDigitNumber(random, resultDigits);
        divisor = generateRandomNDigitNumber(random, divisorDigits);
        dividend = result * divisor;

        System.out.println(dividend + " : " + divisor + " = " + result);
    }


    private int generateRandomNDigitNumber(Random random, int digits) {
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        return random.nextInt(max - min + 1) + min;
    }


    public int getResult() {
        return result;
    }


    public int getDivisor() {
        return divisor;
    }


    public int getDividend() {
        return dividend;
    }
}

