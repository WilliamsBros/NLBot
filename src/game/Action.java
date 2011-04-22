package game;

import player.Player;

public class Action {
/*
 * 0:fold
 * 1:check
 * 2:bet
 * 3:raise
 * 4:post small blind
 * 5:post big blind
 * 6:call
 * 7:wins hand
 * 8:potsizeNoShow
 * 9:potsizeShowdown
 * 
 */
	public Player player;
	public int action;
	public double wager;
	
	public Action(Player p, int a, double w){
		player=p;
		action=a;
		wager=w;
	}
	
	public String toString(){
		
	switch (action){
		case 0: return (player.getName()+" folds.");
		
		case 1: return (player.getName()+" checks.");
		
		case 2: return (player.getName()+" bets $"+wager);

		case 3: return (player.getName()+" raises to $"+wager);

		case 4: return (player.getName()+" posts the small blind: "+wager);

		case 5: return (player.getName()+" posts the big blind "+wager);
		
		case 6: return (player.getName()+" calls ");
		//case 7: return (player.getName()+" wins the pot");
		
		case 8: return (player.getName()+" wins: $" + wager);
		
		case 9: return (player.getName()+" wins: $" +wager);
		
	
	}
	return "";
	}
}
