package perceptron;

import classifier.TrainingExample;

import java.util.List;

public class EPPerceptron extends Perceptron {
    private final static double EPS = 1E-6;

    public EPPerceptron(int inputVectorSize, double bias) {
        super(inputVectorSize, bias);
    }

    public EPPerceptron(List<Double> w, double bias) {
        super(w, bias);
    }

    @Override
    protected void correctWeights(TrainingExample<List<Double>, Boolean> example, Boolean actualResult) {
        if (example.getResult() != actualResult) {
            double c = Math.abs(Vectors.multiply(w, example.getInput())
                    / Vectors.multiply(example.getInput(), example.getInput())) + EPS;
            if (example.getResult() == false) {
                c = -c;
            }

            Vectors.increaseBy(w, Vectors.multiply(example.getInput(), c));
        }
    }
}
