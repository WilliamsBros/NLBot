package game;

import java.util.Vector;

import UofAHandEval.ca.ualberta.cs.poker.Card;
import UofAHandEval.ca.ualberta.cs.poker.Hand;
import UofAHandEval.ca.ualberta.cs.poker.HandEvaluator;

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
	
	public void p(){
		System.out.println("------------------------------------------" +
		"-----------------------");
		for(int i=0;i<round+1;i++){
			for(int j=0;j<HH.get(i).size(); j++){
				System.out.println(HH.get(i).get(j).toString());
			}
		}
		
		
		System.out.println("------------------------------------------" +
				"-----------------------");
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
		resetContributed();
		resurrectPlayers();
		
		while(livePlayers<2){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//System.out.println("exiting while loop");
		
		actionComplete=false;
		
		dealCards();
		
		advanceButton();
		postSB();
		advanceAction();
		postBB();
		lastAggressor=toAct;
		advanceAction();
		
		
		//preflop
		do{
			update(seats[toAct].generateAction());
			
			System.out.println("the last aggressor is:" +
					" "+seats[lastAggressor].getName());
			System.out.println("action is on:" +
					" "+seats[toAct].getName());
			p();
			}while(!actionComplete);
			System.out.println("preflop is over");
			resetContributed();
			if(livePlayers==1){
				System.out.println("going to run complete hand");
				completeHand();
				return;
			}
		
			round=1;
			toAct=button;
			advanceAction();
			for(int i=0;i<3;i++){
				board[i]=deck.deck[i];
			}
			actionComplete=false;
			
			lastAggressor=toAct;
		//flop
		do{
			update(seats[toAct].generateAction());
			System.out.println("the last aggressor is:" +
					" "+seats[lastAggressor].getName());
			System.out.println("action is on:" +
					" "+seats[toAct].getName());
			p();
			
			}while(!actionComplete);
		System.out.println("flop is over");
		resetContributed();
		if(livePlayers==1){
			completeHand();
			return;
		}
		
		round=2;
		toAct=button;
		advanceAction();
		board[3]=deck.deck[3];
		actionComplete=false;
		lastAggressor=toAct;
		//turn
		do{
			update(seats[toAct].generateAction());
			p();
			}while(!actionComplete);
		
		System.out.println("turn is over");
		resetContributed();
		if(livePlayers==1){
			completeHand();
			return;
		}
		
		round=3;
		toAct=button;
		advanceAction();
		board[4]=deck.deck[4];
		actionComplete=false;
		lastAggressor=toAct;
		//river
		do{
			update(seats[toAct].generateAction());
			p();
			}while(!actionComplete);
		System.out.println("river is over");
		resetContributed();
		if(livePlayers==1){
			completeHand();
			return;
		}
		else{
			showdown();
		}
		p();
	}
	
	private void resetContributed() {
		for(int i=0;i<10;i++){
			if(seats[i]!=null){
				seats[i].clearContributed();
			}
		}
		
	}

	private void showdown() {
		Vector<Player> winners=new Vector<Player>();
		Vector<Player> losers=new Vector<Player>();
		Hand h=boardToHand();
		HandEvaluator he=new HandEvaluator();
		int bestHand=-1;
		
		for(int i=0;i<10;i++){
			if(seats[i]!=null && seats[i].isLive()){
				int tmp=he.rankHand(new Card(seats[i].getHand().cardA),
						new Card(seats[i].getHand().cardB), h);
				if(tmp>bestHand){
					for(int j=0;j<winners.size();j++){
						losers.add(winners.get(j));
					}
					winners.clear();
					winners.add(seats[i]);
				
				}
				if(tmp==bestHand){
					winners.add(seats[i]);
				}
					
			}
			
		}
		
		double win=pot/winners.size();
		for(int i=0;i<winners.size();i++){
			winners.get(i).setStack(win);
			pot=pot-win;
			HH.get(round).add(new Action(winners.get(i), 9, win));
		
		}
		for(int i=0;i<winners.size();i++){
			HH.get(round).add(new Action(losers.get(i), 10, 0));
			p();
		}
	
		pot=0;
		advanceButton();
		round=0;
		p();
	}

	private Hand boardToHand() {
		Hand h=new Hand();
		
		for(int i=0;i<5;i++){
			h.addCard(board[i]);
		}
		
		return h;
	}

	private void completeHand() {
		Player winner=seats[findNextLivePlayer(toAct)];
		Action lastAggr=HH.get(round).get(1);
		for(int i=HH.get(round).size()-1;i>=0; i--){
			if(HH.get(round).get(i).player.equals(winner)){
				lastAggr=HH.get(round).get(i);
				break;
			}
			
		}
//TODO two player will be buggy need to fix
		HH.get(round).add(new Action(winner, 8, pot-lastAggr.wager));      
		winner.setStack(pot);
		pot=0;
		advanceButton();
		round=0;
		p();
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
					livePlayers++;
				}
			}
		}
		
	}
	
	//Returns the index of the next player who has a live=true field.
	private int findNextLivePlayer(int index){
		
		index=(index+1)%10;
		while(seats[index]==null || !seats[index].isLive()){
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
		seats[toAct].setContributed(bb);
		setPot(bb);
		toCall=bb;
		
	}

	//Forces the player at index toAct to post the small blind.
	private void postSB(){
		HH.get(round).add(new Action(seats[toAct], 4, sb));
		seats[toAct].setStack(-sb);
		seats[toAct].setContributed(sb);
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
		/*
		 * 0:fold
		 * 1:check
		 * 2:bet
		 * 3:raise
		 * 4:post small blind
		 * 5:post big blind
		 * 6:call
		 * 7:wins hand
		 * 8:potsizeNoShow
		 * 9:potsizeShowdown
		 * 
		 */
			case 0:{seats[toAct].setLive(false);
					livePlayers--;
					actionComplete=isActionComplete();
					advanceAction();
			} 	break;
			case 1:{actionComplete=isActionComplete();
					advanceAction();
			}		break;
			case 2:{setPot(a.wager);
					seats[toAct].setStack(-a.wager);
					lastAggressor=toAct;
					seats[toAct].setContributed(a.wager);
					toCall=a.wager;
					advanceAction();
			}	break;
			case 3:{setPot(a.wager-seats[toAct].getContributed());
					seats[toAct].setStack(-(a.wager-seats[toAct].getContributed()));
					seats[toAct].setContributed(a.wager-
							seats[toAct].getContributed());
					lastAggressor=toAct;
					toCall=a.wager;
					advanceAction();
			}		break;
		
			case 6:{actionComplete=isActionComplete();
				seats[toAct].setStack(toCall-seats[toAct].getContributed());
				setPot(a.wager-seats[toAct].getContributed());
				seats[toAct].setContributed(a.wager-
						seats[toAct].getContributed());
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
		p.sit(this);
	}
	
	public double getSB(){
		return sb;
	}
	
	public double getBB(){
		return bb;
	}
}
