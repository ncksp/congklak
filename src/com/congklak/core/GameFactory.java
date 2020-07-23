package com.congklak.core;

import java.util.Scanner;

import com.congklak.player.EasyComputer;
import com.congklak.player.ExpertComputer;
import com.congklak.player.HardComputer;
import com.congklak.player.MediumComputer;
import com.congklak.player.Player;
import com.congklak.ui.Mode;

public class GameFactory extends Game {
	private final int EASY = 1;
	private final int MEDIUM = 2;
	private final int HARD = 3;
	private final int EXPERT = 4;
	Scanner scan = new Scanner(System.in);
	
	public Game create(int type){
		
		// player 1
		player1 = player("Player 1");
		
		// player 2
		if(type == 1){
			mode = Mode.PLAYER_VS_PLAYER;
			player2 = player("Player 2");
		}
		else{			
			//computer
			player2 = computer(type);
			player2.setLabel("Computer");
		}
		
		player1.getPlayer().setOpponent(player2.getPlayer());
		player2.getPlayer().setOpponent(player1.getPlayer());
		return this;
	}
	
	private Player player(String label) {
		Player player = new Player(setPlayerName(label));
		player.setLabel(label);
		return player;
	}
	
	private String setPlayerName(String label){
		String name = "";
		do {
			System.out.print(label + " name: ");
			name = scan.nextLine();
		} while(name.length() == 0 || name.length() > 10);
		
		return name;
	}
	
	private Player computer(int level){
		mode = Mode.PLAYER_VS_COMPUTER;
		if(level == 2){
			this.level = EASY;
			return new EasyComputer();
		}else if(level == 3){
			this.level = MEDIUM;
			return new MediumComputer();
		}else if(level == 4){
			this.level = HARD;
			return new HardComputer();
		}else{
			this.level = EXPERT;
			return new ExpertComputer();
		}
		
	}
	
}
