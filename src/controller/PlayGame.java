package controller;

import model.*;

import java.util.ArrayList;

/**
 * Created by jelle on 30/01/2017.
 */
public class PlayGame extends Thread {
//    TODO: Implement

    //vars

    private static ArrayList<RemotePlayer> players;
    private final Server server;
    private Board board;
    private ClientHandler handler1;
    private ClientHandler handler2;

    //constructor

    public PlayGame(RemotePlayer player1, RemotePlayer player2, Server server, ClientHandler handler1, ClientHandler handler2) {
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        this.server = server;
        board = new Board(4);
        this.handler1 = handler1;
        this.handler2 = handler2;
    }

    @Override
    public void run() {
        startGame();
    }

    void startGame() {
        String res = Protocol.GAME_START;
        for (Player player : players) {
            res += " " + player.getName();
        }
        sendToBoth(res);
    }

    public void handleMove(String[] command, Player player) {
        int x = Integer.parseInt(command[2]);
        int y = Integer.parseInt(command[3]);
        if (!(x < 0 || x > 3 || y < 0 || y > 3)) {
            y++;
            x++;
            Position result = new Position(x, y, board.getHighestZfromXY(x, y));
            Mark m = new Mark(player.getMark().getChar(), result);
            board.setMark(m);
            if (board.gameOver(player)) {
                sendToBoth(Protocol.GAME_MOVE + player + x + y);
                sendToBoth(Protocol.GAME_END + "PLAYER" + player.getName());
            } else if (board.hasDraw()) {
                sendToBoth(Protocol.GAME_MOVE + player + x + y);
                sendToBoth(Protocol.GAME_END + "DRAW");
            } else {
                String otherPlayer = ""; {
                    for (Player curPlay : players) {
                        if (!player.getName().equals(curPlay.getName())) {
                            otherPlayer= curPlay.getName();
                        }
                    }
                }
                sendToBoth(Protocol.GAME_MOVE + player + x + y + otherPlayer);
            }
        }else {
            if (handler1.getMyPlayer() == player) {
                server.sendMessage(Protocol.ERROR + 120, handler1);
            } else server.sendMessage(Protocol.ERROR + 120, handler2);
        }
    }

    private void sendToBoth(String message) {
        handler1.sendMessage(message);
        handler2.sendMessage(message);
    }
}
