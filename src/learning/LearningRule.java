package learning;

public class LearningRule {
    public enum StopReason {
        ITERATION_LIMIT_REACHED,
        ACCURACY_STABILIZED,
        DESIRED_ACCURACY_REACHED,
        ACCURACY_DECREASED,
        DEVIATION_FROM_OPTIMUM_LIMIT_REACHED,
        BEST_REPETITION_LIMIT_REACHED,
        NO_REASON;

        @Override
        public String toString() {
            switch (this){
                case ITERATION_LIMIT_REACHED: return "Reached iteration limit";
                case ACCURACY_STABILIZED: return "Accuracy stabilized";
                case DESIRED_ACCURACY_REACHED: return "Reached desired accuracy";
                case ACCURACY_DECREASED: return "Accuracy decreased";
                case DEVIATION_FROM_OPTIMUM_LIMIT_REACHED: return "Reached accuracy deviation from stochastic optimum limit.";
                case BEST_REPETITION_LIMIT_REACHED: return "Best result repetition limit reached.";
                case NO_REASON: return "No reason";
                default: return "Unknown reason";
            }
        }
    }

    private boolean iterationLimitEnabled;
    private int iterationLimit;

    private boolean minAccuracyChangeEnabled;
    private double minAccuracyChange;

    private boolean bestRepetitionLimitEnabled = true;
    private int bestRepetitionLimit = 100;

    private int maxAccuracyRepetitions;

    private double minAccuracy;

    private boolean maxDeviationFromOptimumEnabled;
    private double maxDeviationFromOptimum;

    private boolean stopOnAccuracyDecreaseEnabled;

    public LearningRule() {
        minAccuracy = 1.0;
    }

    public boolean shouldStop(TrainingStatistics trainingStatistics) {
        return this.getStopReason(trainingStatistics) != StopReason.NO_REASON;
    }

    public StopReason getStopReason(TrainingStatistics trainingStatistics) {
        if (iterationLimitEnabled && trainingStatistics.getIterationNumber() == iterationLimit) {
            return StopReason.ITERATION_LIMIT_REACHED;
        }
        else if (bestRepetitionLimitEnabled && trainingStatistics.bestRepetitionCount >= bestRepetitionLimit) {
            return StopReason.BEST_REPETITION_LIMIT_REACHED;
        }
        else if (minAccuracyChangeEnabled && trainingStatistics.trainingStarted() &&
                Math.abs(trainingStatistics.getAccuracyChange()) < minAccuracyChange) {
            return StopReason.ACCURACY_STABILIZED;
        }
        else if (trainingStatistics.getCurrentAccuracy() >= minAccuracy) {
            return StopReason.DESIRED_ACCURACY_REACHED;
        }
        else if (stopOnAccuracyDecreaseEnabled && trainingStatistics.trainingStarted() &&
                trainingStatistics.getAccuracyChange() < 0) {
            return StopReason.ACCURACY_DECREASED;
        }
        else if (maxDeviationFromOptimumEnabled && trainingStatistics.trainingStarted() &&
                trainingStatistics.getDeviationFromBest() > maxDeviationFromOptimum) {
            return StopReason.DEVIATION_FROM_OPTIMUM_LIMIT_REACHED;
        }
        else {
            return StopReason.NO_REASON;
        }
    }

    public static LearningRule create() {
        return new LearningRule();
    }

    public LearningRule enableIterationLimit(int iterationLimit) {
        this.iterationLimit = iterationLimit;
        this.iterationLimitEnabled = true;
        return this;
    }

    public LearningRule disableIterationLimit() {
        this.iterationLimitEnabled = false;
        return this;
    }

    public LearningRule enableMinAccuracyChange(double minAccuracyChange) {
        this.minAccuracyChange = minAccuracyChange;
        minAccuracyChangeEnabled = true;
        return this;
    }

    public LearningRule disableMinAccuracyChange() {
        this.minAccuracyChangeEnabled = false;
        return this;
    }

    public LearningRule setMinAccuracy(double minAccuracy) {
        this.minAccuracy = minAccuracy > 0 ? (minAccuracy < 1 ? minAccuracy : 1) : 0;
        return this;
    }

    public LearningRule enableDeviationFromOptimumLimit(double maxDeviationFromOptimum) {
        this.maxDeviationFromOptimumEnabled = true;
        this.maxDeviationFromOptimum = maxDeviationFromOptimum;
        return this;
    }

    public LearningRule disableDeviationFromOptimumLimit() {
        this.maxDeviationFromOptimumEnabled = false;
        return this;
    }

    public LearningRule enableStopOnAccuracyDecrease() {
        this.stopOnAccuracyDecreaseEnabled = true;
        return this;
    }

    public LearningRule disableStopOnAccuracyDecrease() {
        this.stopOnAccuracyDecreaseEnabled = false;
        return this;
    }

    public LearningRule enableBestRepetitionLimit(int bestRepetitionLimit) {
        this.bestRepetitionLimitEnabled = true;
        this.bestRepetitionLimit = bestRepetitionLimit;
        return this;
    }

    public LearningRule disableBestRepetitionLimit() {
        this.bestRepetitionLimitEnabled = false;
        return this;
    }
}
