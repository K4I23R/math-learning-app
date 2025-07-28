package pl.michalsnella.mathlearning.model;

import java.util.Random;

public class SubtractionExercise {
    private final int topNumber;
    private final int bottomNumber;
    private final int result;

    public SubtractionExercise(int difficultyLevel) {
        Random random = new Random();

        int min = (int) Math.pow(10, difficultyLevel - 1);
        int max = (int) Math.pow(10, difficultyLevel) - 1;

        int a = random.nextInt(max - min + 1) + min;
        int b = random.nextInt(max - min + 1) + min;

        topNumber = Math.max(a, b);
        bottomNumber = Math.min(a, b);
        result = topNumber - bottomNumber;
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
