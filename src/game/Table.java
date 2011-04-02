package game;

import java.util.Vector;

import player.Player;

public class Table {

	Player[] seats=new Player[10];
	Deck deck=new Deck();
	int button=1;

	double pot=0;
	int[] board=new int[5];
	int round=0;
	
	//This is a state!!!
	Vector<Vector<Action>> HH=new Vector<Vector<Action>>(4);

	public Table(){
		for(int i=0;i<4;i++){
			HH.add(i, new Vector<Action>(100));
		}
	}
	
	public Table(State s){
		
	}
	
	public void update(Action a){
		HH.get(round).add(a);
	}
	
	public void addPlayer(Player p, int seat){
		if(seats[seat-1]==null){
			seats[seat-1]=p;
		}
	}
	
	
}
