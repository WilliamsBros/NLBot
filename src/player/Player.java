package player;

import game.Action;
import game.Table;

public class Player {

	private String name;
	private double stack;
	private Table table;
	private Boolean isLive=false;
	private Hand hand=new Hand();
	boolean isSittingOut;
	
	public Player(){
		this("Player", 0);
	}
	public Player(String n){
		this(n,0);
	}
	
	public Action generateAction(){
		return null;
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
}
