package learning;

import java.util.List;

public class TrainingStatistics<I, R> {
    LearningClassifier<I, R> classifier;
    List<TrainingExample<I, R>> testSet;

    protected int iterationNumber;
    protected int bestIterationNumber;

    protected int bestRepetitionCount;

    protected double prevAccuracy;

    protected double currentAccuracy;
    protected double bestAccuracy;

    public TrainingStatistics(LearningClassifier<I, R> classifier, List<TrainingExample<I, R>> testSet) {
        this.classifier = classifier;
        this.testSet = testSet;
        this.currentAccuracy = this.classifier.getAccuracy(this.testSet);
        this.bestAccuracy = this.currentAccuracy;
        this.bestRepetitionCount = 1;
    }

    public boolean trainingStarted() {
        return iterationNumber != 0;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }

    public double getBestIterationNumber() {
        return bestIterationNumber;
    }

    public double getPrevAccuracy() {
        return prevAccuracy;
    }

    public double getCurrentAccuracy() {
        return currentAccuracy;
    }

    public double getBestAccuracy() {
        return bestAccuracy;
    }

    public double getAccuracyChange() {
        return currentAccuracy - prevAccuracy;
    }

    public double getDeviationFromBest() {
        return bestAccuracy - currentAccuracy;
    }

    public int getBestRepetitionCount() {
        return bestRepetitionCount;
    }

    public boolean isBest() {
        return iterationNumber == bestIterationNumber;
    }

    public void advance() {
        ++iterationNumber;
        prevAccuracy = currentAccuracy;
        currentAccuracy = classifier.getAccuracy(this.testSet);

        if (bestAccuracy < currentAccuracy) {
            bestAccuracy = currentAccuracy;
            bestIterationNumber = iterationNumber;
            bestRepetitionCount = 1;
        }
        else if (bestAccuracy == currentAccuracy) {
            ++bestRepetitionCount;
        }
    }
}
