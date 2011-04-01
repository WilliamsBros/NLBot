package command;

import game.Deck;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Deck d=new Deck();
		d.p();
		d.shuffle();
		d.p();
	}

}
