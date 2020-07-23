package com.congklak.player;

import java.util.ArrayList;

import com.congklak.core.GameState;

public class MediumComputer extends Computer {
	
	public MediumComputer() {
		super();
	}
	
	@Override
	public void getCombination(GameState state) {
		this.combination(state, new ArrayList<>(), this.setCombination());
	}
}
