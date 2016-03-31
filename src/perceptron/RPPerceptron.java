package perceptron;

import learning.TrainingExample;

import java.util.List;

public class RPPerceptron extends Perceptron {
    private final static double EPS = 1E-6;

    public RPPerceptron(int inputVectorSize) {
        this(inputVectorSize, DEFAULT_BIAS);
    }

    public RPPerceptron(int inputVectorSize, double bias) {
        this(Perceptrons.randomWeights(inputVectorSize), bias);
    }

    public RPPerceptron(List<Double> weights) {
        this(weights, DEFAULT_BIAS);
    }

    public RPPerceptron(List<Double> weights, double bias) {
        super(weights, bias);
    }

    @Override
    protected void correctWeights(TrainingExample<List<Double>, Boolean> example, Boolean actualResult) {
        double numerator = Vectors.multiply(weights, example.getInput()) - bias * 1.0d;
        double denominator = Vectors.multiply(example.getInput(), example.getInput()) + 1.0d * 1.0d;

        double c = Math.abs(numerator / denominator) + EPS;

        if (actualResult == true && example.getResult() == false) {
            c = -c;
        }

        Vectors.increaseBy(weights, Vectors.multiply(example.getInput(), c));
        bias = bias + c;
    }
}
