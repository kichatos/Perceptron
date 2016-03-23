package classifier;

import java.util.*;

public abstract class MulticlassPerceptron<E extends Enum<E>>
        implements LearningClassifier<List<Double>, E> {
    protected final int size;
    protected final int classCount;
    protected final E[] values;
    protected final EnumMap<E, Integer> classes;
    protected List<List<Double>> w;

    protected MulticlassPerceptron(int inputVectorSize, Class<E> enumType) {
        this(Perceptrons.randomWeights(enumType.getEnumConstants().length, inputVectorSize), enumType);
    }

    protected MulticlassPerceptron(List<List<Double>> w, Class<E> enumType) {
        this.values = enumType.getEnumConstants();
        this.classCount = values.length;
        this.checkWeights(w);
        this.classes = new EnumMap<>(enumType);
        this.initClasses();
        this.size = w.get(0).size();
        this.w = new ArrayList<>(classCount);
        for (List<Double> l : w) {
            this.w.add(new ArrayList<>(l));
        }
    }

    private void checkWeights(List<List<Double>> w) {
        if (this.classCount == 0) {
            throw new IllegalArgumentException("Can't create a multiclass perceptron with an empty class set.");
        }

        if (w.size() != this.classCount) {
            throw new IllegalArgumentException("Weight matrix height has to be equal to the number of elements in the enum.");
        }

        Iterator<List<Double>> it = w.iterator();
        List<Double> prev = it.next();
        while (it.hasNext()) {
            List<Double> tmp = it.next();
            if (tmp.size() != prev.size())  {
                throw new IllegalArgumentException("Weight matrix width has to be constant");
            }
        }

        if (w.get(0).size() == 0) {
            throw new IllegalArgumentException("Can't create a multiclass perceptron with empty input.");
        }
    }

    private void initClasses() {
        for (int i = 0; i < classCount; ++i) {
            classes.put(values[i], i);
        }
    }

    protected abstract void correctWeights(TrainingExample<List<Double>, E> example, E actualResult);

    @Override
    public final boolean learn(TrainingExample<List<Double>, E> example) {
        E actualResult = this.classify(example.getInput());
        if (actualResult != example.getResult()) {
            correctWeights(example, actualResult);
            return true;
        }

        return false;
    }

    @Override
    public final boolean learn(List<TrainingExample<List<Double>, E>> learningSet) {
        boolean incorrect = false;
        int errorCount = -1;
        while (errorCount != 0) {
            Collections.shuffle(learningSet);
            errorCount = 0;

            for (TrainingExample<List<Double>, E> example : learningSet) {
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
    public final E classify(List<Double> input) {
        if (input.size() != size) {
            throw new IllegalArgumentException("Can't classify a wrong-sized input vector.");
        }

        int argmax = 0;
        double max = Vectors.multiply(w.get(argmax), input);

        for (int i = 1; i < classCount; ++i) {
            double tmp = Vectors.multiply(w.get(i), input);

            if (tmp > max) {
                argmax = i;
                max = tmp;
            }
        }

        return values[argmax];
    }


}
