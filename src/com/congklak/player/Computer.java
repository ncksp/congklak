package com.congklak.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import com.congklak.Constants;
import com.congklak.core.ComputerFactory;
import com.congklak.core.GameState;
import com.congklak.core.Solution;


public abstract class Computer extends Player {
	
	public Queue<Integer> pick;
	public ArrayList<Solution> solutions;
	private double ratio;
	private int	BIG_HOLE = Constants.BIG_HOLE; 
	
	private static String computerNames[] = new String[]{
		"Christian",
		"Frans",
		"Thomas",
		"Michael",
		"Valent",
		"Yudy"
	};
	
	public Computer() {
		super(generateName());
		this.ratio = setRatio();
		pick = new LinkedList<>();
		solutions = new ArrayList<Solution>();
	}
	
	private static String generateName() {
		Random rand = new Random();
		return computerNames[rand.nextInt(computerNames.length) ];
	}
	
	protected void combination(GameState state, ArrayList<Integer> picks, int maxLevel) {
		this.solutions.clear();
		if(maxLevel <= 0) {
			addSolution(state, picks);
			return;
		}
		for (int i = 1; i <= 7; i++) {
			if(state.computer.getValueHole(i - 1) <= 0)
			continue;
			
			picks.add(i);
			simulateTurn(state.clone(), picks, maxLevel);
			picks.remove(picks.size() - 1);			
		}
		Collections.sort(this.solutions);
	}
	
	private void simulateTurn(GameState state, ArrayList<Integer> picks, int maxLevel) {
		int hole = picks.get(picks.size() - 1);
		Player currentPlayer = state.computer;
//		
		int take = 0;
		int currentIndex = hole;
		take = currentPlayer.getValueHole(hole - 1);
		currentPlayer.setValueHole(hole - 1, 0);
		
		while (take > 0) {
			++currentIndex;
			// skip opponent big hole
			if (currentPlayer == state.player && currentIndex == BIG_HOLE) {
				++currentIndex;
				continue;
			}
			// change side
			if (currentIndex > BIG_HOLE) {
				currentPlayer = currentPlayer.getOpponent();
				currentIndex = 0;
				continue;
			}
			// drop to hole
			if (currentIndex == BIG_HOLE) {
				currentPlayer.increaseBig();
			} else if (currentIndex < BIG_HOLE) {
				currentPlayer.setValueHole(currentIndex - 1, currentPlayer.getValueHole(currentIndex - 1) + 1);
			}
			--take;
			// take from last hole
			if (take == 0 && currentIndex < BIG_HOLE && currentPlayer.getValueHole(currentIndex - 1) > 1) {
				take = currentPlayer.getValueHole(currentIndex - 1);
				currentPlayer.setValueHole(currentIndex - 1, 0);
			}
		}
		if (currentIndex == BIG_HOLE) {
			combination(state, picks, maxLevel - 1);
		} else {
			addSolution(state, picks);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addSolution(GameState state, ArrayList<Integer> picks) {
		Solution data = new Solution();
		data.picks = (ArrayList<Integer>) picks.clone();
		data.result = state.computer.getBig();
		solutions.add(data);
	}
	
	public double getRatio() {
		return ratio;
	}
	
	private double setRatio(){
		ComputerFactory factory = ComputerFactory.getInstance();
		
		return factory.getComputerRatio(this);
	}
	
	protected int setCombination(){
		ComputerFactory factory = ComputerFactory.getInstance();
		return factory.getComputerCombination(this);
	}
	
	public abstract void getCombination(GameState gameState);

}
