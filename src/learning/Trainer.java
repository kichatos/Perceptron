package learning;

public interface Trainer<I, R, C extends LearningClassifier<I, R>> {
    void train(C learningClassifier);
}
