package command;

import player.Player;
import game.Deck;
import game.Table;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		Deck d=new Deck();
//		d.p();
//		d.shuffle();
//		d.p();
		
		Table t=new Table();
		
		t.addPlayer(new Player("Connor",200), 3);
		t.addPlayer(new Player("Forrest",200), 1);
		t.addPlayer(new Player("Alexa",200), 2);
		
		t.playHand();
	}

}
