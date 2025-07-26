package pl.michalsnella.mathlearning.model;

import java.util.Random;

public class AdditionExercise {
    private final int topNumber;
    private final int bottomNumber;
    private final int result;

    public AdditionExercise(int difficultyLevel) {
        Random random = new Random();
        int max = (int) Math.pow(10, difficultyLevel);
        int min = (int) Math.pow(10, difficultyLevel - 1);

        topNumber = random.nextInt(max - min) + min;
        bottomNumber = random.nextInt(max - min) + min;
        result = topNumber + bottomNumber;
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

