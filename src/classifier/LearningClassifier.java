package classifier;

import java.util.List;

public interface LearningClassifier<I, R> extends Classifier<I, R> {
    boolean learn(TrainingExample<I, R> example);
    boolean learn(List<TrainingExample<I, R>> learningSet);
}
