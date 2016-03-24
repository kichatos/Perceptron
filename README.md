# Perceptron
## Typical usage
```
int inputVectorLength;
List<TrainingExample<List<Double>, Boolean>> trainingSet;
// training set initialization
// ...

Perceptron p = new EPPerceptron(inputVectorLength);

double accuracy = 0.8;
p.learn(trainingSet, accuracy);

// ...

List<Double> input;

// ...

p.classify(input);
```
