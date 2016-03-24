package perceptron;

import classifier.LearningClassifier;
import classifier.TrainingExample;

import java.util.*;

public abstract class MulticlassPerceptron<E extends Enum<E>> implements LearningClassifier<List<Double>, E> {
    protected final int inputVectorSize;
    protected final int classCount;
    protected final E[] values;
    protected final EnumMap<E, Integer> classes;
    protected List<List<Double>> weights;

    protected MulticlassPerceptron(int inputVectorSize, Class<E> enumType) {
        this(Perceptrons.randomWeights(enumType.getEnumConstants().length, inputVectorSize), enumType);
    }

    protected MulticlassPerceptron(List<List<Double>> weights, Class<E> enumType) {
        this.values = enumType.getEnumConstants();
        this.classCount = values.length;
        this.checkWeights(weights);
        this.classes = new EnumMap<>(enumType);
        this.initClasses();
        this.inputVectorSize = weights.get(0).size();
        this.weights = new ArrayList<>(classCount);
        for (List<Double> l : weights) {
            this.weights.add(new ArrayList<>(l));
        }
    }

    public int getInputVectorSize() {
        return inputVectorSize;
    }

    public int getClassCount() {
        return classCount;
    }

    public List<Double> getWeightsVector(int index) {
        return new ArrayList<>(weights.get(index));
    }

    public List<List<Double>> getWeightsMatrix() {
        List<List<Double>> res = new ArrayList<>(weights.size());

        for (List<Double> list : weights) {
            res.add(new ArrayList<>(list));
        }

        return res;
    }

    public List<Double> getWeights(E outputClass) {
        return new ArrayList<>(weights.get(classes.get(outputClass)));
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
    public final void learn(TrainingExample<List<Double>, E> example) {
        E actualResult = this.classify(example.getInput());
        if (actualResult != example.getResult()) {
            correctWeights(example, actualResult);
        }
    }

    @Override
    public final double learn(List<TrainingExample<List<Double>, E>> learningSet, double desiredAccuracy) {
        if (desiredAccuracy > 1) {
            desiredAccuracy = 1;
        }

        boolean incorrect = false;
        int errorCount;
        double accuracy = 0;
        while (accuracy < desiredAccuracy) {
            Collections.shuffle(learningSet);
            errorCount = 0;

            for (TrainingExample<List<Double>, E> example : learningSet) {
                if (this.classify(example.getInput()) != example.getResult()) {
                    ++errorCount;
                }

                learn(example);
            }

            accuracy = 1 - ((double) errorCount) / learningSet.size();
        }

        return accuracy;
    }

    public final double learn(List<TrainingExample<List<Double>, E>> learningSet, double desiredAccuracy, int maxIterationCount) {
        if (desiredAccuracy > 1) {
            desiredAccuracy = 1;
        }

        int iteration = 0;
        int errorCount;
        double accuracy = 0;
        while (accuracy < desiredAccuracy && iteration < maxIterationCount) {
            Collections.shuffle(learningSet);
            errorCount = 0;

            for (TrainingExample<List<Double>, E> example : learningSet) {
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
    public final E classify(List<Double> input) {
        if (input.size() != inputVectorSize) {
            throw new IllegalArgumentException("Can't classify a wrong-sized input vector.");
        }

        int argmax = 0;
        double max = Vectors.multiply(weights.get(argmax), input);

        for (int i = 1; i < classCount; ++i) {
            double tmp = Vectors.multiply(weights.get(i), input);

            if (tmp > max) {
                argmax = i;
                max = tmp;
            }
        }

        return values[argmax];
    }


}
