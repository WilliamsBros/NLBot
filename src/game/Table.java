package game;

import graphics.PlayerView;
import graphics.Settings;
import graphics.TableView;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.JFrame;

import UofAHandEval.ca.ualberta.cs.poker.Card;
import UofAHandEval.ca.ualberta.cs.poker.Hand;
import UofAHandEval.ca.ualberta.cs.poker.HandEvaluator;

import player.Player;

public class Table {

	Player[] seats = new Player[10];
	Deck deck = new Deck();

	public double minRaise;
	public int sleep = 0;
	public int smallBlind = 2;
	public int bigBlind = 3;
	int button = 1;
	int toAct = 2;
	double bb = 2;
	double sb = bb / 2;
	public double getDefaultStackSize = 100 * bb;

	double pot = 0;
	double toCall = 0;
	int[] board = new int[5];
	int round = 0;
	boolean actionComplete;
	public boolean bbActsLast = true;
	public int livePlayers;
	public int playerCount;
	int lastAggressor;

	public int playersAllin = 0;
	public JFrame frame;
	public TableView view;
	public boolean[] legalActions = { true, false, true, false, true, false,
			true };
	public Settings settings;

	NumberFormat format = NumberFormat.getCurrencyInstance();

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
		settings = new Settings(this);
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

	public void simulation(int numHands) {
		initPlayerCount();
		NumberFormat formatD = NumberFormat.getInstance();
		formatD.setMaximumFractionDigits(2);

		for (int i = 1; i < numHands + 1; i++) {
			System.out.println("hand number " + i + "\n");

			for (int j = 0; j < 10; j++) {
				if (seats[j] != null) {
					seats[j].setLifetimeWinnings(seats[j].getStack()
							- seats[j].getStartingStack());

					if (seats[j].getStack() < getDefaultStackSize) {
						seats[j].initStack(getDefaultStackSize);
						seats[j].setStartingStack(getDefaultStackSize);

					} else if (seats[j].getStack() > 3 * getDefaultStackSize) {
						seats[j].initStack(getDefaultStackSize);
						seats[j].setStartingStack(getDefaultStackSize);
					} else {
						seats[j].setStartingStack(seats[j].getStack());
					}

				}
			}

			playHand();

		}
		double sum = 0;
		for (int j = 0; j < 10; j++) {
			if (seats[j] != null) {
				System.out.println(seats[j].getName()
						+ " Lifetime Winnings"
						+ ": "
						+ format.format(seats[j].getLifetimeWinnings())
						+ ", winrate: "
						+ formatD.format(seats[j].getLifetimeWinnings()
								/ numHands * 100 / (2 * bb)) + " PTBB/100");
				sum = sum + seats[j].getLifetimeWinnings();
			}
		}

		System.out.println("net: " + format.format(sum));
	}

	public void run(int numHands) {

		initPlayerCount();

		for (int i = 1; i < numHands + 1; i++) {
			System.out.println("hand number " + i + "\n");
			playHand();
		}

	}

	// Plays an entire hand - pre-flop, flop, turn, and river.
	public void playHand() {
		
		
		minRaise = bb;
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
		if (livePlayers == 1) {

		}

		while (livePlayers < 2) {
			try {
				Thread.sleep(200);
				resurrectPlayers();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		
		playerCount=livePlayers;
		HH.get(0).add(generateStartingAction());

		// System.out.println(sumAllMoney());
		actionComplete = false;

		dealCards();

		advanceButton();
		toAct = findNextLivePlayer(button);
		if (livePlayers == 2) {
			advanceAction();
		}
		postSB();
		advanceAction();
		postBB();
		lastAggressor = toAct;
		advanceAction();

		// preflop
		do {
			setLegalActions();
			settings.resetWager();
			if (seats[toAct].getStack() > 0
					&& (livePlayers - playersAllin > 1 || seats[toAct]
							.getContributed() < toCall)) {

				update(seats[toAct].generateAction());
			} else {
				actionComplete = isActionComplete();

				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				advanceAction();
			}

		} while (!actionComplete);

		view.toChips(view.pot, pot);
		view.repaint();

		setTContributed();

		if (livePlayers < 2) {
			completeHand();
			return;
		}
		HH.get(round).add(
				new Action(null, 12, 0, "---------------End Pre-Flop"
						+ "---------------\n"));

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
		minRaise = bb;
		// flop
		do {
			setLegalActions();
			settings.resetWager();
			if (seats[toAct].getStack() > 0
					&& (livePlayers - playersAllin > 1 || seats[toAct]
							.getContributed() < toCall)) {
				update(seats[toAct].generateAction());
			} else {
				actionComplete = isActionComplete();
				advanceAction();
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} while (!actionComplete);

		view.toChips(view.pot, pot);
		view.repaint();
		setTContributed();
		resetContributed();
		if (livePlayers < 2) {
			completeHand();
			return;
		}
		HH.get(round).add(
				new Action(null, 12, 0, "---------------End Flop"
						+ "---------------\n"));

		round = 2;
		toAct = button;
		advanceAction();
		board[3] = deck.deck[3];
		actionComplete = false;
		lastAggressor = indexOfLastLivePlayer();
		toCall = 0;
		minRaise = bb;
		// turn
		do {
			setLegalActions();
			settings.resetWager();
			if (seats[toAct].getStack() > 0
					&& (livePlayers - playersAllin > 1 || seats[toAct]
							.getContributed() < toCall)) {
				update(seats[toAct].generateAction());
			} else {
				actionComplete = isActionComplete();
				advanceAction();
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while (!actionComplete);

		view.toChips(view.pot, pot);
		view.repaint();
		setTContributed();
		resetContributed();

		if (livePlayers < 2) {
			completeHand();
			return;
		}
		HH.get(round).add(
				new Action(null, 12, 0, "---------------End Turn"
						+ "---------------\n"));

		round = 3;
		toAct = button;
		advanceAction();
		board[4] = deck.deck[4];
		actionComplete = false;
		lastAggressor = indexOfLastLivePlayer();
		toCall = 0;
		// river
		minRaise = bb;
		do {
			setLegalActions();
			settings.resetWager();
			if (seats[toAct].getStack() > 0
					&& (livePlayers - playersAllin > 1 || seats[toAct]
							.getContributed() < toCall)) {
				update(seats[toAct].generateAction());
			} else {
				actionComplete = isActionComplete();
				advanceAction();
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while (!actionComplete);

		view.toChips(view.pot, pot);
		view.repaint();
		setTContributed();

		HH.get(round).add(
				new Action(null, 12, 0, "---------------End River"
						+ "---------------\n"));
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
				seats[i].handRank = -1;
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

		// pot
		if (legalActions[2]) {
			legalActions[4] = true;
			view.betPot.setText("Pot");
			view.betPot.setBackground(Color.green);

		} else if (legalActions[3]) {
			legalActions[4] = true;
			view.betPot.setText("Pot");
			view.betPot.setBackground(Color.red);
		}

		else {
			legalActions[4] = false;
			view.betPot.setBackground(view.bet.getParent().getBackground());
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
		Vector<Player> initialClass = new Vector<Player>();
		Hand h = boardToHand();
		HandEvaluator he = new HandEvaluator();
		NumberFormat f = NumberFormat.getCurrencyInstance();

		hClasses.add(initialClass);

		for (int i = 0; i < 10; i++) {
			if (seats[i] != null && seats[i].isLive()) {
				seats[i].handRank = he.rankHand(new Card(
						seats[i].getHand().cardA), new Card(
						seats[i].getHand().cardB), h);

				for (int j = hClasses.size() - 1; j >= 0; j--) {
					if (!hClasses.lastElement().isEmpty()) {
						if (seats[i].handRank > hClasses.get(j).get(0).handRank) {
							hClasses.add(j + 1, new Vector<Player>());
							hClasses.get(j + 1).add(seats[i]);
							break;

						} else if (seats[i].handRank == hClasses.get(j).get(0).handRank) {
							for (int k = hClasses.get(j).size() - 1; k >= 0; k--) {
								if (seats[i].getTContributed() >= hClasses.get(
										j).get(k).getTContributed()) {
									hClasses.get(j).add(k + 1, seats[i]);
									break;
								} else if (k == 0) {
									hClasses.get(j).add(0, seats[i]);
									break;
								}
							}
							break;
						}

						else if (j == 0
								&& seats[i].handRank < hClasses.get(0).get(0).handRank) {
							hClasses.add(0, new Vector<Player>());
							hClasses.get(0).add(seats[i]);
							break;
						}
					}

					else {
						hClasses.lastElement().add(seats[i]);
						break;
					}
				}

			}

		}

		int plyrAtShowdown = 0;
		for (int s = 0; s < hClasses.size(); s++) {
			for (int b = 0; b < hClasses.get(s).size(); b++) {
				plyrAtShowdown++;
			}
		}


		Hand tmpHand = new Hand();

		while (!hClasses.isEmpty()) {

			double pt = 0;
			double frstPlyrAmt = hClasses.lastElement().get(0)
					.getTContributed();
			for (int n = 0; n < hClasses.size(); n++) {
				for (int m = 0; m < hClasses.get(n).size(); m++) {
					if (frstPlyrAmt > hClasses.get(n).get(m).getTContributed()) {

						pt = pt + hClasses.get(n).get(m).getTContributed();
						hClasses.get(n).get(m).setTContributed(
								-hClasses.get(n).get(m).getTContributed());

					} else {

						pt = pt + frstPlyrAmt;
						hClasses.get(n).get(m).setTContributed(-frstPlyrAmt);

					}

				}

			}

			for (int l = 0; l < 10; l++) {
				if (seats[l] != null && !seats[l].isLive()
						&& seats[l].getTContributed() > 0) {
					if (frstPlyrAmt > seats[l].getTContributed()) {

						pt = pt + seats[l].getTContributed();
						seats[l].setTContributed(-seats[l].getTContributed());

					} else {

						pt = pt + frstPlyrAmt;
						seats[l].setTContributed(-frstPlyrAmt);
					}
				}
			}

			tmpHand.makeEmpty();
			for (int m = 0; m < hClasses.lastElement().size(); m++) {

				hClasses.lastElement().get(m).setStack(
						pt / (hClasses.lastElement().size()));
				tmpHand.addCard(new Card(hClasses.lastElement().get(0)
						.getHand().cardA));
				tmpHand.addCard(new Card(hClasses.lastElement().get(0)
						.getHand().cardB));
				for (int a = 0; a < 5; a++) {
					tmpHand.addCard(new Card(board[a]));
				}

				if (pt > 0) {
					HH.get(round).add(
							new Action(hClasses.lastElement().get(0), 7, pt
									/ hClasses.lastElement().size(), hClasses
									.lastElement().get(m).getName()
									+ " wins "
									+ f.format(pt
											/ hClasses.lastElement().size())
									+ " with "
									+ HandEvaluator.nameHand(tmpHand)));

				}
			}

			while (hClasses.get(0).get(0).handRank == -1
					&& hClasses.get(0).get(0).getTContributed() <= 0) {
				hClasses.get(0).remove(0);
			}
			hClasses.lastElement().remove(0);

			if (hClasses.lastElement().isEmpty()) {
				hClasses.remove(hClasses.size() - 1);
			}

		}

		p();
		Graphics g = view.getGraphics();
		try {

			if (sleep > 0) {
				
				int[] hidden=new int[10];
				for(int k=0;k<10;k++){
					if(seats[k].isHidden()){
						hidden[k]=1;
						seats[k].setCardsHidden(false);
					}
				}
				view.repaint();
				for (int j = 0; j < 6; j++) {

					Thread.sleep(1000);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(10F));

					for (int i = 0, winners = 0; i < HH.get(3).size(); i++) {
						g.setFont(new Font("Lucida Sans", Font.BOLD, 15));
						g.setColor(Color.orange);

						Action a = HH.get(3).get(i);
						if (a.action == 7) {

							g.drawString(HH.get(3).get(i).string, 160,
									230 + 15 * winners);
							g2
									.drawRoundRect(view.cardPos[a.player
											.getSeatIndex() * 2].x - 4,
											view.cardPos[a.player
													.getSeatIndex() * 2].y - 4,
											62, 67, 10, 10);

							g.setColor(Color.black);
							g.setFont(new Font("Lucida Sans", Font.BOLD, 12));
							g.drawString(a.player.getName(), view.sPos[a.player
									.getSeatIndex()].x, view.sPos[a.player
									.getSeatIndex()].y);

							winners++;
						}

					}
					g.setFont(new Font("Lucida Sans", Font.BOLD, 12));
					g2.setStroke(new BasicStroke(1F));
					
					for(int k=0;k<10;k++){
						if(hidden[k]==1){
							seats[k].setCardsHidden(true);
						}
					}

				}
			} else {
				Thread.sleep(30 * sleep);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private double sumAllMoney() {
		double acc = 0;
		for (int l = 0; l < 10; l++) {
			if (seats[l] != null // && !seats[l].isSittingOut()
			) {
				acc = acc + seats[l].getStack();

			}
		}

		return acc + pot;
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
			if (HH.get(round).get(i).player != null
					&& HH.get(round).get(i).player.equals(winner)) {
				lastAggr = HH.get(round).get(i);
				break;
			}

		}
		// TODO two player will be buggy need to fix
		HH.get(round).add(
				new Action(winner, 7, pot - lastAggr.wager, winner.getName()
						+ " wins " + format.format(pot - lastAggr.wager)));

		Graphics g = view.getGraphics();

		winner.setStack(pot);
		pot = pot - lastAggr.wager;
		view.toChips(view.pot, pot);
		p();
		try {
			if (sleep > 0) {
				for (int i = 0; i < 2; i++) {

					Thread.sleep(1000);
					g.setFont(new Font("Lucida Sans", Font.BOLD, 15));
					g.setColor(Color.orange);
					g.drawString(HH.get(round).lastElement().string, 200, 230);

					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(10F));

					g2.drawRoundRect(
							view.cardPos[winner.getSeatIndex() * 2].x - 4,
							view.cardPos[winner.getSeatIndex() * 2].y - 4, 62,
							67, 10, 10);
					g.setFont(new Font("Lucida Sans", Font.BOLD, 12));
					g.setColor(Color.black);

					g.drawString(winner.getName(), view.sPos[winner
							.getSeatIndex()].x,
							view.sPos[winner.getSeatIndex()].y);

					g2.setStroke(new BasicStroke(1F));

				}
			} else {
				Thread.sleep(20 * sleep);
			}
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
				seats[i].setHand(deck.deck[cards], deck.deck[cards + 1]);
				cards += 2;
			} else if (seats[i] != null) {

				seats[i].getHand().cardA = -1;

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
					|| (lastAggressor == toAct && !(totalContributed() > 0))) {
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

		if (seats[toAct].getStack() > bb) {
			HH.get(round).add(new Action(seats[toAct], 5, bb));
			setPot(bb);
			seats[toAct].setContributed(bb);
			seats[toAct].setStack(-bb);

		}

		else {
			HH.get(round).add(
					new Action(seats[toAct], 5, seats[toAct].getStack()));
			setPot(seats[toAct].getStack());
			seats[toAct].setContributed(seats[toAct].getStack());
			seats[toAct].setStack(-seats[toAct].getStack());

			playersAllin++;
		}

		toCall = bb;
		view.toChips(seats[toAct].stackChips, seats[toAct].getContributed());
		// view.repaint();

	}

	// Forces the player at index toAct to post the small blind.
	private Action generateStartingAction() {
		String s = "";

		for (int i = 0; i < 10; i++) {
			if (seats[i] == null) {
				s = s + "seat " + (i + 1) + ": empty.\n";
			} else if (seats[i].isSittingOut()) {
				s = s + "seat " + (i + 1) + ": " + seats[i].getName() + " has "
						+ format.format(seats[i].getStack())
						+ " and is sitting out.\n";
			}

			else {
				s = s + "seat " + (i + 1) + ": " + seats[i].getName() + " has "
						+ format.format(seats[i].getStack()) + ".\n";

			}
		}
		Action start = new Action(null, 11, 0);
		start.string = s;
		return start;
	}

	private void postSB() {

		if (seats[toAct].getStack() > sb) {
			HH.get(round).add(new Action(seats[toAct], 4, sb));
			seats[toAct].setStack(-sb);
			seats[toAct].setContributed(sb);
			setPot(sb);
		}

		else {
			HH.get(round).add(
					new Action(seats[toAct], 4, seats[toAct].getStack()));
			setPot(seats[toAct].getStack());
			seats[toAct].setContributed(seats[toAct].getStack());
			seats[toAct].setStack(-seats[toAct].getStack());

			playersAllin++;
		}

		toCall = sb;
		lastAggressor = toAct;
		view.toChips(seats[toAct].stackChips, seats[toAct].getContributed());
		// view.repaint();
	}

	// Moves the button forward one player, skips null seats.
	private void advanceButton() {

		do {
			button = (button + 1) % 10;
		} while (seats[button] == null || seats[button].isSittingOut());

		if (!(livePlayers < 3)) {
			smallBlind = findNextLivePlayer(button);
			bigBlind = findNextLivePlayer(smallBlind);
		}

		else if (livePlayers == 2) {
			smallBlind = button;
			bigBlind = findNextLivePlayer(smallBlind);
		}

		else {

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
			toCall = a.wager;
			minRaise = a.wager;
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
			minRaise = a.wager - toCall;
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
			view
					.toChips(seats[toAct].stackChips, seats[toAct]
							.getContributed());
			advanceAction();
		}
			break;
		}

	}

	// Adds a new player to the table if the seat is open.
	public void addPlayer(Player p, int seat) {
		if (seats[seat - 1] == null) {
			seats[seat - 1] = p;
		}
		p.setSittingOut(true);
		p.sit(this, seat - 1);
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
		return lastAggressor;
	}

	public double getLastAggWager() {
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
		return board;
	}

	public int getToAct() {
		return toAct;
	}

	public Vector<Vector<Action>> getState() {

		return HH;
	}
}
