package player;

import java.util.Scanner;

import game.Action;
import game.Table;

public class Player {

	private String name;
	private double stack;
	private Table table;
	private Boolean isLive=false;
	private Hole hand=new Hole();
	boolean isSittingOut;
	private double contributed=0;
	
	public Player(){
		this("Player", 0);
	}
	public Player(String n){
		this(n,0);
	}
	
	
	
	public Action generateAction(){
		Scanner s=new Scanner(System.in);
		int actionNum;
		double amt;
		
		System.out.println("action is on: "+ name);
		System.out.println("action number please: ");
		
		actionNum=s.nextInt();
		System.out.println("wager please: ");
		amt=s.nextDouble();
		//s.close();
		
		//System.out.println("about to print, actionNum: "+actionNum);
		//System.out.println("amt: "+amt);
		table.p();
		
		return new Action(this, actionNum, amt);
	}
	
	public Player(String n, double s){
		name=n;
		stack=s;
	}
	
	public String getName(){
		return name;
	}
	
	public double getStack(){
		return stack;
	}
	
	public void initStack(double d){
		stack=d;
	}
	public void setStack(double d){
		stack+=d;
	}
	public void sit(Table t){
		table=t;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(Boolean b) {
		isLive=b;
		
	}
	
	public void setHand(int a, int b){
		hand.cardA=a;
		hand.cardB=b;
	}
	public boolean isSittingOut() {
		
		
		return isSittingOut;
	}
	public void setSittingOut(boolean b) {
		isSittingOut=b;
	}
	public void sitOut() {
		isSittingOut=false;
		
	}
	public Table getTable(){
		return table;
	}
	
	public Hole getHand(){
		return hand;
	}
	public double getContributed(){
		return contributed;
	}
	public void setContributed(double d){
		contributed=contributed+d;
	}
	public void clearContributed() {
		contributed=0;
		
	}
}
