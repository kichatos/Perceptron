package perceptron;

import classifier.Classifiers;
import learning.IterativeTrainer;
import learning.TrainingExample;

import java.util.Collections;
import java.util.List;

public class PerceptronTrainer extends IterativeTrainer<List<Double>, Boolean, Perceptron> {
    protected String generateTrainingFinishedMessage(int iteration, double prevAccuracy, double currentAccuracy,
                                                     int bestIteration, double bestAccuracy) {
        StringBuilder res = new StringBuilder();
        res.append(super.generateTrainingFinishedMessage(iteration, prevAccuracy, currentAccuracy)).append('\n');
        res.append("Remembered iteration #").append(bestIteration).append(". Accuracy: ").append(bestAccuracy);
        return res.toString();
    }

    @Override
    public void train(Perceptron learningClassifier) {
        int iteration = 0;
        int bestIteration = 0;
        double prevAccuracy = Classifiers.getAccuracy(learningClassifier, learningSet);
        double currentAccuracy = prevAccuracy;
        double bestAccuracy = prevAccuracy;
        List<Double> bestWeights = learningClassifier.getWeightsVector();

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
                bestWeights = learningClassifier.getWeightsVector();
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

        learningClassifier.setWeightsVector(bestWeights);
    }
}
