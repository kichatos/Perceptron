package perceptron;

import classifier.TrainingExample;

import java.util.List;

public class MulticlassEPPerceptron<E extends Enum<E>> extends MulticlassPerceptron<E> {
    private static final double EPS = 1E-6;

    public MulticlassEPPerceptron(int inputVectorSize, Class<E> enumType) {
        this(Perceptrons.randomWeights(enumType.getEnumConstants().length, inputVectorSize), enumType);
    }

    public MulticlassEPPerceptron(List<List<Double>> weights, Class<E> enumType) {
        super(weights, enumType);
    }

    @Override
    protected void correctWeights(TrainingExample<List<Double>, E> example, E actualResult) {
        if (example.getResult() != actualResult) {
            int expectedClass = classes.get(example.getResult());
            int actualClass = classes.get(actualResult);
            double c = Vectors.multiply(Vectors.subtract(weights.get(actualClass), weights.get(expectedClass)), example.getInput())
                    / Vectors.multiply(example.getInput(), example.getInput()) + EPS;

            Vectors.increaseBy(weights.get(expectedClass), Vectors.multiply(example.getInput(), c));
            Vectors.decreaseBy(weights.get(actualClass), Vectors.multiply(example.getInput(), c));
        }
    }
}
