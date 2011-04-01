package game;

import java.util.Random;

public class Deck {

	
	int[] deck=new int[52];
	Random r=new Random();
	int swaps=200;
	
	public Deck(){
		for(int i=0;i<52;i++){
			deck[i]=i;
		}
	}
	
	public void shuffle(){
		for(int i=0;i<swaps;i++){
			int a,b,tmp;
			a=r.nextInt(52);
			b=r.nextInt(52);
			
			tmp=deck[a];
			deck[a]=deck[b];
			deck[b]=tmp;
			
		}
	}
	
	public void p(){
		for(int i=0;i<52;i++){
			System.out.print(deck[i]+" ");
		}
		System.out.println();
	}
	
}
