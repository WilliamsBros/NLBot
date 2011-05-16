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
		//int[] a={13,14,44,45,9};
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
		
		
//		Hand tmpHand=new Hand();
//		int[] board={33,34,35,42,3};
//		tmpHand.addCard(new Card(38));
//		tmpHand.addCard(new Card(51));
//		for(int a=0;a<5;a++){
//			tmpHand.addCard(new Card(board[a]));
//		}
//		System.out.println(HandEvaluator.nameHand(tmpHand));
		
		
		
		
		
		
		t.addPlayer(new Player("Connor",Table.getDefaultStackSize), 3);
		t.addPlayer(new Player("Forrest",Table.getDefaultStackSize), 1);
		t.addPlayer(new Player("Alexa",Table.getDefaultStackSize), 2);
		t.addPlayer(new Player("Prometheus",Table.getDefaultStackSize), 4);
		t.addPlayer(new Player("Corey",Table.getDefaultStackSize), 5);
		t.addPlayer(new Player("Rani",Table.getDefaultStackSize), 6);
		t.addPlayer(new Player("Bob",Table.getDefaultStackSize), 7);
		t.addPlayer(new Player("Turner",Table.getDefaultStackSize), 8);
		t.addPlayer(new Player("Amy",Table.getDefaultStackSize), 9);
		t.addPlayer(new Player("Ben Franklin",Table.getDefaultStackSize), 10);
		
		//t.playHand();
		
		
		t.run(1000000);
	}

}
