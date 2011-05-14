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
		
		double startingStack=50;
		
		t.addPlayer(new Player("Connor",startingStack), 3);
		t.addPlayer(new Player("Forrest",startingStack), 1);
		t.addPlayer(new Player("Alexa",startingStack), 2);
		t.addPlayer(new Player("Prometheus",startingStack), 4);
		t.addPlayer(new Player("Corey",startingStack), 5);
		t.addPlayer(new Player("Rani",startingStack), 6);
		t.addPlayer(new Player("Bob",startingStack), 7);
		t.addPlayer(new Player("Turner",startingStack), 8);
		t.addPlayer(new Player("Amy",startingStack), 9);
		t.addPlayer(new Player("Ben Franklin",startingStack), 10);
		
		//t.playHand();
		t.run(300);
	}

}
