package ai;

import player.Player;
import game.Action;
import game.Table;

public abstract class AIUnit {

	Player I;
	Table table;
	int weight;
	
	public AIUnit(Table t){
		table=t;
		I=table.getSeats()[table.getToAct()];
	}
	public AIUnit(Table t, Player p, int w){
		table=t;
		I=p;
		weight=w;
	}
	
	public Action getAction(){
		return null;
	}
	
	public void setWeight(int w){
		weight=w;
	}
	
	public int getWeight(){
		return weight;
	}
	
	
}
