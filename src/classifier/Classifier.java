package classifier;

import learning.TrainingExample;

import java.util.List;

public interface Classifier <I, R> {
    R classify(I input);

    default double getAccuracy(List<TrainingExample<I, R>> testSet) {
        int accurateCount = 0;
        for (TrainingExample<I, R> trainingExample : testSet) {
            if (this.classify(trainingExample.getInput()) == trainingExample.getResult()) {
                ++accurateCount;
            }
        }

        return ((double)accurateCount) / testSet.size();
    }
}
