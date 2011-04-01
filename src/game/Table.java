package game;

import player.Player;

public class Table {

	Player[] seats=new Player[10];
	Deck deck=new Deck();
	int button=1;
	
	public Table(){
		
	}
	
	
	
	public void addPlayer(Player p, int seat){
		if(seats[seat-1]==null){
			seats[seat-1]=p;
		}
	}
	
	
}
