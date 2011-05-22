package ai;

import game.Action;
import game.Table;
import player.Player;

public class PotButtonMasher extends AIUnit {

	public PotButtonMasher(Table t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	public PotButtonMasher(Table t, Player p, int w) {
		super(t, p, w);
		// TODO Auto-generated constructor stub
	}

	
	public Action getAction(){
		
		for(;;){
		switch((int)(Math.random()*7)){
		
		case 0: if(table.legalActions[0])
					return new Action(null,0,-1);
					break;
		case 1:	if(table.legalActions[1])
					return new Action(null,1,-1);
					break;
		case 2:	if(table.legalActions[2])
					return new Action(null,4,-1);
					break;
		case 3:	if(table.legalActions[3])
					return new Action(null,4,-1);
					break;
		case 4:	if(table.legalActions[0])
					return new Action(null,0,-1);
					break;
		case 5: if(table.legalActions[6])
					return new Action(null,6,-1);
					break;
		case 6:	if(table.legalActions[6])
					return new Action(null,6,-1);
					break;
		//case 5:	table.view.reload[(int)(Math.random()*10)].doClick(pushed);break;
			}
		}
	}
	
}
