package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.Game;
import model.Position;

public class TUI {
	
	Scanner sc;
	Game g;
	
	public TUI() {
		sc = new Scanner(System.in);
	}

	
	public int askHowManyPlayers() {
		int players = 0;
		System.out.println("Enter amount of players (Between 2 and 4): ");
		players = sc.nextInt();
		while (players < 2 || players > 4) {
			System.out.println("Not a number or not between 2 and 4, AGAIN.");
			players = sc.nextInt();
		}
		System.out.println("You picked " + players + " players.");
		return players;
	}
	
	public int askHowManyComputerPlayers() {
		int players = -1;
		while (players < 0 || players > 4) {
			System.out.println("How many computers do you want? (Between 0 and " 
					+ g.getManyPlayers() + ")");
			players = sc.nextInt();
		}
		return players;
	}
	
	public Position askPosition() {
		Position p;
		int x = -1;
		int y = -1;
		
		while (x < 1 || x > 4 && y < 1 || y > 4) {
			System.out.println("What's your x? (1-4) ");
			x = sc.nextInt();
			System.out.println("What's your y? (1-4) ");
			y = sc.nextInt();
		}
		
		p = new Position(x - 1, y - 1, 0);
		return p;
	}
	
	public ArrayList<String> askNames(Game gm) {
		ArrayList<String> names = new ArrayList<String>();
		System.out.println("Set player names:");
		
		for (int i = 0; i < g.getManyPlayers(); i++) {
			System.out.println("What's the name of Player " + (i + 1) + "?");
			String s = sc.nextLine();
			names.add(s);
		}
		return names;
	}
}
