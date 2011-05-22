package ai;

import player.Player;
import UofAHandEval.ca.ualberta.cs.poker.Card;
import game.Action;
import game.Table;

public class Rote1 extends AIUnit {

	
	Card cardA;
	Card cardB;
	
	public Rote1(Table t, Player p, int w) {
		super(t,p,w);
		}
	
	public Action getAction(){
		cardA=new Card(I.getHand().cardA);
		cardB=new Card(I.getHand().cardB);
	
//		System.out.println("cardA rank: "+cardA.getRank());
//		System.out.println("cardB rank: "+cardB.getRank());
//		System.out.println("cardA suit: "+cardA.getSuit());
//		System.out.println("cardB rank: "+cardA.getSuit());
//	System.out.println("10s and up: "+plus(12));
//	System.out.println("suited connector 7up: "+(isSuitedConnector() && plus(7)));
//	System.out.println("isPair(): "+isPair());
		
	if(table.getRound()==0){
		if(plus(8)||(isSuitedConnector() && plus(5)) ||isPair()){
			if(table.legalActions[3]){
				return new Action(null,3,4);
			}
			
			if(table.legalActions[6]){
				return new Action(null,6,-1);
			}
		}
		
		if(table.legalActions[1]){
			return new Action(null,1,-1);
		}
			return new Action(null,0,-1);
		
	}
	
	else{
		if(table.legalActions[3]){
			return new Action(null,3,2);
		}
		
		if(table.legalActions[2]){
			return new Action(null,2,2);
		}
		if(table.legalActions[6]){
			return new Action(null,6,-1);
		}
		if(table.legalActions[1]){
			return new Action(null,1,-1);
		}
	}
	
	
	return null;
	}
	
	
	public boolean isSuited(){
		
		return cardA.getSuit()==cardB.getSuit();
	}
	
	
	public boolean isPair(){
		return cardA.getRank()==cardB.getRank();
	}
	
	public boolean isSuitedConnector(){
		int tmp=cardA.getRank()-cardB.getRank();
		return isSuited() && (tmp==1 ||tmp==-1);
	}
	
	public boolean plus(int rank){
		
		int lowRank=cardA.getRank();
		if(cardB.getRank()<lowRank){
			lowRank=(cardB.getRank());
		}
	
		return lowRank>=rank;
		
	}

}
