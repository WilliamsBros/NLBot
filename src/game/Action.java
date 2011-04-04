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
 * 7:
 * 8:
 * 9:
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
