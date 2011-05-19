package command;

import ai.Rote1;
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
		
		
		
		
		
		
		t.addPlayer(new Player("Connor",t.getDefaultStackSize), 3);
		t.getSeats()[2].setAI(new Rote1(t));
		
		t.addPlayer(new Player("Forrest",t.getDefaultStackSize), 1);
		t.addPlayer(new Player("Alexa",t.getDefaultStackSize), 2);
		t.addPlayer(new Player("Prometheus",t.getDefaultStackSize), 4);
		t.addPlayer(new Player("Corey",t.getDefaultStackSize), 5);
		t.addPlayer(new Player("Rani",t.getDefaultStackSize), 6);
		t.addPlayer(new Player("Bob",t.getDefaultStackSize), 7);
		t.addPlayer(new Player("Turner",t.getDefaultStackSize), 8);
		t.addPlayer(new Player("Amy",t.getDefaultStackSize), 9);
		t.addPlayer(new Player("Ben Franklin",t.getDefaultStackSize), 10);
		
		//t.playHand();
		
		long l=System.nanoTime();
		t.run(100000);
		System.out.println((System.nanoTime()-l)/1000000000+" seconds");
	}

}
