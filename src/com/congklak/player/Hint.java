package com.congklak.player;

import java.util.ArrayList;

import com.congklak.core.GameState;

public class Hint extends Computer {

	public Hint() {
		super();
	}
	
	@Override
	public void getCombination(GameState state) {
		this.combination(state, new ArrayList<>(), this.setCombination());
	}
	
	public int getHint(Player p1, Player p2) {
		GameState state = new GameState(p1.clone(), p2.clone());
		this.getCombination(state);
		return this.solutions.get(0).picks.get(0);
	}
}
