package perceptron;

import learning.TrainingExample;

import java.util.List;

public class RPPerceptron extends Perceptron {
    private final static double EPS = 1E-6;

    public RPPerceptron(int inputVectorSize) {
        this(inputVectorSize, DEFAULT_BIAS);
    }

    public RPPerceptron(int inputVectorSize, double bias) {
        super(inputVectorSize, bias);
    }

    public RPPerceptron(List<Double> weights, double bias) {
        super(weights, bias);
    }

    public void setBiasCorrectionEnabled(boolean biasCorrectionEnabled) {
        this.biasCorrectionEnabled = biasCorrectionEnabled;
    }

    @Override
    protected void correctWeights(TrainingExample<List<Double>, Boolean> example, Boolean actualResult) {
        if (!Vectors.isZero(example.getInput())) {
            double c = Math.abs(Vectors.multiply(weights, example.getInput())
                    / Vectors.multiply(example.getInput(), example.getInput())) + EPS;
            if (Double.isNaN(c)) {
                if (loggingEnabled) {
                    logger.severe("Skipped weight correction for NaN correction constant.");
                }
                return;
            }

            if (example.getResult() == false) {
                c = -c;
                if (biasCorrectionEnabled) {
                    ++bias;
                }
            }
            else {
                if (biasCorrectionEnabled) {
                    --bias;
                }
            }

            Vectors.increaseBy(weights, Vectors.multiply(example.getInput(), c));
        }
        else {
            if (loggingEnabled) {
                logger.info("Skipped weight correction for a zero input");
            }
        }
    }
}
