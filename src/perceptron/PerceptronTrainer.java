package perceptron;

import classifier.Classifiers;
import learning.IterativeTrainer;
import learning.TrainingExample;
import learning.TrainingIteration;

import java.util.Collections;
import java.util.List;

public class PerceptronTrainer extends IterativeTrainer<List<Double>, Boolean, Perceptron> {
    protected String generateTrainingFinishedMessage(TrainingIteration iteration) {
        StringBuilder res = new StringBuilder();
        res.append(super.generateTrainingFinishedMessage(iteration)).append('\n');
        res.append("Remembered iteration #").append(iteration.getBestIterationNumber());
        res.append(". Accuracy: ").append(iteration.getBestAccuracy());
        return res.toString();
    }

    @Override
    public void train(Perceptron learningClassifier) {
        TrainingIteration iteration = new TrainingIteration();
        List<Double> bestWeights = learningClassifier.getWeightsVector();

        while (!learningRule.shouldStop(iteration)) {
            Collections.shuffle(learningSet);
            for (TrainingExample<List<Double>, Boolean> trainingExample : learningSet) {
                learningClassifier.learn(trainingExample);
            }

            iteration.advance(Classifiers.getAccuracy(learningClassifier, learningSet));
            if (iteration.isBest()) {
                bestWeights = learningClassifier.getWeightsVector();
            }

            if (loggingEnabled && iteration.getIterationNumber() % loggingFrequency == 0) {
                logger.info(this.generateIterationMessage(iteration));
            }
        }

        if (loggingEnabled) {
            logger.info(this.generateTrainingFinishedMessage(iteration));
        }

        learningClassifier.setWeightsVector(bestWeights);
    }
}
