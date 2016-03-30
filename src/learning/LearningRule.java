package learning;

public class LearningRule {
    public enum StopReason {
        ITERATION_LIMIT_REACHED,
        ACCURACY_STABILIZED,
        DESIRED_ACCURACY_REACHED,
        ACCURACY_DECREASED,
        DEVIATION_FROM_OPTIMUM_LIMIT_REACHED,
        NO_REASON;

        @Override
        public String toString() {
            switch (this){
                case ITERATION_LIMIT_REACHED: return "Reached iteration limit";
                case ACCURACY_STABILIZED: return "Accuracy stabilized";
                case DESIRED_ACCURACY_REACHED: return "Reached desired accuracy";
                case ACCURACY_DECREASED: return "Accuracy decreased";
                case DEVIATION_FROM_OPTIMUM_LIMIT_REACHED: return "Reached accuracy deviation from stochastic optimum limit.";
                case NO_REASON: return "No reason";
                default: return "Unknown reason";
            }
        }
    }

    private boolean iterationLimitEnabled;
    private int iterationLimit;

    private boolean minAccuracyChangeEnabled;
    private double minAccuracyChange;

    private double minAccuracy;

    private boolean maxDeviationFromOptimumEnabled;
    private double maxDeviationFromOptimum;

    private boolean stopOnAccuracyDecreaseEnabled;

    public LearningRule() {
        minAccuracy = 1.0;
    }

    public boolean shouldStop(TrainingIteration trainingIteration) {
        return this.getStopReason(trainingIteration) != StopReason.NO_REASON;
    }

    public StopReason getStopReason(TrainingIteration trainingIteration) {
        if (iterationLimitEnabled && trainingIteration.getIterationNumber() == iterationLimit) {
            return StopReason.ITERATION_LIMIT_REACHED;
        }
        else if (minAccuracyChangeEnabled && trainingIteration.trainingStarted() &&
                Math.abs(trainingIteration.getAccuracyChange()) < minAccuracyChange) {
            return StopReason.ACCURACY_STABILIZED;
        }
        else if (trainingIteration.getCurrentAccuracy() >= minAccuracy) {
            return StopReason.DESIRED_ACCURACY_REACHED;
        }
        else if (stopOnAccuracyDecreaseEnabled && trainingIteration.trainingStarted() &&
                trainingIteration.getAccuracyChange() < 0) {
            return StopReason.ACCURACY_DECREASED;
        }
        else if (maxDeviationFromOptimumEnabled && trainingIteration.trainingStarted() &&
                trainingIteration.getDeviationFromBest() > maxDeviationFromOptimum) {
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
}
