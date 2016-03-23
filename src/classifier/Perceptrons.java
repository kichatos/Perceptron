package classifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptrons {
    private static final double EPS = 0.3;
    private static final double DEFAULT_MIN = -EPS;
    private static final double DEFAULT_MAX = EPS;

    public static List<Double> randomWeights(int inputVectorSize, double min, double max) {
        if (inputVectorSize < 0) {
            throw new IllegalArgumentException("Can't generate a weight matrix of negative length.");
        }

        List<Double> res = new ArrayList<>(inputVectorSize);
        for (int i = 0; i < inputVectorSize; ++i) {
            res.add(ThreadLocalRandom.current().nextDouble(min, max));
        }

        return res;
    }

    public static List<Double> randomWeights(int inputVectorSize) {
        return Perceptrons.randomWeights(inputVectorSize, DEFAULT_MIN, DEFAULT_MAX);
    }

    public static List<List<Double>> randomWeights(int classCount, int inputVectorSize, double min, double max) {
        if (classCount < 0) {
            throw new IllegalArgumentException("Can't generate a weight matrix of negative height.");
        }

        List<List<Double>> res = new ArrayList<>(classCount);
        for (int i = 0; i < classCount; ++i) {
            res.add(Perceptrons.randomWeights(inputVectorSize, min, max));
        }

        return res;
    }

    public static List<List<Double>> randomWeights(int classCount, int inputVectorSize) {
        return Perceptrons.randomWeights(classCount, inputVectorSize, DEFAULT_MIN, DEFAULT_MAX);
    }
}
