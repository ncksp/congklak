package com.congklak.core;

import com.congklak.player.Computer;
import com.congklak.player.EasyComputer;
import com.congklak.player.ExpertComputer;
import com.congklak.player.HardComputer;
import com.congklak.player.MediumComputer;

public class ComputerFactory {
	private static ComputerFactory factory;
	private ComputerFactory(){}
	
	public static ComputerFactory getInstance(){
		if(factory == null) factory = new ComputerFactory();
		
		return factory;
	}
	public double getComputerRatio(Computer T){
		if(T instanceof EasyComputer){
			return 0.70;
		}else if(T instanceof MediumComputer){
			return 0.50;
		}else if(T instanceof HardComputer){
			return 0.50;
		}else if(T instanceof ExpertComputer){
			return 0.50;
		}
		return 0;
	}
	
	public int getComputerCombination(Computer T){
		if(T instanceof EasyComputer){
			return 1;
		}else if(T instanceof MediumComputer){
			return 2;
		}else if(T instanceof HardComputer){
			return 3;
		}else if(T instanceof ExpertComputer){
			return 4;
		}
		return 6;
	}
}
