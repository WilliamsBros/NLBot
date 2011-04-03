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
 * 6:end preflop
 * 7:end flop
 * 8:end turn
 * 9:end river
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
}
