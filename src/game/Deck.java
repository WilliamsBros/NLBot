package game;

import java.util.Random;

public class Deck {

	
	int[] deck=new int[52];
	Random r=new Random();
	
	
	
	public Deck(){
		for(int i=0;i<52;i++){
			deck[i]=i;
		}
	}
	
	//Fisher-Yates Shuffle
	public void shuffle(){
		for(int i=51;i>=0;i--){
			int a,tmp;
			a=r.nextInt(i+1);
			
			tmp=deck[i];
			deck[i]=deck[a];
			deck[a]=tmp;
			
		}
	}
	
	public void p(){
		for(int i=0;i<52;i++){
			System.out.print(deck[i]+" ");
		}
		System.out.println();
	}
	
}
