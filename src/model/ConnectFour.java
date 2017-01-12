package model;

import exceptions.IllegalIntegerException;

public class ConnectFour {

    public static void main(String[] args) throws IllegalIntegerException {
        Game g = new Game();
        g.start();
    }
}
