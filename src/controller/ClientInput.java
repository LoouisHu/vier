package controller;

import java.util.Scanner;

/**
 * Created by jelle on 31/01/2017.
 */
public class ClientInput extends Thread {
    private Client client;
    private Scanner sc;

    public ClientInput(Client client) {
        this.client = client;
        sc = new Scanner(System.in);
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
                } else {
                    System.out.println("USAGE: Besides entering an x or y when asked,"
                    		+ " you can do the following:");
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
