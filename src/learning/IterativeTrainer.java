package learning;

import classifier.Classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class IterativeTrainer<I, R, C extends LearningClassifier<I, R>> implements Trainer<I, R, C> {
    protected LearningRule learningRule;
    protected List<TrainingExample<I, R>> learningSet;

    protected boolean loggingEnabled;
    protected int loggingFrequency = 10;

    protected static Logger logger = Logger.getLogger(IterativeTrainer.class.getName());

    public IterativeTrainer() {
        this(new LearningRule());
    }

    public IterativeTrainer(LearningRule learningRule) {
        this(learningRule, new ArrayList<>());
    }

    public IterativeTrainer(List<TrainingExample<I, R>> learningSet) {
        this(new LearningRule(), learningSet);
    }

    public IterativeTrainer(LearningRule learningRule, List<TrainingExample<I, R>> learningSet) {
        this.learningRule = learningRule;
        this.learningSet = learningSet;
    }

    public static IterativeTrainer create() {
        return new IterativeTrainer();
    }

    public boolean getLoggingEnabled() {
        return this.loggingEnabled;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public void enableLogging() {
        setLoggingEnabled(true);
    }

    public void disableLogging() {
        setLoggingEnabled(false);
    }

    public int getLoggingFrequency() {
        return this.loggingFrequency;
    }

    public void setLoggingFrequency(int loggingFrequency) {
        this.loggingFrequency = loggingFrequency;
    }

    public LearningRule getLearningRule() {
        return this.learningRule;
    }

    public void setLearningRule(LearningRule learningRule) {
        this.learningRule = learningRule;
    }

    public List<TrainingExample<I, R>> getLearningSet() {
        return this.learningSet;
    }

    public void setLearningSet(List<TrainingExample<I, R>> learningSet) {
        this.learningSet = learningSet;
    }

    protected String generateTrainingFinishedMessage(TrainingIteration trainingIteration) {
        StringBuilder res = new StringBuilder();
        res.append("Finished training.\n");
        res.append("Reason: ").append(learningRule.getStopReason(trainingIteration)).append('\n');
        res.append("Iteration count: ").append(trainingIteration.getIterationNumber());
        return res.toString();
    }

    protected String generateIterationMessage(TrainingIteration trainingIteration) {
        return "Iteration #" + trainingIteration.getIterationNumber()
                + ". Accuracy: " + trainingIteration.getCurrentAccuracy();
    }

    public void train(C learningClassifier) {
        TrainingIteration trainingIteration = new TrainingIteration();
        while (!learningRule.shouldStop(trainingIteration)) {
            Collections.shuffle(learningSet);
            for (TrainingExample<I, R> trainingExample : learningSet) {
                learningClassifier.learn(trainingExample);
            }

            trainingIteration.advance(Classifiers.getAccuracy(learningClassifier, learningSet));

            if (loggingEnabled && trainingIteration.getIterationNumber() % loggingFrequency == 0) {
                logger.info(this.generateIterationMessage(trainingIteration));
            }
        }

        if (loggingEnabled) {
            logger.info(this.generateTrainingFinishedMessage(trainingIteration));
        }
    }
}
