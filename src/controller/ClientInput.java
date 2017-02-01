package controller;

import model.Board;
import model.HumanPlayer;
import model.Player;
import model.Position;

import java.util.Scanner;

/**
 * Created by jelle on 31/01/2017.
 */
public class ClientInput extends Thread {
    private Client client;
    private Scanner sc;
    private int x = 1;
    private int y = 1;
    private boolean update = false;

    public ClientInput(Client client) {
        this.client = client;
        sc = new Scanner(System.in);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getUpdate() {
        return update;
    }

    @Override
    public void run() {
        while (true) {
            while (sc.hasNext()) {
                String msg = sc.nextLine();
                String[] split = new String[1000];
                split[0] = msg;
                if (msg.contains(" ")) {
                    split = msg.split(" ");
                }
                if (split[0].equals("exit")) {
                    client.disconnect();
                } else if (split[0].equals("chat")) {
                    client.chat(split);
                } else if (msg.equals("players")) {
                    client.players(split);
                } else if (split[0].equals("move")) {
                    if (split.length > 2) {
                        Board board;
                        try {
                            board = client.getGame().getBoard();
                        } catch (Exception e) {
                            System.out.println("Please join a game first.");
                            break;
                        }
                        try {
                            x = Integer.parseInt(split[1]);
                            y = Integer.parseInt(split[2]);
                            update = true;
                        } catch (Exception e) {
                            System.out.println("Please enter a valid command. Example: 'move 1 1'");
                        }
                        Player me = client.getGame().getPlayers().get(client.getMyNumerInGame());
                        if (x < 1 || x > board.getBoardLength() || y < 1 || y > board.getBoardLength() || (board.getHighestZfromXY(x, y) > 3)) {
                            System.out.println("Move not valid.");
                            x = 1;
                            y = 1;
                            update = false;
                        }
                        if (me instanceof HumanPlayer) {
                            ((HumanPlayer) me).setX(x);
                            ((HumanPlayer) me).setY(y);
                            ((HumanPlayer) me).setUpdate(update);
                            suspend();
                        } else {
                            System.out.println("Can't make a move for a computer player.");
                        }
                    }
                } else {
                    System.out.println("USAGE:");
                    System.out.println("When it's your turn, make a move using 'move [x] [y]'");
                    System.out.println("Private chat to a user using "
                    		+ "'chat private [username] [message]'");
                    System.out.println("Chat to all users using 'chat global [message]");
                    System.out.println("Chat to your current game using 'chat game [message]'");
                    System.out.println("Request a list of connected players using 'players all'");
                    System.out.println("Request a list of connected players "
                    		+ "that support chat using 'players chat'");
                    System.out.println("Exit the client using 'exit'");
                }
            }
        }
    }
}
