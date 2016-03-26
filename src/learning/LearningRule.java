package learning;

public class LearningRule {
    public enum StopReason {
        ITERATION_LIMIT_REACHED,
        ACCURACY_STABILIZED,
        DESIRED_ACCURACY_REACHED,
        ACCURACY_DECREASED,
        NO_REASON;

        @Override
        public String toString() {
            switch (this){
                case ITERATION_LIMIT_REACHED: return "Reached iteration limit";
                case ACCURACY_STABILIZED: return "Accuracy stabilized";
                case DESIRED_ACCURACY_REACHED: return "Reached desired accuracy";
                case ACCURACY_DECREASED: return "Accuracy decreased";
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

    private boolean stopOnAccuracyDecreaseEnabled;

    public LearningRule() {
        minAccuracy = 1.0;
    }

    public boolean shouldStop(int iterationNumber, double prevAccuracy, double currentAccuracy) {
        return this.getStopReason(iterationNumber, prevAccuracy, currentAccuracy) != StopReason.NO_REASON;
    }

    public StopReason getStopReason(int iterationNumber, double prevAccuracy, double currentAccuracy) {
        double accuracyChange = currentAccuracy - prevAccuracy;
        if (iterationLimitEnabled && iterationNumber == iterationLimit) {
            return StopReason.ITERATION_LIMIT_REACHED;
        }
        else if (minAccuracyChangeEnabled && iterationNumber != 0 && Math.abs(accuracyChange) < minAccuracyChange) {
            return StopReason.ACCURACY_STABILIZED;
        }
        else if (currentAccuracy >= minAccuracy) {
            return StopReason.DESIRED_ACCURACY_REACHED;
        }
        else if (stopOnAccuracyDecreaseEnabled && iterationNumber != 0 &&accuracyChange < 0) {
            return StopReason.ACCURACY_DECREASED;
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

    public LearningRule enableStopOnAccuracyDecrease() {
        this.stopOnAccuracyDecreaseEnabled = true;
        return this;
    }

    public LearningRule stopOnAccuracyDecrease() {
        this.stopOnAccuracyDecreaseEnabled = true;
        return this;
    }

    public LearningRule continueOnAccuracyDecrease() {
        this.stopOnAccuracyDecreaseEnabled = false;
        return this;
    }
}
