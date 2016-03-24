package classifier;

import java.util.List;

public interface LearningClassifier<I, R> extends Classifier<I, R> {
    void learn(TrainingExample<I, R> example);
    double learn(List<TrainingExample<I, R>> learningSet, double desiredAccuracy);
}
