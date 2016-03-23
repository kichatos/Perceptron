package classifier;

import java.util.List;

public class MulticlassEPPerceptron<E extends Enum<E>> extends MulticlassPerceptron<E> {
    private static final double EPS = 1E-6;

    public MulticlassEPPerceptron(int inputVectorSize, Class<E> enumType) {
        this(Perceptrons.randomWeights(enumType.getEnumConstants().length, inputVectorSize), enumType);
    }

    public MulticlassEPPerceptron(List<List<Double>> w, Class<E> enumType) {
        super(w, enumType);
    }

    @Override
    protected void correctWeights(TrainingExample<List<Double>, E> example, E actualResult) {
        if (example.getResult() != actualResult) {
            int expectedClass = classes.get(example.getResult());
            int actualClass = classes.get(actualResult);
            double c = Vectors.multiply(Vectors.subtract(w.get(actualClass), w.get(expectedClass)), example.getInput())
                    / Vectors.multiply(example.getInput(), example.getInput()) + EPS;

            Vectors.increaseBy(w.get(expectedClass), Vectors.multiply(example.getInput(), c));
            Vectors.decreaseBy(w.get(actualClass), Vectors.multiply(example.getInput(), c));
        }
    }
}
