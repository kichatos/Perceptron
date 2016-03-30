package learning;

public class TrainingIteration {
    protected int iterationNumber;
    protected int bestIterationNumber;

    protected double prevAccuracy;
    protected double currentAccuracy;
    protected double bestAccuracy;

    public TrainingIteration() {

    }

    public TrainingIteration(TrainingIteration trainingIteration) {
        this(trainingIteration.iterationNumber, trainingIteration.bestIterationNumber, trainingIteration.prevAccuracy,
                trainingIteration.currentAccuracy, trainingIteration.bestAccuracy);
    }

    public TrainingIteration(int iterationNumber, int bestIterationNumber,
                             double prevAccuracy, double currentAccuracy, double bestAccuracy) {
        this.iterationNumber = iterationNumber;
        this.bestIterationNumber = bestIterationNumber;
        this.prevAccuracy = prevAccuracy;
        this.currentAccuracy = currentAccuracy;
        this.bestAccuracy = bestAccuracy;
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

    public boolean isBest() {
        return iterationNumber == bestIterationNumber;
    }

    public void advance(double newAccuracy) {
        ++iterationNumber;
        prevAccuracy = currentAccuracy;
        currentAccuracy = newAccuracy;

        if (bestAccuracy < currentAccuracy) {
            bestAccuracy = currentAccuracy;
            bestIterationNumber = iterationNumber;
        }
    }
}
