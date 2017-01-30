package view;

import exceptions.IllegalIntegerException;
import model.*;

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
        x = sc.nextInt();
        System.out.print(player.getName() + ", what's your y? (1-" + board.getBoardLength() + "): ");
        y = sc.nextInt();

        if (x < 1 || x > board.getBoardLength()) {
            throw new IllegalIntegerException(x);
        }

        if (y < 1 || y > board.getBoardLength()) {
            throw new IllegalIntegerException(y);
        }

        p = new Position(x, y);
        return p;
    }
}

