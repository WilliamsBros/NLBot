package player;

import java.util.Scanner;

import javax.swing.JFrame;

import UofAHandEval.ca.ualberta.cs.poker.Card;

import game.Action;
import game.Table;
import graphics.PlayerView;

public class Player {

	public int pushed=0;
	private String name;
	private double stack;
	private Table table;
	private Boolean isLive = false;
	private Hole hand = new Hole();
	boolean isSittingOut;
	private double contributed = 0;
	private double tContributed=0;
	public int action=-1;
	public double amount=0;
	public int[] stackChips=new int[15];
	public int handRank=-1;
	
	public JFrame frame;
	PlayerView view;

	public Player() {
		this("Player", 0);
	}

	public Player(String n) {
		this(n, 0);
	}

	public Action generateAction() {
		int actionNum;
		double amt;

		table.view.repaint();
		while(action==-1){
			try {
				Thread.sleep(table.sleep);
				
				switch((int)(Math.random()*5)){
					
				case 0: table.view.bet.doClick(pushed);break;
				case 1:	table.view.call.doClick(pushed);break;
				case 2:	table.view.check.doClick(pushed);break;
				case 3:	table.view.raise.doClick(pushed);break;
				case 4:	table.view.fold.doClick(pushed);break;
				}
				
				
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

		if(stack<=0){
			isSittingOut=true;
		}
		return isSittingOut;
	}

	public void setSittingOut(boolean b) {
		isSittingOut = b;
	}

	public void sitOut() {
		if(stack<=0){
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

	public double getTContributed() {
		return tContributed;
	}
	public void setContributed(double d) {
		contributed = contributed + d;
	}

	public void clearContributed() {
		contributed = 0;

	}

	public void clearTContributed() {
		tContributed=0;
		
	}

	public void setTContributed(double c) {
		tContributed=tContributed+c;
		
	}
}
