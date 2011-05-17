package game;

import graphics.PlayerView;
import graphics.TableView;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.JFrame;

import UofAHandEval.ca.ualberta.cs.poker.Card;
import UofAHandEval.ca.ualberta.cs.poker.Hand;
import UofAHandEval.ca.ualberta.cs.poker.HandEvaluator;

import player.Player;

public class Table {

	public static final double getDefaultStackSize = 200;
	Player[] seats = new Player[10];
	Deck deck = new Deck();

	public int sleep=0;
	public int smallBlind = 2;
	public int bigBlind = 3;
	int button = 1;
	int toAct = 2;
	double sb = 1.00;
	double bb = 2.00;

	double pot = 0;
	double toCall = 0;
	int[] board = new int[5];
	int round = 0;
	boolean actionComplete;
	public boolean bbActsLast = true;
	public int livePlayers;
	int playerCount;
	int lastAggressor;

	public int playersAllin = 0;
	public JFrame frame;
	public TableView view;
	public boolean[] legalActions = { true, false, true, false, false, false,
			true };
	// This is a state!!!
	Vector<Vector<Action>> HH = new Vector<Vector<Action>>(4);

	public Table() {

		// Sets the 4 rounds: pre-flop, flop, turn and river. Each round has
		// enough
		// space for 100 actions.
		for (int i = 0; i < 4; i++) {
			HH.add(i, new Vector<Action>(100));
		}
		frame = new JFrame("Table");
		view = new TableView(this);
		view.repaint();
	}

	public Table(State s) {

	}

	public void p() {
		System.out.println("------------------------------------------"
				+ "-----------------------");
		for (int i = 0; i < round + 1; i++) {
			for (int j = 0; j < HH.get(i).size(); j++) {
				System.out.println(HH.get(i).get(j).toString());
			}
		}

		System.out.println("------------------------------------------"
				+ "-----------------------");
	}

	public void run(int numHands) {

		initPlayerCount();

		for (int i = 1; i < numHands+1; i++) {
			System.out.println("hand number "+i+"\n");
			playHand();
		}

	}

	// Plays an entire hand - pre-flop, flop, turn, and river.
	public void playHand() {
		// seats[(button+1)%10];

		
		resetTContributed();
		playersAllin = 0;
		view.clearChipCount(view.pot);
		bbActsLast = true;
		deck.shuffle();
		round = 0;
		toCall = 0;
		pot = 0;
		resetContributed();
		resurrectPlayers();
		for (int i = 0; i < HH.size(); i++) {
			HH.get(i).clear();
		}

		view.repaint();
		//System.out.println(generateStartingAction());
		while (livePlayers < 2) {
			try {
				Thread.sleep(200);
				resurrectPlayers();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		HH.get(0).add(generateStartingAction());
		
		//System.out.println(sumAllMoney());
		actionComplete = false;

		dealCards();
		

		advanceButton();
		toAct = findNextLivePlayer(button);
		if(livePlayers==2){
			advanceAction();
			//bbActsLast=false;
		}
		postSB();
		advanceAction();
		postBB();
		lastAggressor = toAct;
		advanceAction();

		// preflop
		do {
			setLegalActions();
//			System.out.println("livePlayers: " + livePlayers);
//			System.out.println("playersAllin: " + playersAllin);
			if (seats[toAct].getStack() > 0
					&& (livePlayers - playersAllin > 1 || seats[toAct]
							.getContributed() < toCall)) {

				update(seats[toAct].generateAction());
			} else {
				actionComplete = isActionComplete();

//				try {
//					Thread.sleep(sleep);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				advanceAction();
			}

		} while (!actionComplete);

		view.toChips(view.pot, pot);
		view.repaint();

		setTContributed();

		System.out.println("preflop is over");
		if (livePlayers < 2) {
			System.out.println("going to run complete hand");
			completeHand();
			return;
		}

		resetContributed();
		round = 1;
		toAct = button;
		advanceAction();
		for (int i = 0; i < 3; i++) {
			board[i] = deck.deck[i];
		}
		actionComplete = false;

		lastAggressor = indexOfLastLivePlayer();
		toCall = 0;
		// flop
		do {
			setLegalActions();
//			System.out.println("livePlayers: " + livePlayers);
//			System.out.println("playersAllin: " + playersAllin);
			if (seats[toAct].getStack() > 0
					&& (livePlayers - playersAllin > 1 || seats[toAct]
							.getContributed() < toCall)) {
				update(seats[toAct].generateAction());
			} else {
				actionComplete = isActionComplete();
				advanceAction();
//				try {
//					Thread.sleep(sleep);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}

		} while (!actionComplete);

		view.toChips(view.pot, pot);
		view.repaint();
		setTContributed();

		System.out.println("flop is over");
		resetContributed();
		if (livePlayers < 2) {
			completeHand();
			return;
		}

		round = 2;
		toAct = button;
		advanceAction();
		board[3] = deck.deck[3];
		actionComplete = false;
		lastAggressor = indexOfLastLivePlayer();
		toCall = 0;
		// turn
		do {
			setLegalActions();
//			System.out.println("livePlayers: " + livePlayers);
//			System.out.println("playersAllin: " + playersAllin);
			if (seats[toAct].getStack() > 0
					&& (livePlayers - playersAllin > 1 || seats[toAct]
							.getContributed() < toCall)) {
				update(seats[toAct].generateAction());
			} else {
				actionComplete = isActionComplete();
				advanceAction();
//				try {
//					Thread.sleep(sleep);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		} while (!actionComplete);

		view.toChips(view.pot, pot);
		view.repaint();
		setTContributed();

		System.out.println("turn is over");
		resetContributed();
		if (livePlayers < 2) {
			completeHand();
			return;
		}

		round = 3;
		toAct = button;
		advanceAction();
		board[4] = deck.deck[4];
		actionComplete = false;
		lastAggressor = indexOfLastLivePlayer();
		toCall = 0;
		// river
		do {
			setLegalActions();
//			System.out.println("livePlayers: " + livePlayers);
//			System.out.println("playersAllin: " + playersAllin);
			if (seats[toAct].getStack() > 0
					&& (livePlayers - playersAllin > 1 || seats[toAct]
							.getContributed() < toCall)) {
				update(seats[toAct].generateAction());
			} else {
				actionComplete = isActionComplete();
				advanceAction();
//				try {
//					Thread.sleep(sleep);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		} while (!actionComplete);

		view.toChips(view.pot, pot);
		view.repaint();
		setTContributed();

		System.out.println("river is over");
		resetContributed();
		if (livePlayers < 2) {
			completeHand();

		} else {
			showdown();
		}
	}

	private void resetTContributed() {
		for (int i = 0; i < 10; i++) {
			if (seats[i] != null) {
				seats[i].clearTContributed();
				seats[i].handRank=-1;
			}
		}

	}

	private void setTContributed() {
		for (int i = 0; i < 10; i++) {
			if (seats[i] != null) {
				seats[i].setTContributed(seats[i].getContributed());
			}
		}

	}

	private int playersAllIn() {
		int count = 0;
		for (int i = 0; i < 10; i++) {
			if (seats[i] != null && seats[i].isLive()
					&& seats[i].getStack() <= 0) {
				count++;
			}
		}
		return count;
	}

	private void setLegalActions() {
		// fold
		if (seats[toAct].getContributed() - toCall == 0) {
			legalActions[0] = false;
			view.fold.setBackground(view.fold.getParent().getBackground());
		} else {
			legalActions[0] = true;
			view.fold.setBackground(Color.yellow);
		}

		// call
		if (seats[toAct].getContributed() - toCall == 0) {
			legalActions[6] = false;
			view.call.setBackground(view.call.getParent().getBackground());
		} else {
			legalActions[6] = true;
			view.call.setBackground(Color.yellow);
		}

		// check
		if (toCall == 0 || (bbActsLast && toAct == bigBlind && toCall == bb)) {
			legalActions[1] = true;
			view.check.setBackground(Color.yellow);
		} else {
			legalActions[1] = false;
			view.check.setBackground(view.check.getParent().getBackground());
		}

		// bet
		if (toCall == 0 && seats[toAct].getStack() > 0) {
			legalActions[2] = true;
			view.bet.setBackground(Color.yellow);
		} else {
			legalActions[2] = false;
			view.bet.setBackground(view.bet.getParent().getBackground());
		}

		// raise
		if (toCall > 0
				&& seats[toAct].getStack() + seats[toAct].getContributed() > toCall
				&& livePlayers - playersAllin > 1) {
			legalActions[3] = true;
			view.raise.setBackground(Color.yellow);
		} else {
			legalActions[3] = false;
			view.raise.setBackground(view.raise.getParent().getBackground());
		}

	}

	private void resetContributed() {
		for (int i = 0; i < 10; i++) {
			if (seats[i] != null) {
				seats[i].clearContributed();
			}
		}

	}

	private void showdown() {

		Vector<Vector<Player>> hClasses = new Vector<Vector<Player>>();
		Vector<Player> winners = new Vector<Player>();
		Hand h = boardToHand();
		HandEvaluator he = new HandEvaluator();
		NumberFormat f=NumberFormat.getCurrencyInstance();
		
		
		hClasses.add(winners);

		for (int i = 0; i < 10; i++) {
			if (seats[i] != null && seats[i].isLive()) {
				seats[i].handRank = he.rankHand(new Card(
						seats[i].getHand().cardA), new Card(
						seats[i].getHand().cardB), h);

//				System.out.println("trying to add "+seats[i].getName()+
//						" with handrank: "+seats[i].handRank);
				for (int j = hClasses.size() - 1; j >= 0; j--) {
					if (!hClasses.lastElement().isEmpty()) {
						if (seats[i].handRank > hClasses.get(j).get(0).handRank) {
							hClasses.add(j+1,new Vector<Player>());
							hClasses.get(j+1).add(seats[i]);
							//System.out.println("adding "+seats[i].getName()+" in greater than");
							break;

						} 
						else if (seats[i].handRank == hClasses.get(j).get(0).handRank) {
							System.out.println("made it into equals");
							for (int k = hClasses.get(j).size() - 1; k >= 0; k--) {
								if (seats[i].getTContributed() >= hClasses.get(j).get(k)
									.getTContributed()) {
									hClasses.get(j).add(k+1,seats[i]);
									//System.out.println("adding "+seats[i].getName()+" at equals");
									break;
								}
								else if(k==0){
									hClasses.get(j).add(0,seats[i]);
									//System.out.println("adding "+seats[i].getName()+" at equals");
									break;
								}
							}
							break;
						}
						
						else if(j==0 &&seats[i].handRank < hClasses.get(0).get(0).handRank ){
							hClasses.add(0, new Vector<Player>());
							hClasses.get(0).add(seats[i]);
							//System.out.println("adding "+seats[i].getName()+" at less than 0th class");
							break;
						}
					} 
					
					else {
						hClasses.lastElement().add(seats[i]);
						//System.out.println("adding "+seats[i].getName()+" in final else");
							break;
					}
				}

			}

		}

		int plyrAtShowdown=0;
		for(int s=0;s<hClasses.size();s++){
			System.out.println("hand rank class: "+s);
			for(int b=0;b<hClasses.get(s).size();b++){
				plyrAtShowdown++;
				//System.out.println(hClasses.get(s).get(b).getName()+
				//		" hand rank: "+hClasses.get(s).get(b).handRank);
			}
		}
		//System.out.println("livePlayers: "+livePlayers+" playersAtShowdown: "
		//		+plyrAtShowdown);
		
		
		
		Hand tmpHand=new Hand();

		while(!hClasses.isEmpty()){
			
			double pt=0;
			double frstPlyrAmt=hClasses.lastElement().get(0).getTContributed();
			for(int n=0;n<hClasses.size();n++){
				for(int m=0;m<hClasses.get(n).size();m++){
					if(frstPlyrAmt>hClasses.get(n).get(m).getTContributed()){
						
//						System.out.println(hClasses.get(n).get(m).getName()
//								+" total contributed: "+hClasses.get(n).get(m).getTContributed()
//								+", stack: "+hClasses.get(n).get(m).getStack());
						
						pt=pt+hClasses.get(n).get(m).getTContributed();
						hClasses.get(n).get(m).setTContributed
							(-hClasses.get(n).get(m).getTContributed());
						
//						System.out.println(hClasses.get(n).get(m).getName()
//								+" total contributed: "+hClasses.get(n).get(m).getTContributed()
//								+", stack: "+hClasses.get(n).get(m).getStack());
						//System.out.println(sumAllMoney());
					}
					else{
//						System.out.println(hClasses.get(n).get(m).getName()
//								+" total contributed: "+hClasses.get(n).get(m).getTContributed()
//								+", stack: "+hClasses.get(n).get(m).getStack());
						
						pt=pt+frstPlyrAmt;
						hClasses.get(n).get(m).setTContributed(-frstPlyrAmt);
						
//						System.out.println(hClasses.get(n).get(m).getName()
//								+" total contributed: "+hClasses.get(n).get(m).getTContributed()
//								+", stack: "+hClasses.get(n).get(m).getStack());
						//System.out.println(sumAllMoney());
					}
					
					
					
			}
				
			//System.out.println();
		}
			
			for(int l=0;l<10;l++){
				if(seats[l]!=null && !seats[l].isLive() 
						&& seats[l].getTContributed()>0){
					if(frstPlyrAmt>seats[l].getTContributed()){
						
//						System.out.println(seats[l].getName()
//								+" total contributed: "+seats[l].getTContributed()
//								+", stack: "+seats[l].getStack());
						
						pt=pt+seats[l].getTContributed();
						seats[l].setTContributed
							(-seats[l].getTContributed());
//						
//						System.out.println(seats[l].getName()
//								+" total contributed: "+seats[l].getTContributed()
//								+", stack: "+seats[l].getStack());
						
						
						//System.out.println(sumAllMoney());
					}
					else{
//						System.out.println(seats[l].getName()
//								+" total contributed: "+seats[l].getTContributed()
//								+", stack: "+seats[l].getStack());
						
						pt=pt+frstPlyrAmt;
						seats[l].setTContributed(-frstPlyrAmt);
						
//						System.out.println(seats[l].getName()
//								+" total contributed: "+seats[l].getTContributed()
//								+", stack: "+seats[l].getStack());
						//System.out.println(sumAllMoney());
					}
				}
			}
			
			tmpHand.makeEmpty();
			for(int m=0;m<hClasses.lastElement().size();m++){
				
					hClasses.lastElement().get(m).setStack(pt/(hClasses.lastElement().size()));
				tmpHand.addCard(new Card(hClasses.lastElement().get(0).getHand().cardA));
				//System.out.println(new Card(hClasses.lastElement().get(0).getHand().cardA).toString());
				tmpHand.addCard(new Card(hClasses.lastElement().get(0).getHand().cardB));
				//System.out.println(new Card(hClasses.lastElement().get(0).getHand().cardB).toString());
				for(int a=0;a<5;a++){
					tmpHand.addCard(new Card(board[a]));
					//System.out.println(new Card(board[a]).toString());
				}
				
			
				if(pt>0){			
					HH.get(round).add(new Action(hClasses.lastElement().get(0),7
							,pt/hClasses.lastElement().size()
							, hClasses.lastElement().get(0).getName()
							+" wins "
							+f.format(pt/hClasses.lastElement().size())
							+" with "+HandEvaluator.nameHand(tmpHand)));
							
//					view.getGraphics().setColor(Color.yellow);
//					view.getGraphics().setFont(new Font(null,Font.BOLD,30));
					
					
//					try {
//						Thread.sleep(sleep);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					//view.getGraphics().setColor(Color.WHITE);
				}
				//System.out.println(sumAllMoney());
				
			//}
			}
			
			while(hClasses.get(0).get(0).handRank==-1 && 
					hClasses.get(0).get(0).getTContributed()<=0){
				hClasses.get(0).remove(0);
			}
			hClasses.lastElement().remove(0);
			
			if(hClasses.lastElement().isEmpty()){
				hClasses.remove(hClasses.size()-1);
			}
			//System.out.println(sumAllMoney());
			
		}
		
	p();
		try {
			Thread.sleep(20*sleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private double sumAllMoney(){
		double acc=0;
		for(int l=0;l<10;l++){
			if(seats[l]!=null //&& !seats[l].isSittingOut() 
					){
				acc=acc+seats[l].getStack();
				System.out.println(acc);
			}
		}
		System.out.println(acc);
		return acc+pot;
	}
	
	private Hand boardToHand() {
		Hand h = new Hand();

		for (int i = 0; i < 5; i++) {
			h.addCard(board[i]);
		}

		return h;
	}

	public Hand boardToHand(int[] a) {
		Hand h = new Hand();

		for (int i = 0; i < 5; i++) {
			h.addCard(a[i]);
		}

		return h;
	}

	private void completeHand() {
		Player winner = seats[findNextLivePlayer(toAct)];
		Action lastAggr = HH.get(round).get(1);
		for (int i = HH.get(round).size() - 1; i >= 0; i--) {
			if (HH.get(round).get(i).player.equals(winner)) {
				lastAggr = HH.get(round).get(i);
				break;
			}

		}
		// TODO two player will be buggy need to fix
		HH.get(round).add(new Action(winner, 8, pot - lastAggr.wager));
		winner.setStack(pot);
		p();
		try {
			Thread.sleep(20*sleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int indexOfLastLivePlayer() {
		int tmp = button;
		for (int i = 0; i < 10; i++) {
			if (seats[tmp] == null || seats[tmp].isLive() == false) {
				tmp = tmp - 1;
				if (tmp == -1)
					tmp = 9;
			} else {
				break;
			}
		}

		return tmp;
	}

	private void dealCards() {
		int cards = 5; // livePlayers * 2;
		for (int i = 0; i < 10; i++) {
			if (seats[i] != null && !seats[i].isSittingOut()) {
				seats[i].setHand(deck.deck[cards],
				// can be negative -fixing bug -connor
						deck.deck[cards + 1]);
				cards += 2;
			}
			else if(seats[i] != null){
				
				seats[i].getHand().cardA=-1;
				
			}
		}

	}

	// Sets all plyers live field to true, meaning they can act again.
	private void resurrectPlayers() {
		livePlayers = 0;
		for (int i = 0; i < 10; i++) {
			if (seats[i] != null) {
				seats[i].sitOut();
				if (!seats[i].isSittingOut()) {
					seats[i].setLive(true);
					livePlayers++;
				} else {
					seats[i].setLive(false);
				}
			}
		}

	}

	// Returns the index of the next player who has a live=true field.
	private int findNextLivePlayer(int index) {

		index = (index + 1) % 10;
		while (seats[index] == null || !seats[index].isLive()) {
			index = (index + 1) % 10;
		}
		return index;
	}

	// Calculates the number of players and sets playerCount to the result.
	private void initPlayerCount() {
		for (int i = 0; i < 10; i++) {
			if (seats[i] != null)
				playerCount++;
		}

	}

	// Returns true if the round is over, otherwise returns false.
	private Boolean isActionComplete() {

		if (round == 0) {
			if (livePlayers == 1
					|| (findNextLivePlayer(toAct) == lastAggressor && bbActsLast == false)
					|| (lastAggressor == toAct && bbActsLast == true)
					|| (lastAggressor == toAct && !(totalContributed() > 0) ))
			{
				return true;
			}

			return false;
		}

		if (livePlayers == 1
				|| (findNextLivePlayer(toAct) == lastAggressor && (seats[lastAggressor]
						.getContributed() > 0)) || toAct == lastAggressor
				&& !(seats[lastAggressor].getContributed() > 0)) {
			return true;

		}
		return false;

	}

	public double totalContributed() {
		double sum = 0;
		for (int i = 0; i < 10; i++) {
			if (seats[i] != null && !seats[i].isSittingOut()) {
				sum = sum + seats[i].getContributed();
			}
		}
		return sum;
	}

	// Forces the player at index toAct to post the big blind.

	private void postBB() {
		HH.get(round).add(new Action(seats[toAct], 5, bb));
		seats[toAct].setStack(-bb);
		seats[toAct].setContributed(bb);
		setPot(bb);
		toCall = bb;
		view.toChips(seats[toAct].stackChips, seats[toAct].getContributed());
		// view.repaint();

	}

	// Forces the player at index toAct to post the small blind.
	private Action generateStartingAction(){
		String s="";
		NumberFormat f=NumberFormat.getCurrencyInstance();
		
		for(int i=0;i<10;i++){
			if(seats[i]==null){
				s=s+"seat "+(i+1)+": empty.\n";
			}
			else if(seats[i].isSittingOut()){
				s=s+"seat "+(i+1)+": "+seats[i].getName()
				     +" has "+f.format(seats[i].getStack())
				     +" and is sitting out.\n";
			}
			
			else{
				s=s+"seat "+(i+1)+": "+seats[i].getName()
				       		+" has "+f.format(seats[i].getStack())
				       	+".\n";
				
			}
		}
		Action start=new Action(null,11,0);
		start.string=s;
		return start;
	}
	
	private void postSB() {
		HH.get(round).add(new Action(seats[toAct], 4, sb));
		seats[toAct].setStack(-sb);
		seats[toAct].setContributed(sb);
		setPot(sb);
		toCall = sb;
		lastAggressor=toAct;
		view.toChips(seats[toAct].stackChips, seats[toAct].getContributed());
		// view.repaint();
	}

	// Moves the button forward one player, skips null seats.
	private void advanceButton() {
		do {
			button = (button + 1) % 10;
		} while (seats[button] == null || seats[button].isSittingOut());

		
		
		if(livePlayers!=2){
			smallBlind = findNextLivePlayer(button);
			bigBlind = findNextLivePlayer(smallBlind);
		}
		
		else {
			smallBlind=button;
			bigBlind=findNextLivePlayer(smallBlind);
		}
		


	}

	// Sets toAct to the next player, skips null/"dead" players.
	private void advanceAction() {

		do {

			toAct = (toAct + 1) % 10;
		} while (seats[toAct] == null || !seats[toAct].isLive());
	}

	// Adds d to the pot.
	private void setPot(double d) {
		pot += d;
	}

	// Adds a to the list of actions.
	public void update(Action a) {
		HH.get(round).add(a);

		switch (a.action) {
		/*
		 * 0:fold 1:check 2:bet 3:raise 4:post small blind 5:post big blind
		 * 6:call 7:wins hand 8:potsizeNoShow 9:potsizeShowdown
		 */
		case 0: {
			if (a.player == seats[bigBlind]) {
				bbActsLast = false;
			}
			seats[toAct].setLive(false);
			livePlayers--;
			actionComplete = isActionComplete();
			advanceAction();
		}
			break;
		case 1: {
			actionComplete = isActionComplete();
			if (a.player == seats[bigBlind]) {
				bbActsLast = false;
			}

			advanceAction();

		}
			break;
		case 2: {
			if (a.player == seats[bigBlind]) {
				bbActsLast = false;
			}
			setPot(a.wager);
			seats[toAct].setStack(-a.wager);
			if (seats[toAct].getStack() <= 0) {
				playersAllin++;
			}
			lastAggressor = toAct;
			seats[toAct].setContributed(a.wager);
			// test(a);
			toCall = a.wager;
			view
					.toChips(seats[toAct].stackChips, seats[toAct]
							.getContributed());
			advanceAction();
		}
			break;
		case 3: {
			if (a.player == seats[bigBlind]) {
				bbActsLast = false;
			}

			setPot(a.wager - seats[toAct].getContributed());
			seats[toAct].setStack(-(a.wager - seats[toAct].getContributed()));
			if (seats[toAct].getStack() <= 0) {
				playersAllin++;
			}
			seats[toAct]
					.setContributed(a.wager - seats[toAct].getContributed());//
			lastAggressor = toAct;
			// test(a);
			toCall = a.wager;
			view
					.toChips(seats[toAct].stackChips, seats[toAct]
							.getContributed());
			advanceAction();
		}
			break;

		case 6: {
			if (a.player == seats[bigBlind]) {
				bbActsLast = false;
			}
			actionComplete = isActionComplete();
			if (seats[toAct].getStack() < (a.wager - seats[toAct]
					.getContributed())) {
				a.wager = seats[toAct].getStack()
						+ seats[toAct].getContributed();
			}

			seats[toAct].setStack(-(a.wager - seats[toAct].getContributed()));

			if (seats[toAct].getStack() <= 0) {
				playersAllin++;
			}

			setPot(a.wager - seats[toAct].getContributed());
			seats[toAct]
					.setContributed(a.wager - seats[toAct].getContributed());// -
																				// seats[toAct].getContributed());

			// test(a);
			view.toChips(seats[toAct].stackChips, seats[toAct]
							.getContributed());
			advanceAction();
		}
			break;
		}

	}

	private void test(Action a) {
		System.out.println("toCall: " + toCall);
		System.out.println("pot: " + pot);
		System.out.println("a.wager: " + a.wager);
		System.out.println("toAct.getContributed: "
				+ seats[toAct].getContributed());
	}

	// Adds a new player to the table if the seat is open.
	public void addPlayer(Player p, int seat) {
		if (seats[seat - 1] == null) {
			seats[seat - 1] = p;
		}
		p.setSittingOut(true);
		p.sit(this);
	}

	public double getSB() {
		return sb;
	}

	public double getBB() {
		return bb;
	}

	public Player[] getSeats() {
		return seats;
	}

	public int getButton() {
		return button;
	}

	public int getLastAggressor() {
		// TODO Auto-generated method stub
		return lastAggressor;
	}

	public double getLastAggWager() {
		// TODO Auto-generated method stub
		return lastAggressor;
	}

	public Double getPot() {

		return pot;
	}

	public Double getToCall() {

		return toCall;
	}

	public int getRound() {

		return round;
	}

	public int[] getBoard() {
		// TODO Auto-generated method stub
		return board;
	}

	public int getToAct() {
		// TODO Auto-generated method stub
		return toAct;
	}
}
