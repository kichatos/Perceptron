package perceptron;

import classifier.LearningClassifier;
import classifier.TrainingExample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Perceptron implements LearningClassifier<List<Double>, Boolean> {
    protected final int size;
    protected final Double bias;

    protected ArrayList<Double> w;

    protected Perceptron(int inputVectorSize, Double bias) {
        this(Perceptrons.randomWeights(inputVectorSize), bias);
    }

    protected Perceptron(List<Double> w, Double bias) {
        if (w.size() == 0) {
            throw new IllegalArgumentException("Can't create a perceptron with empty input.");
        }

        this.size = w.size();
        this.w = new ArrayList<>(w);
        this.bias = bias;
    }

    protected abstract void correctWeights(TrainingExample<List<Double>, Boolean> example, Boolean actualResult);

    protected final boolean activationFunction(Double input) {
        return input >= bias;
    }

    @Override
    public final boolean learn(TrainingExample<List<Double>, Boolean> example) {
        Boolean actualResult = this.classify(example.getInput());
        if (actualResult != example.getResult()) {
            correctWeights(example, actualResult);
            return true;
        }

        return false;
    }

    @Override
    public final boolean learn(List<TrainingExample<List<Double>, Boolean>> learningSet) {
        boolean incorrect = false;
        int errorCount = -1;
        while (errorCount != 0) {
            Collections.shuffle(learningSet);
            errorCount = 0;

            for (TrainingExample<List<Double>, Boolean> example : learningSet) {
                if (learn(example)) {
                    ++errorCount;
                }
            }

            if (errorCount != 0) {
                incorrect = true;
            }
        }

        return incorrect;
    }

    @Override
    public final Boolean classify(List<Double> input) {
        if (input.size() != size) {
            throw new IllegalArgumentException("Can't classify a wrong-sized input vector.");
        }

        return activationFunction(Vectors.multiply(w, input));
    }


}
