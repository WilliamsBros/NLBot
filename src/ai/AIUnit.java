package ai;

import player.Player;
import game.Action;
import game.Table;

public abstract class AIUnit {

	Player I;
	Table table;
	
	public AIUnit(Table t){
		table=t;
		I=table.getSeats()[table.getToAct()];
	}
	
	public Action getAction(){
		return null;
	}
}
