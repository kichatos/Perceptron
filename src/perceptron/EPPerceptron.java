package perceptron;

import classifier.TrainingExample;

import java.util.List;

public class EPPerceptron extends Perceptron {
    private final static double EPS = 1E-6;

    public EPPerceptron(int inputVectorSize) {
        this(inputVectorSize, DEFAULT_BIAS);
    }

    public EPPerceptron(int inputVectorSize, double bias) {
        super(inputVectorSize, bias);
    }

    public EPPerceptron(List<Double> weights, double bias) {
        super(weights, bias);
    }

    @Override
    protected void correctWeights(TrainingExample<List<Double>, Boolean> example, Boolean actualResult) {
        if (example.getResult() != actualResult) {
            double c = Math.abs(Vectors.multiply(weights, example.getInput())
                    / Vectors.multiply(example.getInput(), example.getInput())) + EPS;
            if (example.getResult() == false) {
                c = -c;
            }

            Vectors.increaseBy(weights, Vectors.multiply(example.getInput(), c));
        }
    }
}
