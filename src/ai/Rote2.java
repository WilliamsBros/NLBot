package ai;

import UofAHandEval.ca.ualberta.cs.poker.Card;
import game.Action;
import game.Table;
import player.Player;

public class Rote2 extends AIUnit {

	Card cardA;
	Card cardB;
	
	public Rote2(Table t) {
		super(t);
		
	}

	public Rote2(Table t, Player p, int w) {
		super(t, p, w);
		// TODO Auto-generated constructor stub
		
		
		int[] playersStartingHand=new int[10];
		
	}
	
	public Action getAction(){
		cardA=I.getBrain().pCardA;
		cardB=I.getBrain().pCardB;
		
		
		if(table.getRound()==0){
			
		}
		
		
		
		return null;
	}
}
