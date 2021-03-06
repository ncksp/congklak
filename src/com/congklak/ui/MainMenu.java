package com.congklak.ui;
import java.util.Scanner;

public class MainMenu {

	Scanner scan = new Scanner(System.in);
	private MainMenu() {
		int input = -1;
		do {
			printLine();
			System.out.println("Menu\n" +
					"----\n" +
					"1. Player vs Player\n" +
					"2. Player vs Computer (Easy)\n" +
					"3. Player vs Computer (Medium)\n" +
					"4. Player vs Computer (Hard)\n" +
					"5. Player vs Computer (Expert)\n" +
					"6. How to Play\n" +
					"7. Exit");
			System.out.print("Choose: ");
			try {
				input = scan.nextInt();
			} catch (Exception ex) {
				input = -1;
			}
			scan.nextLine();
			if(input == 6){
				printHowToPlay();
				continue;
			}
			if(input == -1) continue;
			else if(input == 7) break;
			
			new GameBoard(input);
		} while(input != 7);
	}
	
	private void printLine() {
		for (int i = 0; i < 30; i++) {
			System.out.println();
		}
	}
	
	private void printHowToPlay(){
		System.out.println("How to Play\n" + 
				"-----------\n" +
				"Every player have 7 small holes and 1 big hole, every small hole have 7 units\n" + 
				"Every turn, the Player must choose the hole to take the units. After that, the player must distribute to other holes by order and skip the opponent big hole\n" + 
				"When the last unit drops to a filled small hole, then the player must take from the hole and distribute to other holes by order again\n" + 
				"When the last unit drops to a big hole, then the player can choose the hole to the units again.\n" + 
				"\n" + 
				"When the last unit drops to the empty small hole, then the player must change turn.\n" + 
				"When the last unit drops to the empty small hole on that's the player side, then the player takes the last unit and the unit from opponent small hole side to that's the player big hole.\n");
		scan.nextLine();
	}

	public static void main(String[] args) {
		new MainMenu();
	}
}
