package ai;

import game.Action;
import game.Table;

import java.util.Vector;

public class Brain {

	
	Vector<AIUnit> units=new Vector<AIUnit>();
	Table table;
	int[] votesAction=new int[7];
	int[] votesAmount=new int[7]; //amount relative pot: 0=minRaise,
								 //1:25%,2:50%,3:75%,4:100%;5:2x,6:allin
								 //
	
	
	public Brain(Table t){
		table=t;
		
	}
	
	public void addAIUnit(AIUnit ai){
		units.add(ai);
	}
	
	public Action getAction(){
		
		
		for(int q=0;q<7;q++){
			votesAction[q]=0;
			votesAmount[q]=0;
		}
		
		for(int i=0;i<units.size();i++){
			Action a=units.get(i).getAction();
			
			votesAction[a.action]+=units.get(i).weight;
			if(a.wager>=0){
				
				votesAmount[(int)(a.wager)]+=units.get(i).weight;
			}
		}
		
		
		int tmp=indexOfLargest(votesAction);
		if(tmp==2 || tmp==3){
			int amtIndex=indexOfLargest(votesAmount);
			System.out.println("amtIndex: "+amtIndex+"tmp: "+tmp);
			switch (amtIndex){
			
			case 0: return new Action(null, tmp, table.getToCall()+table.minRaise);
			case 1:
				if(table.minRaise<=table.getPot()/4+table.getToCall()){
					return new Action(null, tmp, table.getPot()/4 
							+ table.getToCall());
				}
				return new Action(null, tmp,table.minRaise
						+ table.getToCall());
				
			case 2:
				System.out.println("table.minRaise: "+table.minRaise);
				if(table.minRaise<=table.getPot()*.5+table.getToCall()){
					System.out.println("going to bet1: "+table.getPot()/2 
							+ table.getToCall());
					System.out.println("amount field: "+table.settings.amountField.getValue());
					return new Action(null, tmp, table.getPot()/2 
							+ table.getToCall());
				}
				System.out.println("going to bet 2: "+table.minRaise
						+ table.getToCall());
				return new Action(null, tmp,table.minRaise
						+ table.getToCall());
			case 3:
				if(table.minRaise<=table.getPot()*3/4+table.getToCall()){
					return new Action(null, tmp, table.getPot()*3/4 
							+ table.getToCall());
				}
				return new Action(null, tmp,table.minRaise
						+ table.getToCall());
			case 4:
				return new Action(null, 4, 0);
				
			case 5:
					return new Action(null, tmp, table.getPot()*2 
							+ table.getToCall());
			
			case 6:
				return new Action(null, tmp, table.getSeats()[table.getToAct()]
				                         .getStack()
				                         +table.getSeats()[table.getToAct()].getContributed());
			
			}
			
			
		
		
		
		}
		
		return new Action(null, tmp, 0);
		
		
	}

	private int indexOfLargest(int[] arr) {
		
		int tmp=-1;
		int index=0;
		for(int i=0;i<arr.length;i++){
			if(tmp<arr[i]){
				tmp=arr[i];
				index=i;
			}
		}
		
		
		return index;
	}
	
	public boolean sentient(){
		return units.size()>0;
	}
}
