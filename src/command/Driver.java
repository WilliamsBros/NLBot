package command;

import UofAHandEval.ca.ualberta.cs.poker.Card;
import UofAHandEval.ca.ualberta.cs.poker.Hand;
import UofAHandEval.ca.ualberta.cs.poker.HandEvaluator;
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
		int[] a={13,14,44,45,9};
		Table t=new Table();
		
//		Hand h = t.boardToHand(a);
//		HandEvaluator he = new HandEvaluator();
//		int bestHand = -1;
//
//			System.out.println(
//				he.rankHand(new Card(51),
//						new Card(38), h));
//			
//			System.out.println(
//					he.rankHand(new Card(50),
//							new Card(37), h));
		
		t.addPlayer(new Player("Connor",200), 3);
		t.addPlayer(new Player("Forrest",200), 1);
		t.addPlayer(new Player("Alexa",200), 2);
		t.addPlayer(new Player("Prometheus",200), 4);
		t.addPlayer(new Player("Corey",200), 5);
		t.addPlayer(new Player("Rani",200), 6);
		t.addPlayer(new Player("Bob",200), 7);
		t.addPlayer(new Player("Turner",200), 8);
		t.addPlayer(new Player("Amy",200), 9);
		t.addPlayer(new Player("Ben Franklin",200), 10);
		
		//t.playHand();
		t.run(300);
	}

}
