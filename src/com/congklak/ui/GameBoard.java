package com.congklak.ui;

import java.util.Scanner;

import com.congklak.core.GameFactory;
import com.congklak.player.Hint;
import com.congklak.player.Player;

public class GameBoard {
	private int turn = 1;
	
	Scanner scan = new Scanner(System.in);
	Player currentPlayer = null;
	
	GameFactory game = new GameFactory();
	public GameBoard(int type) {
		game.create(type);
		play();
	}
	
	private void play() {
		Player pOne = game.getPlayer1(), pTwo = game.getPlayer2();
		while (true) {
			int hole = -1;
			if (turn % 2 == 1) currentPlayer = pOne;
			else currentPlayer = pTwo;
			
			if(!isPlayerHasMove(currentPlayer))
				continue;
			
			printBoard();
			if (currentPlayer.getLabel().contains("Player")) { // is player
				hole = inputHole(currentPlayer);
			} else { // is computer
				hole = game.getComputerPick(currentPlayer);
				System.out.println("Computer choose: " + hole);
				System.out.print("Press enter to continue");
				scan.nextLine();
			}
			
			int take = 0;
			int currentIndex = hole;
			take = currentPlayer.getValueHole(hole - 1);
			currentPlayer.setValueHole(hole - 1, 0);
			
			currentIndex = doMove(currentIndex, take, hole);	
			// steal from opponent
			if (currentIndex < game.getBIG_HOLE() && ((turn % 2 == 1 && currentPlayer == pOne) || 
					(turn % 2 == 0 && currentPlayer == pTwo))) 
				stealSeed(currentPlayer, currentIndex);
			
			
			if (game.getPlayer1().hasMove() == false && game.getPlayer2().hasMove() == false) break;
			
			if (currentIndex != game.getBIG_HOLE()) changeTurn();
		}
		
		printBoard();
		printResult();
	}
	
	private void changeTurn() {
		++turn;
		printBoard();
		System.out.println("Change turns");
		System.out.print("Press enter to continue");
		scan.nextLine();
	}

	private int doMove(int currentIndex, int take, int hole){
		while (take > 0) {
			++currentIndex;
			// skip opponent big hole
			if ((turn % 2 == 1 && currentPlayer == game.getPlayer2() && currentIndex == game.getBIG_HOLE()) || 
					(turn % 2 == 0 && currentPlayer == game.getPlayer1() && currentIndex == game.getBIG_HOLE())) {
				++currentIndex;
				continue;
			}
			// change side
			if (currentIndex > game.getBIG_HOLE()) {
				currentPlayer = currentPlayer.getOpponent();
				currentIndex = 0;
				continue;
			}
			
			game.slide(currentPlayer, currentIndex, turn, hole);
			
			--take;
			if (take == 0 && currentIndex < game.getBIG_HOLE() && currentPlayer.getValueHole(currentIndex - 1) > 1) {
				take = currentPlayer.getValueHole(currentIndex - 1);
			}
			printBoard();
			System.out.println("On hand: " + take);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return currentIndex;
	}
	
	private void stealSeed(Player currentPlayer, int currentIndex){
		int takeOpponent = currentPlayer.steelSeed(currentIndex);
		printBoard();
		System.out.println(currentPlayer.getName() + " steal: " + takeOpponent);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean isPlayerHasMove(Player currentPlayer) {
		if (currentPlayer.hasMove()) return true;
		
		++turn;
		printBoard();
		System.out.println(currentPlayer.getName() + " has't move");
		System.out.print("Press enter to continue");
		scan.nextLine();
		return false;
	}

	private void printBoard() {
		String player1label = game.getPlayer1().getLabel();
		String player2label = game.getPlayer2().getLabel();
		printLine();

		System.out.println("               7  6  5  4  3  2  1");
		System.out.println("             +--+--+--+--+--+--+--+");
		System.out.printf( " %-10s  |%2d|%2d|%2d|%2d|%2d|%2d|%2d|\n", game.getPlayer2().getName(), game.getPlayer2().getValueHole(6), game.getPlayer2().getValueHole(5), game.getPlayer2().getValueHole(4), game.getPlayer2().getValueHole(3), game.getPlayer2().getValueHole(2), game.getPlayer2().getValueHole(1), game.getPlayer2().getValueHole(0));
		System.out.println("          +--+--+--+--+--+--+--+--+--+");
		System.out.printf( " %-8s |%2d|                    |%2d| %8s\n", player2label, game.getPlayer2().getBig(), game.getPlayer1().getBig(), player1label);
		System.out.println("          +--+--+--+--+--+--+--+--+--+");
		System.out.printf( "             |%2d|%2d|%2d|%2d|%2d|%2d|%2d|  %10s\n", game.getPlayer1().getValueHole(0), game.getPlayer1().getValueHole(1), game.getPlayer1().getValueHole(2), game.getPlayer1().getValueHole(3), game.getPlayer1().getValueHole(4), game.getPlayer1().getValueHole(5), game.getPlayer1().getValueHole(6), game.getPlayer1().getName());
		System.out.println("             +--+--+--+--+--+--+--+");
		System.out.println("               1  2  3  4  5  6  7");
	}
	
	private int inputHole(Player player) {
		int hole = 0;
		do {
			System.out.println(player.getName() + " turn");
			if (player.getName().equals("BINUS")) { // hidden feature
				Hint c = new Hint();
				System.out.println("Hint: " + c.getHint(player.getOpponent(), player));
			}
			System.out.print("Choose hole(1-7): ");
			if (scan.hasNextInt()) {
				hole = scan.nextInt();
			}
			if (scan.hasNextLine()) {
				scan.nextLine();
			}
		} while(hole < 1 || hole > 7 || player.getValueHole(hole - 1) == 0);
		return hole;
	}	

	private void printResult() {
		if(game.getPlayer1().getBig() == game.getPlayer2().getBig()) {
			System.out.println("Draw");
		} else if(game.getPlayer1().getBig() > game.getPlayer2().getBig()) {
			System.out.println(game.getPlayer1().getName() + " win");
		} else {
			System.out.println(game.getPlayer2().getName() + " win");
		}
		
		System.out.print("Press enter to continue");
		scan.nextLine();
	} 

	private void printLine() {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}	
}
