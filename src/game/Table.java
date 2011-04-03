package game;

import java.util.Vector;

import player.Player;

public class Table {

	Player[] seats=new Player[10];
	Deck deck=new Deck();
	int button=1;
	int toAct=2;
	double sb=1;
	double bb=2;
	
	double pot=0;
	int[] board=new int[5];
	int round=0;
	boolean actionComplete;
	
	int livePlayers;
	int playerCount;
	int lastAggressor;
	//This is a state!!!
	Vector<Vector<Action>> HH=new Vector<Vector<Action>>(4);

	public Table(){
		for(int i=0;i<4;i++){
			HH.add(i, new Vector<Action>(100));
		}
	}
	
	public Table(State s){
		
	}
	
	public void run(int numHands){
		
		initPlayerCount();
		
		for(int i=0;i<numHands; i++){
			playHand();	
		}
		
		
		
	}
	
	public void playHand(){
		//seats[(button+1)%10];
		resurrectPlayers();
		actionComplete=false;
		
		advanceButton();
		postSB();
		advanceAction();
		postBB();
		advanceAction();
		lastAggressor=toAct;
		
		//preflop
		while(!actionComplete){
			
			
		}
		
	}
	
	private void resurrectPlayers() {
		for(int i=0;i<10;i++){
			if(seats[i]!=null){
				seats[i].setLive(true);
			}
		}
		
	}

	private int findNextLivePlayer(int index){
		
		index++;
		while(!seats[index].isLive() ||seats[index]==null){
			index=(index+1)%10;
		}
		return index;
	}
	
	private void initPlayerCount() {
		for(int i=0;i<10;i++){
			if(seats[i]!=null)
				playerCount++;
		}
		
	}

	
	private Boolean isActionComplete(){
		if(livePlayers==1)
			return true;
		
		
		
		return false;
	}
	
	private void postBB() {
		HH.get(round).add(new Action(seats[toAct], 4, bb));
		seats[toAct].setStack(-bb);
		setPot(bb);
		
	}

	private void postSB(){
		HH.get(round).add(new Action(seats[toAct], 4, sb));
		seats[toAct].setStack(-sb);
		setPot(sb);
	}
	
	private void advanceButton(){
		while(seats[button]==null){
		button=(button+1)%10;
		}
	}
	
	private void advanceAction(){
		
		do{
		toAct=(toAct+1)%10;
		} while(seats[toAct]==null ||!seats[toAct].isLive());
	}
	
	private void setPot(double d){
		pot+=d;
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
