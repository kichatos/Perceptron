package classifier;

public interface Classifier <I, R> {
    R classify(I input);
}
