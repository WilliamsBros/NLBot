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
	double toCall=0;
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
		
		deck.shuffle();
		round=0;
		
		resurrectPlayers();
		while(livePlayers<2){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		actionComplete=false;
		
		dealCards();
		
		advanceButton();
		postSB();
		advanceAction();
		postBB();
		advanceAction();
		lastAggressor=toAct;
		
		//preflop
		while(!actionComplete){
			update(seats[toAct].generateAction());
			
			}
			if(livePlayers==1){
				completeHand();
			}
		
			round=1;
			toAct=button;
			advanceAction();
			for(int i=0;i<3;i++){
				board[i]=deck.deck[i];
			}
			
		//flop
		while(!actionComplete){
			update(seats[toAct].generateAction());
			
			}
		
		if(livePlayers==1){
			completeHand();
		}
		
		round=2;
		toAct=button;
		advanceAction();
		board[3]=deck.deck[3];
		
		//turn
		while(!actionComplete){
			update(seats[toAct].generateAction());
			
			}
		
		if(livePlayers==1){
			completeHand();
		}
		
		round=3;
		toAct=button;
		advanceAction();
		board[4]=deck.deck[4];
		
		//river
		while(!actionComplete){
			update(seats[toAct].generateAction());
			
			}
		if(livePlayers==1){
			completeHand();
		}
		else{
			showdown();
		}
	}
	
	private void showdown() {
		
		
	}

	private void completeHand() {
		seats[findNextLivePlayer(toAct)].setStack(pot);
		pot=0;
		advanceButton();
		
	}

	private void dealCards() {
		int cards=livePlayers*2;
		for(int i=0;i<10;i++){
			if(seats[i]!=null){
				seats[i].setHand(deck.deck[51-cards],
						deck.deck[51-(cards-1)]);
				cards-=2;
			}
		}
		
	}

	//Sets all plyers live field to true, meaning they can act again.
	private void resurrectPlayers() {
		for(int i=0;i<10;i++){
			if(seats[i]!=null){
				seats[i].sitOut();
				if(seats[i].isSittingOut()==false){
					seats[i].setLive(true);
				}
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
		if(livePlayers==1 ||toAct==lastAggressor)
			return true;
		
		
		return false;
	}
	
	//Forces the player at index toAct to post the big blind.
	
	
	private void postBB() {
		HH.get(round).add(new Action(seats[toAct], 5, bb));
		seats[toAct].setStack(-bb);
		setPot(bb);
		toCall=bb;
		
	}

	//Forces the player at index toAct to post the small blind.
	private void postSB(){
		HH.get(round).add(new Action(seats[toAct], 4, sb));
		seats[toAct].setStack(-sb);
		setPot(sb);
	}
	
	//Moves the button forward one player, skips null seats.
	private void advanceButton(){
		while(seats[button]==null ||seats[button].isSittingOut()==true){
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
		
		
		switch(a.action){
		
			case 0:{seats[toAct].setLive(false);
					actionComplete=isActionComplete();
					advanceAction();
			} 	break;
			case 1:{actionComplete=isActionComplete();
					advanceAction();
			}		break;
			case 2:{setPot(a.wager);
					seats[toAct].setStack(-a.wager);
					lastAggressor=toAct;
					toCall=a.wager;
					advanceAction();
			}	break;
			case 3:{setPot(a.wager);
					seats[toAct].setStack(-a.wager);
					lastAggressor=toAct;
					toCall=a.wager;
					advanceAction();
			}		break;
		
			case 6:{actionComplete=isActionComplete();
					advanceAction();
			}		break;
		}
		
		
	
	}
	
	//Adds a new player to the table if the seat is open.
	public void addPlayer(Player p, int seat){
		if(seats[seat-1]==null){
			seats[seat-1]=p;
		}
		p.setSittingOut(true);
	}
	
	
}
