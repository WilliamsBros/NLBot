package player;

import game.Table;

public class Player {

	private String name;
	private double stack;
	private Table table;
	
	public Player(){
		this("Player", 0);
	}
	public Player(String n){
		this(n,0);
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
	
	public void setStack(double d){
		stack=d;
	}
	
	public void sit(Table t){
		table=t;
	}
}
