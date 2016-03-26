package learning;

import classifier.Classifier;

public interface LearningClassifier<I, R> extends Classifier<I, R> {
    void learn(TrainingExample<I, R> example);
}
