package view;

import exceptions.IllegalIntegerException;
import model.*;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by jelle on 30/01/2017.
 */
public class TUI {

    Scanner sc;

    public TUI() {
        sc = new Scanner(System.in);
    }

    public int askInt(String question) {
        int res = 0;
        System.out.println(question);
        res = sc.nextInt();
        return res;
    }

    public String askString(String question) {
        String res = "";
        System.out.println(question);
        res = sc.nextLine();
        return res;
    }

    public void updateGameState(Board board) {
        System.out.println("\nCurrent game situation: \n\n" + board.toString());
    }

    public Position askPosition(Player player, Board board) throws IllegalIntegerException {
        Position p;
        int x = -1;
        int y = -1;

        System.out.print(player.getName() + ", what's your x? (1-" + board.getBoardLength() + "): ");
        try {
            x = sc.nextInt();
            System.out.print(player.getName() + ", what's your y? (1-" + board.getBoardLength() + "): ");
            y = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter an integer instead of something else.");
            Position temp = askPosition(player, board);
            x = temp.getX();
            y = temp.getY();
        }

        if (x < 1 || x > board.getBoardLength() || y < 1 || y > board.getBoardLength() || (board.getHighestZfromXY(x, y) > 3)) {
            System.out.println("Move not valid.");
            Position temp = askPosition(player, board);
            x = temp.getX();
            y = temp.getY();
        }

        p = new Position(x, y);
        return p;
    }
}

