package view;

import java.util.ArrayList;
import java.util.Scanner;

import exceptions.IllegalIntegerException;
import model.Game;
import model.Position;
import model.Strategy;
import model.TrumpStrategy;

//TODO Throw exceptions, maybe? Wrong inputs can go wrong.

public class TUI {
	
	Scanner sc;
	
	public TUI() {
		sc = new Scanner(System.in);
	}

	
	public int askHowManyPlayers() throws IllegalIntegerException {
		int players = 0;
		System.out.print("Enter amount of players (Between 2 and 4): ");
		players = sc.nextInt();
		if (players < 2 || players > 4) {
			throw new IllegalIntegerException(players);
		}
		System.out.println("You picked " + players + " players.");
		return players;
	}
	
	public int askHowManyComputerPlayers(Game g) throws IllegalIntegerException {
		int players = -1;
		System.out.print("Enter amount of Computer Players: ");
		players = sc.nextInt();
		
		if (players < 0 || players > 4 && players <= g.getManyPlayers()) {
			throw new IllegalIntegerException(players);
		}
		return players;
	}
	
	public Position askPosition() throws IllegalIntegerException {
		Position p;
		int x = -1;
		int y = -1;
		
		System.out.print("What's your x? (1-4): ");
		x = sc.nextInt();
		System.out.print("What's your y? (1-4): ");
		y = sc.nextInt();
		
		if (x < 1 || x > 4) {
			throw new IllegalIntegerException(x);
		}
		
		if (y < 1 || y > 4) {
			throw new IllegalIntegerException(y);
		}
		
		p = new Position(x - 1, y - 1, 0);
		return p;
	}
	
	public ArrayList<String> askNames(Game g) {
		ArrayList<String> names = new ArrayList<String>();
		System.out.println("Set player names:");
		
		for (int i = 0; i < g.getManyPlayers(); i++) {
			System.out.println("What's the name of Player " + (i + 1) + "?");
			String s = sc.next();
			names.add(s);
		}
		return names;
	}
	
	public ArrayList<Strategy> askStrategies(Game g) {
		ArrayList<Strategy> result = new ArrayList<Strategy>();
		
		for (int i = 0; i < g.getManyComputerPlayers(); i++) {
			System.out.println("What strategy do you give Computer Player " + (i + 1) + "?\n"
				+ "Available strategies: <TrumpStrategy>");
			String s = sc.next();
			if (s.equals("Trump") || s.equals("TrumpStrategy")) {
				result.add(new TrumpStrategy());
			} else {
				System.out.println("WRONG! We'll make this game GREAT AGAIN!");
				result.add(new TrumpStrategy());
			}

		}
		
		
		return result;
	}
}
