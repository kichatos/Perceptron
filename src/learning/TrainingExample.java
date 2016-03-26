package learning;

public class TrainingExample<I, R> {
    private I input;
    private R result;

    public TrainingExample(I input, R result) {
        this.input = input;
        this.result = result;
    }

    public I getInput() {
        return input;
    }

    public R getResult() {
        return result;
    }

    public void setInput(I input) {
        this.input = input;
    }

    public void setResult(R result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingExample<I, R> trainingExample = (TrainingExample<I, R>) o;

        if (input != null ? !input.equals(trainingExample.input) : trainingExample.input != null) return false;
        return result != null ? result.equals(trainingExample.result) : trainingExample.result == null;

    }

    @Override
    public int hashCode() {
        int result = input != null ? input.hashCode() : 0;
        result = 31 * result + (this.result != null ? this.result.hashCode() : 0);
        return result;
    }
}
