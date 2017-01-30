package tests;

import exceptions.IllegalIntegerException;
import exceptions.IllegalStringException;

public class LocalGame {

	//Testing purposes
    public static void main(String[] args) throws IllegalIntegerException, IllegalStringException {
        LocalGameClass g = new LocalGameClass();
        g.start();
    }
}
