package model;

import java.util.HashMap;

/**
 * Created by jelle on 01/02/2017.
 */
public class NaiveStrategy implements Strategy {
    private String name;

    public NaiveStrategy(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Mark determineMove(Board b, Mark m) {
        Mark other = m;
        for (Mark mark : b.getPlayedMarks().values()) {
            if (!mark.equals(m)) {
                other = mark;
            }
        }
        TrumpStrategy trump = new TrumpStrategy("Trump");
        Mark best = trump.determineMove(b, m);
        for (int i = 0; i < 500; i++) {
            Board deep = b.deepCopy();
            Mark move = trump.determineMove(deep, other);
            deep.setMark(move);
            if (deep.hasFour(other) || deep.hasDiagonalFour(other)) {
                best = move;
            }
        }
        for (int i = 0; i < 600; i++) {
            Board deep = b.deepCopy();
            Mark move = trump.determineMove(deep, m);
            deep.setMark(move);
            if (deep.hasFour(m) || deep.hasDiagonalFour(m)) {
                best = move;
            }
        }
        return best;
    }
}
