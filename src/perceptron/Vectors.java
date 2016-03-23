package perceptron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Vectors {
    static void increaseBy(List<Double> l, List<Double> r) {
        if (l.size() != r.size()) {
            throw new IllegalArgumentException("Can't add vectors of different length");
        }

        Iterator<Double> it = r.iterator();
        for (int i = 0; i < l.size(); ++i) {
            l.set(i, l.get(i) + it.next());
        }
    }

    static List<Double> add(List<Double> l, List<Double> r) {
        if (l.size() != r.size()) {
            throw new IllegalArgumentException("Can't add vectors of different length");
        }

        List<Double> res = new ArrayList<>(l.size());
        Iterator<Double> lIt = l.iterator();
        Iterator<Double> rIt = r.iterator();

        while (lIt.hasNext()) {
            res.add(lIt.next() - rIt.next());
        }

        return res;
    }

    static void decreaseBy(List<Double> l, List<Double> r) {
        if (l.size() != r.size()) {
            throw new IllegalArgumentException("Can't subtract vectors of different length");
        }

        Iterator<Double> it = r.iterator();
        for (int i = 0; i < l.size(); ++i) {
            l.set(i, l.get(i) - it.next());
        }
    }

    static List<Double> subtract(List<Double> l, List<Double> r) {
        if (l.size() != r.size()) {
            throw new IllegalArgumentException("Can't subtract vectors of different length");
        }

        List<Double> res = new ArrayList<>(l.size());
        Iterator<Double> lIt = l.iterator();
        Iterator<Double> rIt = r.iterator();

        while (lIt.hasNext()) {
            res.add(lIt.next() - rIt.next());
        }

        return res;
    }

    static List<Double> multiply(List<Double> v, Double alpha) {
        List<Double> res = new ArrayList<>();

        for (Double aV : v) {
            res.add(alpha * aV);
        }

        return res;
    }

    static List<Double> negate(List<Double> v) {
        List<Double> res = new ArrayList<>();

        for (Double aV : v) {
            res.add(-aV);
        }

        return res;
    }

    static Double multiply(List<Double> l, List<Double> r) {
        if (l.size() != r.size()) {
            throw new IllegalArgumentException("Can't multiply vectors of different length");
        }

        Double res = 0.0;
        Iterator<Double> lIt = l.iterator();
        Iterator<Double> rIt = r.iterator();

        while (lIt.hasNext()) {
            res += lIt.next() * rIt.next();
        }

        return res;
    }
}
