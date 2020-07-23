package com.congklak.core;

import java.util.Random;

import com.congklak.Constants;
import com.congklak.player.Computer;
import com.congklak.player.Player;
import com.congklak.ui.Mode;

public class Game {
	private Random rand = new Random();
	protected Player player1;
	protected Player player2;
	protected Mode mode;
	protected int level = 0;
	
	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public Mode getMode() {
		return mode;
	}

	public int getLevel() {
		return level;
	}
	
	public int getBIG_HOLE() {
		return Constants.BIG_HOLE;
	}
	
	public void slide(Player player, int currentIndex, int turn, int hole){
		// drop to hole
		if (currentIndex == getBIG_HOLE()) {
			player.increaseBig();
		} else if (currentIndex < getBIG_HOLE()) {
			player.setValueHole(currentIndex - 1, player.getValueHole(currentIndex - 1) + 1);
		}
	}
	
	public int getComputerPick(Player currentPlayer){
		Computer comp = (Computer) currentPlayer.getPlayer();
		if (!comp.pick.isEmpty()) 
			return comp.pick.remove();
		
		GameState state = new GameState(currentPlayer.getPlayer().clone(), currentPlayer.getOpponent().getPlayer().clone());
		
		((Computer) currentPlayer).getCombination(state);
		int bound = (int) Math.ceil(comp.solutions.size() * ((Computer) currentPlayer).getRatio());
		
		for (Integer pick : comp.solutions.get(rand.nextInt(bound)).picks) {
			comp.pick.add(pick);
		}
		
		return comp.pick.remove();
	}
}
