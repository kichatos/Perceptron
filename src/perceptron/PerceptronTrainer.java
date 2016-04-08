package perceptron;

import learning.IterativeTrainer;
import learning.TrainingExample;
import learning.TrainingStatistics;

import java.util.Collections;
import java.util.List;

public class PerceptronTrainer extends IterativeTrainer<List<Double>, Boolean, Perceptron> {
    protected String generateTrainingFinishedMessage(TrainingStatistics iteration) {
        StringBuilder res = new StringBuilder();
        res.append(super.generateTrainingFinishedMessage(iteration)).append('\n');
        res.append("Remembered iteration #").append(iteration.getBestIterationNumber());
        res.append(". Accuracy: ").append(iteration.getBestAccuracy());
        return res.toString();
    }

    @Override
    public void train(Perceptron learningClassifier) {
        TrainingStatistics<List<Double>, Boolean> trainingStatistics =
                new TrainingStatistics<>(learningClassifier, learningSet);
        List<Double> bestWeights = learningClassifier.getWeightsVector();

        while (!learningRule.shouldStop(trainingStatistics)) {
            Collections.shuffle(learningSet);
            for (TrainingExample<List<Double>, Boolean> trainingExample : learningSet) {
                learningClassifier.learn(trainingExample);
            }

            trainingStatistics.advance();
            if (trainingStatistics.isBest()) {
                bestWeights = learningClassifier.getWeightsVector();
            }

            if (loggingEnabled && trainingStatistics.getIterationNumber() % loggingFrequency == 0) {
                logger.info(this.generateIterationMessage(trainingStatistics));
            }
        }

        if (loggingEnabled) {
            logger.info(this.generateTrainingFinishedMessage(trainingStatistics));
        }

        learningClassifier.setWeightsVector(bestWeights);
    }
}
