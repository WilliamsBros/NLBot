package player;

import java.util.Scanner;

import javax.swing.JFrame;

import UofAHandEval.ca.ualberta.cs.poker.Card;

import game.Action;
import game.Table;
import graphics.PlayerView;

public class Player {

	private String name;
	private double stack;
	private Table table;
	private Boolean isLive = false;
	private Hole hand = new Hole();
	boolean isSittingOut;
	private double contributed = 0;
	public int action=-1;
	public double amount=0;
	public int[] stackChips=new int[15];
	
	public JFrame frame;
	PlayerView view;

	public Player() {
		this("Player", 0);
	}

	public Player(String n) {
		this(n, 0);
	}

	public Action generateAction() {
		//view.setPlayer(this);
		//Scanner s = new Scanner(System.in);
		int actionNum;
		double amt;
		Player[] seats = table.getSeats();

		table.view.repaint();
//		for (int i = 0; i < 10; i++) {
//			if (seats[i] != null) {
//				System.out.print(i + ": " + seats[i].getName());
//
//				if (i == table.getButton()) {
//					System.out.print(" <B<:");
//				}
//				if (seats[i] == this) {
//					System.out.print(" <toAct<:");
//				}
//				if (i == table.getLastAggressor()) {
//					System.out.print(" <lastAggressor<:");
//				}
//				if (seats[i].isSittingOut) {
//					System.out.print(" :sitting out ");
//				}
//				if (seats[i].isLive() == false) {
//					System.out.print(" :folded ");
//				}
//				System.out.print(" Contributed: " + seats[i].getContributed());
//				System.out.print(" Stack size: " + seats[i].getStack());
//				System.out.print(" Hole cards: " + "["+
//						new Card(seats[i].getHand().cardA).toString()+"]"
//						+ "["+
//						(new Card(seats[i].getHand().cardB)).toString()+"]");
//						
//				System.out.println();
//				continue;
//			}
//			System.out.println(i + ": empty");
//		}
		// System.out.println("action is on: "+ name);
//		System.out.println(" Potsize: " + table.getPot());
//		System.out.println(" Board: ");

//		switch (table.getRound()) {
//
//		case 0:
//			System.out.println(" [][][][][] ");
//			break;
//		case 1:
//			for (int i = 0; i < 3; i++) {
//				System.out.print("[" + new Card(table.getBoard()[i]).toString()
//						+ "]");
//			}
//			break;
//
//		case 2:
//			for (int i = 0; i < 4; i++) {
//				System.out.print("[" + new Card(table.getBoard()[i]).toString()
//						+ "]");
//			}
//			break;
//		case 3:
//			for (int i = 0; i < 5; i++) {
//				System.out.print("[" + new Card(table.getBoard()[i]).toString()
//						+ "]");
//			}
//			break;
//
//		}
//		System.out.println();
//		System.out.println("action number please: ");
//		System.out.println("0: fold, 1: check, 2: bet, 3: raise 6: call ");

		while(action==-1){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		actionNum = action;//s.nextInt();
		//System.out.println("wager please: ");
		amt = amount;//s.nextDouble();
		action=-1;
		amount=0;
		// s.close();

		// System.out.println("about to print, actionNum: "+actionNum);
		// System.out.println("amt: "+amt);
		//table.p();

		return new Action(this, actionNum, amt);
	}

	public Player(String n, double s) {
		name = n;
		stack = s;
		//frame=table.frame;//new JFrame(name);
		//view=table.view;//new PlayerView(this);
	}

	public String getName() {
		return name;
	}

	public double getStack() {
		return stack;
	}

	public void initStack(double d) {
		stack = d;
	}

	public void setStack(double d) {
		stack += d;
	}

	public void sit(Table t) {
		table = t;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(Boolean b) {
		isLive = b;

	}

	public void setHand(int a, int b) {
		hand.cardA = a;
		hand.cardB = b;
	}

	public boolean isSittingOut() {

		if(stack<=1){
			isSittingOut=true;
		}
		return isSittingOut;
	}

	public void setSittingOut(boolean b) {
		isSittingOut = b;
	}

	public void sitOut() {
		if(stack<=1){
			isSittingOut=true;
		}
		else{
			isSittingOut = false;
			}

	}

	public Table getTable() {
		return table;
	}

	public Hole getHand() {
		return hand;
	}

	public double getContributed() {
		return contributed;
	}

	public void setContributed(double d) {
		contributed = contributed + d;
	}

	public void clearContributed() {
		contributed = 0;

	}
}
