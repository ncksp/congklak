package com.congklak.player;

import com.congklak.Constants;

public class Player implements Cloneable {
	private int hole[] = new int[7];
	private int big = 0;
	private String name;
	private String label;

	private Player opponent = null;
	
	public Player(String name) {
		this.name = name;
		for (int i = 0; i < 7; i++) {
			hole[i] = Constants.VALUE_HOLE;
		}
		big = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setValueHole(int index, int value) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
		if (index < 0 || index > 6)
			throw new ArrayIndexOutOfBoundsException("Must between 0 and 6");
		if (value < 0)
			throw new IllegalArgumentException("Must more than or equals zero");
		hole[index] = value;
	}
	
	public int getValueHole(int index) throws ArrayIndexOutOfBoundsException {
		if (index < 0 || index > 6)
			throw new ArrayIndexOutOfBoundsException("Must between 0 and 6");
		return hole[index];
	}
	
	public int getBig() {
		return big;
	}
	
	public void increaseBig(){
		this.big ++;
	}
	
	public Player getOpponent() {
		return opponent;
	}
	
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	
	public int steelSeed(int currentIndex){
		int opponentIndex = 7 - currentIndex;
		Player opponentPlayer = getOpponent();
		
		if(opponentPlayer.getValueHole(opponentIndex) == 0)
			return 0;
		
		int takeOpponent = opponentPlayer.getValueHole(opponentIndex);
		opponentPlayer.setValueHole(opponentIndex, 0);
		setValueHole(currentIndex - 1, 0);
		this.big += (takeOpponent + 1);
		return takeOpponent;
	}
	
	public Player clone() {
		Player p = new Player(name);
		p.hole = this.hole.clone();
		p.big = this.big;
		p.opponent = null;
		return p;
	}
	
	@Override
	public String toString() {
		String str = name + " [";
		for (int i : hole) {
			str += i + ", ";
		}
		str += "]";
		return str;
	}
	
	public Boolean hasMove() {
		for (int i : hole){
			if (i > 0) return true;
		}
		return false;
	}

	public Player getPlayer() {
		// TODO Auto-generated method stub
		return this;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
