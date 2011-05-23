package command;

import ai.PotButtonMasher;
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
		
		Table t=new Table();
	
		t.addPlayer(new Player("Michelangelo",t.getDefaultStackSize), 3);
		t.getSeats()[2].addAIUnit(new PotButtonMasher(t,t.getSeats()[2],100));
		
		t.addPlayer(new Player("April",t.getDefaultStackSize), 1);
		t.getSeats()[0].addAIUnit(new PotButtonMasher(t,t.getSeats()[0],100));
		
		t.addPlayer(new Player("Donatello",t.getDefaultStackSize), 2);
		t.getSeats()[1].addAIUnit(new PotButtonMasher(t,t.getSeats()[1],100));
		
		t.addPlayer(new Player("Rocksteady",t.getDefaultStackSize), 4);
		t.getSeats()[3].addAIUnit(new PotButtonMasher(t,t.getSeats()[3],100));
		
		t.addPlayer(new Player("Splinter",t.getDefaultStackSize), 5);
		t.getSeats()[4].addAIUnit(new PotButtonMasher(t,t.getSeats()[4],100));
		
		t.addPlayer(new Player("Shredder",t.getDefaultStackSize), 6);
		t.getSeats()[5].addAIUnit(new PotButtonMasher(t,t.getSeats()[5],100));
		
		t.addPlayer(new Player("Raphael",t.getDefaultStackSize), 7);
		t.getSeats()[6].addAIUnit(new PotButtonMasher(t,t.getSeats()[6],100));
		
		t.addPlayer(new Player("Leonardo",t.getDefaultStackSize), 8);
		t.getSeats()[7].addAIUnit(new Rote1(t,t.getSeats()[7],100));
		
		t.addPlayer(new Player("Krang",t.getDefaultStackSize), 9);
		t.getSeats()[8].addAIUnit(new PotButtonMasher(t,t.getSeats()[8],100));
		
		t.addPlayer(new Player("Bebop",t.getDefaultStackSize), 10);
		t.getSeats()[9].addAIUnit(new PotButtonMasher(t,t.getSeats()[9],100));
		
		long l=System.nanoTime();
		t.run(100000);
		//t.simulation(500);
		System.out.println((System.nanoTime()-l)/1000000000+" seconds");
	}

}
