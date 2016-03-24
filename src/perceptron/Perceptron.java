package perceptron;

import classifier.LearningClassifier;
import classifier.TrainingExample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Perceptron implements LearningClassifier<List<Double>, Boolean> {
    public final static double DEFAULT_BIAS = 0.0;

    protected final int inputVectorSize;
    protected final Double bias;

    protected List<Double> weights;

    protected Perceptron(int inputVectorSize) {
        this(inputVectorSize, DEFAULT_BIAS);
    }

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

    public List<Double> getWeightsVector(int index) {
        if (index != 0) {
            throw new IndexOutOfBoundsException();
        }

        return new ArrayList<>(weights);
    }

    public List<Double> getWeightsVector() {
        return this.getWeightsVector(0);
    }

    public List<List<Double>> getWeightsMatrix() {
        List<List<Double>> weights = new ArrayList<>();
        weights.add(this.getWeightsVector());
        return weights;
    }

    protected abstract void correctWeights(TrainingExample<List<Double>, Boolean> example, Boolean actualResult);

    protected final boolean activationFunction(Double input) {
        return input >= bias;
    }

    @Override
    public final void learn(TrainingExample<List<Double>, Boolean> example) {
        Boolean actualResult = this.classify(example.getInput());
        if (actualResult != example.getResult()) {
            correctWeights(example, actualResult);
        }
    }

    @Override
    public final double learn(List<TrainingExample<List<Double>, Boolean>> learningSet, double desiredAccuracy) {
        if (desiredAccuracy > 1) {
            desiredAccuracy = 1;
        }

        int errorCount;
        double accuracy = 0;
        while (accuracy < desiredAccuracy) {
            Collections.shuffle(learningSet);
            errorCount = 0;

            for (TrainingExample<List<Double>, Boolean> example : learningSet) {
                if (this.classify(example.getInput()) != example.getResult()) {
                    ++errorCount;
                }

                learn(example);
            }

            accuracy = 1 - ((double) errorCount) / learningSet.size();
        }

        return accuracy;
    }

    public final double learn(List<TrainingExample<List<Double>, Boolean>> learningSet, double desiredAccuracy, int maxIterationCount) {
        if (desiredAccuracy > 1) {
            desiredAccuracy = 1;
        }

        int iteration = 0;
        int errorCount;
        double accuracy = 0;
        while (accuracy < desiredAccuracy && iteration < maxIterationCount) {
            Collections.shuffle(learningSet);
            errorCount = 0;

            for (TrainingExample<List<Double>, Boolean> example : learningSet) {
                if (this.classify(example.getInput()) != example.getResult()) {
                    ++errorCount;
                }

                learn(example);
            }

            ++iteration;
            accuracy = 1 - ((double) errorCount) / learningSet.size();
        }

        return accuracy;
    }

    @Override
    public final Boolean classify(List<Double> input) {
        if (input.size() != inputVectorSize) {
            throw new IllegalArgumentException("Can't classify a wrong-sized input vector.");
        }

        return activationFunction(Vectors.multiply(weights, input));
    }


}
