package pl.michalsnella.mathlearning.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DivisionExercise {
    private final int dividend;
    private final int divisor;
    private final int result;
    private final List<DivisionStep> steps = new ArrayList<>();


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

        generateSteps();

        System.out.println(dividend + " : " + divisor + " = " + result);

        for (DivisionStep step : steps) {
            System.out.println("Step:");
            System.out.println("  Segment: " + step.getSegment());
            System.out.println("  Multiple: " + step.getMultiple());
            System.out.println("  Subtraction: " + step.getSubtraction());
        }
    }


    private int generateRandomNDigitNumber(Random random, int digits) {
        int min = (int) Math.pow(10, digits - 1);
        int max = (int) Math.pow(10, digits) - 1;
        return random.nextInt(max - min + 1) + min;
    }


    private void generateSteps() {
        String dividendStr = String.valueOf(dividend);
        int currentIndex = 0;
        String carry = "";

        while (currentIndex < dividendStr.length()) {
            StringBuilder segmentBuilder = new StringBuilder();

            if (!carry.isEmpty() && !carry.equals("0")) {
                segmentBuilder.append(carry);
            }

            while (currentIndex < dividendStr.length()) {
                char nextChar = dividendStr.charAt(currentIndex);

                if (segmentBuilder.isEmpty() && nextChar == '0') {
                    currentIndex++;
                    continue;
                }

                segmentBuilder.append(nextChar);
                int segment = Integer.parseInt(segmentBuilder.toString());

                if (segment >= divisor) {
                    currentIndex++;
                    break;
                }

                currentIndex++;
            }

            String segmentStr = segmentBuilder.toString();
            if (segmentStr.isEmpty() || Integer.parseInt(segmentStr) < divisor) {
                break;
            }

            int segmentInt = Integer.parseInt(segmentStr);
            int multiple = (segmentInt / divisor) * divisor;
            int subtraction = segmentInt - multiple;

            steps.add(new DivisionStep(
                    currentIndex - segmentStr.length(),
                    segmentStr,
                    String.valueOf(multiple),
                    String.valueOf(subtraction)
            ));

            carry = subtraction == 0 ? "" : String.valueOf(subtraction);
        }
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


    public List<DivisionStep> getSteps() {
        return steps;
    }
}
