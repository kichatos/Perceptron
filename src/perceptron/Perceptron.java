package perceptron;

import learning.LearningClassifier;
import learning.LearningRule;
import learning.TrainingExample;
import java.util.logging.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Perceptron implements LearningClassifier<List<Double>, Boolean> {
    public final static Logger logger = Logger.getLogger(Perceptron.class.getName());

    protected boolean loggingEnabled;

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

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public void enableLogging() {
        setLoggingEnabled(true);
    }

    public void disableLogging() {
        setLoggingEnabled(false);
    }

    public int getInputVectorSize() {
        return inputVectorSize;
    }

    public Double getBias() {
        return bias;
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

    public void setWeightsVector(List<Double> weightsVector) {
        this.setWeightsVector(0, weightsVector);
    }

    public void setWeightsVector(int index, List<Double> weightsVector) {
        if (index != 0) {
            throw new IndexOutOfBoundsException();
        }

        if (this.inputVectorSize != weightsVector.size()) {
            throw new IllegalArgumentException("New weights vector has to be the same size.");
        }

        this.weights = new ArrayList<>(weightsVector);
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
    public final Boolean classify(List<Double> input) {
        if (input.size() != inputVectorSize) {
            throw new IllegalArgumentException("Can't classify a wrong-sized input vector.");
        }

        return activationFunction(Vectors.multiply(weights, input));
    }


}
