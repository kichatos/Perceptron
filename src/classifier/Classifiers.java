package classifier;

import learning.TrainingExample;

import java.util.Collection;

public class Classifiers {
    public static <I, R> double getAccuracy(Classifier<I, R> classifier, Collection<TrainingExample<I, R>> trainingSet) {
        int accurateCount = 0;
        for (TrainingExample<I, R> trainingExample : trainingSet) {
            if (classifier.classify(trainingExample.getInput()) == trainingExample.getResult()) {
                ++accurateCount;
            }
        }

        return ((double)accurateCount) / trainingSet.size();
    }
}
