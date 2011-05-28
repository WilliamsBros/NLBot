package ai;

import UofAHandEval.ca.ualberta.cs.poker.Card;
import game.Action;
import game.Table;
import player.Player;

public class Rote2 extends AIUnit {

	Card cardA;
	Card cardB;

	public Rote2(Table t) {
		super(t);

	}

	public Rote2(Table t, Player p, int w) {
		super(t, p, w);
		// TODO Auto-generated constructor stub

		int[] playersStartingHand = new int[10];

	}

	public Action getAction() {
		cardA = I.getBrain().pCardA;
		cardB = I.getBrain().pCardB;

		if (table.getRound() == 0) {

		}

		switch (table.getRound()) {
		//preflop
		case 0:
			switch (table.playerCount) {

			case 2:
				switch (I.getBrain().raisesByRound[table.getRound()]) {
				case 0:
					if (I.getSeatIndex() == table.getButton()) {
						return (new Action(null, 4, 4));
					}
				case 1:
				case 2:
				default: return (new Action(null, 3, 6));
				}
			

			case 3:
				checkFold();
			case 4:
				checkFold();
			case 5:
				checkFold();
			case 6:
				checkFold();
			case 7:
				checkFold();
			case 8:
				checkFold();
			case 9:
				checkFold();
			}

		//flop
		case 1:
			checkFold();
		//turn	
		case 2:
			checkFold();
		//river
		case 3:
			checkFold();
		}

		return null;
	}

	private Action checkFold() {
		if (table.legalActions[1]) {
			return new Action(null, 1, -1);
		}

		return new Action(null, 0, -1);
	}
}
