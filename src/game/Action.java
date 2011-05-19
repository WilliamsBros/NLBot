package game;

import java.text.NumberFormat;

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
 * 10:loses hand
 * 11:beginning state
 */
	public Player player;
	public int action;
	public double wager;
	public String string="";
	NumberFormat format=NumberFormat.getCurrencyInstance();
	
	public Action(Player p, int a, double w){
		player=p;
		action=a;
		wager=w;
	}
	
	public Action(Player p, int a, double w, String s){
		player=p;
		action=a;
		wager=w;
		string=s;
	}
	
	public void setString(String s){
		string=s;
	}
	public String toString(){
		
	switch (action){
		case 0: return (player.getName()+" folds.");
		
		case 1: return (player.getName()+" checks.");
		
		case 2: return (player.getName()+" bets "+format.format(wager));

		case 3: return (player.getName()+" raises to "+format.format(wager));

		case 4: return (player.getName()+" posts the small blind: "+format.format(wager));

		case 5: return (player.getName()+" posts the big blind "+format.format(wager));
		
		case 6: return (player.getName()+" calls ");
		
		case 7: return (string);
		
		case 8: return (player.getName()+" wins: " + format.format(wager));
		
		case 9: return (player.getName()+" wins: " +format.format(wager));
		
		case 10: return (player.getName()+ " lost");
		
		case 11: return (string);
	
	}
	return "";
	}
}
