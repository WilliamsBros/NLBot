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
		
		//Sets the 4 rounds: pre-flop, flop, turn and river. Each round has enough
		//space for 100 actions.
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
	
	//Plays an entire hand - pre-flop, flop, turn, and river.
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
	
	//Sets all plyers live field to true, meaning they can act again.
	private void resurrectPlayers() {
		for(int i=0;i<10;i++){
			if(seats[i]!=null){
				seats[i].setLive(true);
			}
		}
		
	}
	
	//Returns the index of the next player who has a live=true field.
	private int findNextLivePlayer(int index){
		
		index++;
		while(!seats[index].isLive() ||seats[index]==null){
			index=(index+1)%10;
		}
		return index;
	}
	
	//Calculates the number of players and sets playerCount to the result.
	private void initPlayerCount() {
		for(int i=0;i<10;i++){
			if(seats[i]!=null)
				playerCount++;
		}
		
	}

	
	//Returns true if the round is over, otherwise returns false.
	private Boolean isActionComplete(){
		if(livePlayers==1)
			return true;
		
		
		
		return false;
	}
	
	//Forces the player at index toAct to post the big blind.
	
	//TODO Does seats[toAct] need to be seats[toAct-1]?
	private void postBB() {
		HH.get(round).add(new Action(seats[toAct], 4, bb));
		seats[toAct].setStack(-bb);
		setPot(bb);
		
	}

	//Forces the player at index toAct to post the small blind.
	private void postSB(){
		HH.get(round).add(new Action(seats[toAct], 4, sb));
		seats[toAct].setStack(-sb);
		setPot(sb);
	}
	
	//Moves the button forward one player, skips null seats.
	private void advanceButton(){
		while(seats[button]==null){
		button=(button+1)%10;
		}
	}
	
	//Sets toAct to the next player, skips null/"dead" players.
	private void advanceAction(){
		
		do{
		toAct=(toAct+1)%10;
		} while(seats[toAct]==null ||!seats[toAct].isLive());
	}
	
	//Adds d to the pot.
	private void setPot(double d){
		pot+=d;
	}
	
	//Adds a to the list of actions.
	public void update(Action a){
		HH.get(round).add(a);
	}
	
	//Adds a new player to the table if the seat is open.
	public void addPlayer(Player p, int seat){
		if(seats[seat-1]==null){
			seats[seat-1]=p;
		}
	}
	
	
}
