package perceptron;

import classifier.Classifiers;
import learning.LearningClassifier;
import learning.Trainer;
import learning.TrainingExample;

import java.util.Collections;
import java.util.List;

public class PerceptronTrainer extends Trainer<List<Double>, Boolean> {
    protected String generateTrainingFinishedMessage(int iteration, double prevAccuracy, double currentAccuracy,
                                                     int bestIteration, double bestAccuracy) {
        StringBuilder res = new StringBuilder();
        res.append(super.generateTrainingFinishedMessage(iteration, prevAccuracy, currentAccuracy)).append('\n');
        res.append("Remembered iteration #").append(bestIteration).append(". Accuracy: ").append(bestAccuracy);
        return res.toString();
    }

    @Override
    public void train(LearningClassifier<List<Double>, Boolean> learningClassifier) {
        if (!(learningClassifier instanceof Perceptron)) {
            throw new IllegalArgumentException("Can't train a classifier different from a perceptron.");
        }

        Perceptron p = (Perceptron) learningClassifier;

        int iteration = 0;
        int bestIteration = 0;
        double prevAccuracy = Classifiers.getAccuracy(learningClassifier, learningSet);
        double currentAccuracy = prevAccuracy;
        double bestAccuracy = prevAccuracy;
        List<Double> bestWeights = p.getWeightsVector();

        while (!learningRule.shouldStop(iteration, prevAccuracy, currentAccuracy)) {
            ++iteration;
            prevAccuracy = currentAccuracy;
            Collections.shuffle(learningSet);
            for (TrainingExample<List<Double>, Boolean> trainingExample : learningSet) {
                learningClassifier.learn(trainingExample);
            }

            currentAccuracy = Classifiers.getAccuracy(learningClassifier, learningSet);
            if (currentAccuracy > bestAccuracy) {
                bestAccuracy = currentAccuracy;
                bestWeights = p.getWeightsVector();
                bestIteration = iteration;
            }

            if (loggingEnabled && iteration % loggingFrequency == 0) {
                logger.info(this.generateIterationMessage(iteration, currentAccuracy));
            }
        }

        if (loggingEnabled) {
            logger.info(this.generateTrainingFinishedMessage(iteration, prevAccuracy, currentAccuracy,
                    bestIteration, bestAccuracy));
        }

        p.setWeightsVector(bestWeights);
    }
}
