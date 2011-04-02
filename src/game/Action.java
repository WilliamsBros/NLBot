package game;

import player.Player;

public class Action {

	public Player player;
	public int action;
	public double wager;
	
	public Action(Player p, int a, double w){
		player=p;
		action=a;
		wager=w;
	}
}
