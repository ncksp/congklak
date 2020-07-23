package com.congklak.player;

import java.util.ArrayList;

import com.congklak.core.GameState;

public class EasyComputer extends Computer {
	
	public EasyComputer() {
		super();
	}
	@Override
	public void getCombination(GameState state) {
		this.combination(state, new ArrayList<>(), this.setCombination());
	}
}
