package model;

import exceptions.IllegalIntegerException;
import exceptions.IllegalStringException;

public class LocalGame {

    public static void main(String[] args) throws IllegalIntegerException, IllegalStringException {
        Game g = new Game();
        g.start();
    }
}
