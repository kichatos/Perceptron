package perceptron;

import classifier.LearningClassifier;
import classifier.TrainingExample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Perceptron implements LearningClassifier<List<Double>, Boolean> {
    protected final int inputVectorSize;
    protected final Double bias;

    protected List<Double> weights;

    protected Perceptron(int inputVectorSize, Double bias) {
        this(Perceptrons.randomWeights(inputVectorSize), bias);
    }

    protected Perceptron(List<Double> weights, Double bias) {
        if (weights.size() == 0) {
            throw new IllegalArgumentException("Can't create a perceptron with empty input.");
        }

        this.inputVectorSize = weights.size();
        this.weights = new ArrayList<>(weights);
        this.bias = bias;
    }

    public int getInputVectorSize() {
        return inputVectorSize;
    }

    public Double getBias() {
        return new Double(bias);
    }

    public List<Double> getWeights() {
        return new ArrayList<>(weights);
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
        if (input.size() != inputVectorSize) {
            throw new IllegalArgumentException("Can't classify a wrong-sized input vector.");
        }

        return activationFunction(Vectors.multiply(weights, input));
    }


}
