package player;

import java.text.ParseException;
import java.util.Scanner;

import javax.swing.JFrame;

import ai.AIUnit;
import ai.Brain;

import UofAHandEval.ca.ualberta.cs.poker.Card;

import game.Action;
import game.Table;
import graphics.PlayerView;

public class Player {

	//private boolean canReload=false;
	public boolean autonomous=false;
	AIUnit ai;
	private Brain brain;
	
	
	public int pushed;
	private String name;
	private double stack;
	private Table table;
	private Boolean isLive = false;
	private Hole hand = new Hole();
	boolean isSittingOut;
	private double contributed = 0;
	private double tContributed=0;
	public int action=-1;
	public double amount=-1;
	public int[] stackChips=new int[15];
	public int handRank=-1;
	
	public JFrame frame;
	PlayerView view;
	public boolean isWaiting=false;

	public Player() {
		this("Player", 0);
	}

	public Player(String n) {
		this(n, 0);
	}
	
	public Player(String n, double s) {
		name = n;
		stack = s;
		
		
		
		//frame=table.frame;//new JFrame(name);
		//view=table.view;//new PlayerView(this);
	}
	
	

	public Action generateAction() {
		pushed=table.sleep*10;
		int actionNum;
		double amt;

		table.view.repaint();
		
		
		while(action==-1 ||amount==-1)
			{
			
			try {
				Thread.sleep(pushed);
			
				
			
				if(autonomous && !brain.sentient()){
				switch((int)(Math.random()*7)){
					
				case 0: if(table.legalActions[0])
							table.view.fold.doClick(pushed);
								break;
				case 1:	if(table.legalActions[1])
							table.view.check.doClick(pushed);
								break;
				case 2:	if(table.legalActions[4])
					table.view.betPot.doClick(pushed);
						break;
				case 3:	if(table.legalActions[6])
							table.view.call.doClick(pushed);
								break;
				case 4:	if(table.legalActions[1])
					table.view.check.doClick(pushed);
						break;
				case 5: if(table.legalActions[0])
					table.view.fold.doClick(pushed);
						break;
				case 6:	if(table.legalActions[6])
					table.view.call.doClick(pushed);
						break;
				//case 5:	table.view.reload[(int)(Math.random()*10)].doClick(pushed);break;
				}
				}
				else{
					if(autonomous){
						Action a=brain.getAction();
						switch(a.action){
							
						case 0:table.view.fold.doClick(pushed);
										break;
						case 1:
									table.view.check.doClick(pushed);
										break;
						case 2:
							table.settings.amountField.setValue(a.wager);
									table.view.bet.doClick(pushed);
										break;
						
						case 3:	
							table.settings.amountField.setValue(a.wager);
							table.view.raise.doClick(pushed);
										break;
									
						
						case 4:		table.view.betPot.doClick(pushed);
										break;
						case 6:	
									table.view.call.doClick(pushed);
										break;
						//case 5:	table.view.reload[(int)(Math.random()*10)].doClick(pushed);break;
						}
						}
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		try {
//			wait(view);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		actionNum = action;//s.nextInt();
		//System.out.println("wager please: ");
		amt = amount;//s.nextDouble();
		action=-1;
		amount=-1;
//		System.out.println("toAct: "+table.getSeats()[table.getToAct()].getName());
//		System.out.println("small blind: "+table.getSeats()[table.smallBlind].getName());
//		System.out.println("big blind: "+table.getSeats()[table.bigBlind].getName());
//		System.out.println("lastAggressor: "+table.getSeats()[table.getLastAggressor()].getName());
//		System.out.println("bbActsLast: "+table.bbActsLast);
//		System.out.println("livePlayers: "+table.livePlayers);
//		System.out.println("totalContributed: "+table.totalContributed());
//		System.out.println("round: "+table.getRound());
		
		// s.close();

		// System.out.println("about to print, actionNum: "+actionNum);
		// System.out.println("amt: "+amt);
		//table.p();

		return new Action(this, actionNum, amt);
	}

	public boolean canReload(){
		
		
		return (!isLive && stack<table.getDefaultStackSize);
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
		pushed=t.sleep;
		brain=new Brain(table);
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
		
		if(stack>0 ||isWaiting){
			isSittingOut=false;
			isWaiting=false;
		}
		else{
			isSittingOut = true;
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
	
	public void setAI(AIUnit a){
		ai=a;
	}
	
	public void addAIUnit(AIUnit ai){
		brain.addAIUnit(ai);
	}
}
