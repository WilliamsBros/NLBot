package graphics;

import game.Action;
import game.Table;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

import UofAHandEval.ca.ualberta.cs.poker.Card;

import player.Player;

public class TableView extends JPanel implements MouseInputListener,
		ActionListener, ChangeListener {

	JFrame frame;
	Player player;
	Table table;
	JMenuBar menu = new JMenuBar();

	public JButton fold = new JButton("Fold");
	public JButton check = new JButton("Check");
	public JButton bet = new JButton("Bet");
	public JButton raise = new JButton("Raise");
	public JButton call = new JButton("Call");
	public JButton betPot= new JButton("Pot");
	public JButton[] reload=new JButton[10]; 
	public JCheckBoxMenuItem allAut=new JCheckBoxMenuItem("Autonomous");
	
	public JCheckBox[] autonomous=new JCheckBox[10]; 
	NumberFormat format1=NumberFormat.getCurrencyInstance();
	BufferedImage tbl;
	BufferedImage cardImgs;
	Point[] sPos = new Point[10];
	Point[] cardPos = new Point[20];

	Double[] chipVals = { .01, .05, .25, 1.0, 5.0, 25.0, 100.0, 500.0, 1000.0,
			5000.0, 25000.0, 100000.0, 500000.0, 1000000.0, 5000000.0 };

	BufferedImage chips;

	public int[] pot = new int[15];
	Point[] chipPos = new Point[10];

	// JButton clear=new JButton("clear");
	// JSlider amount=new JSlider(0,(int)player.getStack(),0);

	public TableView(Table t) {
		try {
			
			
			ClassLoader cldr = TableView.class.getClassLoader();
			
//no jar			
			tbl = ImageIO.read(cldr.getResource("\\graphics\\Texas_Holdem_Poker_Table.jpg"));
			cardImgs = ImageIO.read(cldr.getResource("\\graphics\\clip_image004_2.gif"));
			chips = ImageIO.read(cldr.getResource("\\graphics\\Chips3.png"));	
			
			
			
//for jar
//			tbl = ImageIO.read(cldr.getResource("NLBot1\\graphics\\Texas_Holdem_Poker_Table.jpg"));
//			cardImgs = ImageIO.read(cldr.getResource("NLBot1\\graphics\\clip_image004_2.gif"));
//			chips = ImageIO.read(cldr.getResource("NLBot1\\graphics\\Chips3.png"));
			
		} catch (IOException e) {
			System.out.println("Image could not be read");
			System.exit(1);
		}
		
		
		this.setLayout(null);
		setBackground(Color.white);
		// C:" +
		// "\\Documents and Settings\\ccadmin.LT-SF-0XX\\Desktop\\" +
		// "java\\NLBot\\src\\Texas_Holdem_Poker_Table.jpg
		// C:\\Documents and
		// Settings\\ccadmin.LT-SF-0XX\\Desktop\\java\\NLBot\\src\\clip_image004_2.gif
		table = t;
		frame = table.frame;
		int fontSize=15;
		
		//frame.setLayout(null);
		frame.setSize(1000, 550);
		//frame.setResizable(false);
		//frame.setExtendedState();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		
		fold.addActionListener(this);
		fold.setFont(new Font("Lucida Sans",Font.BOLD,fontSize));
		
		
		check.addActionListener(this);
		check.setFont(new Font("Lucida Sans",Font.BOLD,fontSize));
		
		call.addActionListener(this);
		call.setFont(new Font("Lucida Sans",Font.BOLD,fontSize));
		
		bet.addActionListener(this);
		bet.setFont(new Font("Lucida Sans",Font.BOLD,fontSize));
		
		raise.addActionListener(this);
		raise.setFont(new Font("Lucida Sans",Font.BOLD,fontSize));
		
		betPot.addActionListener(this);
		betPot.setFont(new Font("Lucida Sans",Font.BOLD,fontSize));
		
		allAut.addActionListener(this);
		// amount.addChangeListener(this);

		//menu.setBorder(BorderFactory.createEmptyBorder(0,0, 0, 0));
		
		
	     
	     
		menu.add(fold);
		menu.add(check);
		menu.add(call);
		menu.add(bet);
		menu.add(raise);
		menu.add(betPot);
		menu.add(table.settings.amountLabel);
		menu.add(table.settings.amountField);
		menu.add(new JLabel("Stack%"));
		menu.add(table.settings.sliders.get(1));
		menu.add(new JLabel("Speed"));
		menu.add(table.settings.sliders.get(0));
		menu.add(allAut);
		//menu.add(new JLabel("|"));
		
		//menu.setLayout(LayoutManager)
		// menu.add(amount);

		// frame.setTitle(player.getName());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menu);
		//addMouseListener(this);

		sPos[0] = new Point(130, 50);
		sPos[1] = new Point(610, 50);
		sPos[2] = new Point(680, 120);
		sPos[3] = new Point(710, 210);
		sPos[4] = new Point(560, 370);
		sPos[5] = new Point(440, 370);
		sPos[6] = new Point(320, 370);
		sPos[7] = new Point(200, 370);
		sPos[8] = new Point(40, 210);
		sPos[9] = new Point(70, 120);

		chipPos[0] = new Point(sPos[0].x + 100, sPos[0].y + 80);
		chipPos[1] = new Point(sPos[1].x - 85, sPos[1].y + 80);
		chipPos[2] = new Point(sPos[2].x - 95, sPos[2].y + 50);
		chipPos[3] = new Point(sPos[3].x - 105, sPos[3].y + 20);
		chipPos[4] = new Point(sPos[4].x - 20, sPos[4].y - 100);
		chipPos[5] = new Point(sPos[5].x - 10, sPos[5].y - 100);
		chipPos[6] = new Point(sPos[6].x - 10, sPos[6].y - 100);
		chipPos[7] = new Point(sPos[7].x + 20, sPos[7].y - 100);
		chipPos[8] = new Point(sPos[8].x + 115, sPos[8].y + 20);
		chipPos[9] = new Point(sPos[9].x + 105, sPos[9].y + 60);
		
		cardPos[0] = new Point(sPos[0].x + 59, sPos[0].y );
		cardPos[1] = new Point(sPos[0].x + 70, sPos[0].y );
		cardPos[2] = new Point(sPos[1].x - 60, sPos[1].y -10);
		cardPos[3] = new Point(sPos[1].x - 49, sPos[1].y -10);
		cardPos[4] = new Point(sPos[2].x - 60, sPos[2].y - 10);
		cardPos[5] = new Point(sPos[2].x - 49, sPos[2].y - 10);
		cardPos[6] = new Point(sPos[3].x - 60, sPos[3].y - 10);
		cardPos[7] = new Point(sPos[3].x -49, sPos[3].y - 10);
		cardPos[8] = new Point(sPos[4].x -5, sPos[4].y - 70);
		cardPos[9] = new Point(sPos[4].x + 6, sPos[4].y - 70);
		cardPos[10] = new Point(sPos[5].x -5, sPos[5].y - 70);
		cardPos[11] = new Point(sPos[5].x + 6, sPos[5].y - 70);
		cardPos[12] = new Point(sPos[6].x -5, sPos[6].y - 70);
		cardPos[13] = new Point(sPos[6].x + 6, sPos[6].y- 70);
		cardPos[14] = new Point(sPos[7].x -5, sPos[7].y - 70);
		cardPos[15] = new Point(sPos[7].x + 6, sPos[7].y - 70);
		cardPos[16] = new Point(sPos[8].x + 54, sPos[8].y);
		cardPos[17] = new Point(sPos[8].x + 65, sPos[8].y );
		cardPos[18] = new Point(sPos[9].x + 59, sPos[9].y );
		cardPos[19] = new Point(sPos[9].x + 70, sPos[9].y );

		
		
		for(int i=0;i<10;i++){
			
			reload[i]=new JButton("R");
			reload[i].setFont((new Font("Lucida Sans",Font.BOLD,10)));
			reload[i].setSize(45,15);
			reload[i].setActionCommand("rel"+i);
			reload[i].addActionListener(this);
			reload[i].setLocation(sPos[i].x, sPos[i].y+30);
			this.add(reload[i]);
		}
		for(int i=0;i<10;i++){
			
			autonomous[i]=new JCheckBox("Aut");
			autonomous[i].setFont((new Font("Lucida Sans",Font.BOLD,10)));
			autonomous[i].setSize(45,15);
			autonomous[i].setActionCommand("aut"+i);
			autonomous[i].addActionListener(this);
			autonomous[i].setLocation(sPos[i].x, sPos[i].y+45);
			this.add(autonomous[i]);
		
		}
		frame.setVisible(true);
//		for(int i=0;i<10;i++){
//			
//		}
		
	}

//	 public static void main(String[] args) {
//	 TableView v = new TableView(new Table());
//	
//	 v.toChips(v.pot, 544.0);
//	 }

	public void toChips(int[] c, double d) {
		double chips = d;
		int i = 14;

		clearChipCount(c);

		while (chips > 0) {
			i = 14;
			while (true) {
				if (chipVals[i] < chips) {
					i = i + 1;
					if (i > 14) {
						i = 14;
					}
				}
				if (chipVals[i] > chips)
					i = i / 2;
				if ((i == 14) || (i == 0)
						|| (chipVals[i] <= chips && chipVals[i + 1] > chips)) {
					break;
				}
				// System.out.println(i + ": " + chipVals[i]);
			}
			 //System.out.println(i + ": " + chipVals[i]);

			c[i] = c[i] + 1;
			chips = chips - chipVals[i];
		}

//		 for(int j=0;j<15; j++){
//		 if(c[j]!=0){
//		 System.out.println(c[j]+" times "+chipVals[j]);
//		 }
//		 }
	}

	public BufferedImage getCardImage(int i) {

		return cardImgs.getSubimage(((i % 13) + 1) % 13 * 44, i / 13 * 60, 44,
				60);

	}

	public void draw() {

		repaint();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.black);
		g.drawImage(tbl, 110, 80, 572, 240, new Color(255, 255, 255), this);

		g.setFont(new Font("Lucida Sans",Font.BOLD,12));
		
		g.setColor(new Color(8388608));
		g.fillOval(chipPos[table.getButton()].x+25
		                            , chipPos[table.getButton()].y
		                            , 20, 20);
		g.setColor(Color.white);
		g.drawString("D",chipPos[table.getButton()].x+30
                , chipPos[table.getButton()].y+15);
		
		g.setColor(Color.black);
		for (int i = 0; i < 10; i++) {
			// g.setColor(Color.black);
			//g.drawRoundRect(sPos[i].x, sPos[i].y, 45, 45, 10, 10);
			g.setColor(Color.black);
			
			
			
			
			if (i == table.getToAct() && table.livePlayers>1) {
				//g.setColor(Color.red);
				// g.fillOval(sPos[i].x+2, sPos[i].y+30, 10, 10);
				Graphics2D g2D = (Graphics2D) g;      

				
//				g.setColor(Color.red);
//				g2D.setStroke(new BasicStroke(12F));
//				g2D.drawRoundRect(cardPos[i*2].x-7, cardPos[i*2].y-7, 66, 71, 10, 10);
				
				g.setColor(Color.red);
				g2D.setStroke(new BasicStroke(10F));
				g2D.drawRoundRect(cardPos[i*2].x-4, cardPos[i*2].y-4, 62, 67, 10, 10);
				
//				g.setColor(Color.orange);
//				g2D.setStroke(new BasicStroke(3F));
//				g2D.drawRoundRect(cardPos[i*2].x-4, cardPos[i*2].y-4, 61, 66, 10, 10);
//				
//				g.setColor(Color.yellow);
//				g2D.setStroke(new BasicStroke(3F));
//				g2D.drawRoundRect(cardPos[i*2].x-3, cardPos[i*2].y-3, 60, 65, 10, 10);
//				
//				g.setColor(Color.green);
//			    g2D.setStroke(new BasicStroke(2F));
//				g2D.drawRoundRect(cardPos[i*2].x-2, cardPos[i*2].y-2, 59, 64, 10, 10);
//				
//				g.setColor(Color.black);
//				g2D.setStroke(new BasicStroke(4F));
//				g2D.drawRoundRect(cardPos[i*2].x, cardPos[i*2].y, 55, 60, 10, 10);
				
				g2D.setStroke(new BasicStroke(1F));
				g.setColor(Color.black);
			}
			
			

			if (table.getSeats()[i] != null) {
				if (!table.getSeats()[i].isLive()) {
					if (table.getSeats()[i].isSittingOut()) {
						
						if(!table.getSeats()[i].isWaiting){
						g.drawString(table.getSeats()[i].getName(), sPos[i].x,
								sPos[i].y);
						g.drawString("sitting out", sPos[i].x, sPos[i].y + 10);
						}
						else {
							g.drawString(table.getSeats()[i].getName(), sPos[i].x,
									sPos[i].y);
							g.drawString(format1.format(table.getSeats()[i]
									.getStack()), sPos[i].x, sPos[i].y + 15);
							g.drawString("waiting", sPos[i].x, sPos[i].y + 30);
						}
					} 
					else  {
						g.drawString(table.getSeats()[i].getName(), sPos[i].x,
								sPos[i].y);
						g.drawString(format1.format(table.getSeats()[i]
								.getStack()), sPos[i].x, sPos[i].y + 15);
						g.drawString("folded", sPos[i].x, sPos[i].y + 30);
					}
					
					
				} else {
					if (i > 3 && i < 8) {
						g
								.drawImage(getCardImage(table.getSeats()[i]
										.getHand().cardA), cardPos[i*2].x ,
										cardPos[i*2].y, 44, 60, null);

						g
								.drawImage(getCardImage(table.getSeats()[i]
										.getHand().cardB), cardPos[i*2+1].x,
										cardPos[i*2+1].y, 44, 60, null);
					} else if (i > 0 && i < 4) {
						g
								.drawImage(getCardImage(table.getSeats()[i]
										.getHand().cardA), cardPos[i*2].x,
										cardPos[i*2].y, 44, 60, null);

						g
								.drawImage(getCardImage(table.getSeats()[i]
										.getHand().cardB), cardPos[i*2+1].x,
										cardPos[i*2+1].y, 44, 60, null);

					}

					else {

						g
								.drawImage(getCardImage(table.getSeats()[i]
										.getHand().cardB), cardPos[i*2].x,
										 cardPos[i*2].y, 44, 60, null);
						g
								.drawImage(getCardImage(table.getSeats()[i]
										.getHand().cardA), cardPos[i*2+1].x ,
										 cardPos[i*2+1].y, 44, 60, null);

					}

					
					
					if (table.getSeats()[i].getContributed() > 0) {
						for (int q = 14, count = 0; q >= 0; q--) {
							if (table.getSeats()[i].stackChips[q] != 0) {
								for (int z = 0; z < table.getSeats()[i].stackChips[q]; z++) {
									count++;
									g.drawImage(getChipImg(q), chipPos[i].x,
											chipPos[i].y - count * 4, 29, 23,
											null);
								}

							}
						}
					}

					
					g.drawString(table.getSeats()[i].getName(), sPos[i].x,
							sPos[i].y);

					g.drawString(format1.format(table.getSeats()[i].getStack()),
							sPos[i].x, sPos[i].y + 15);
					g.drawString(format1.format(table.getSeats()[i]
							.getContributed()), sPos[i].x, sPos[i].y + 30);
//					g.drawString(f.format(table.getSeats()[i]
//					           .getTContributed()), sPos[i].x, sPos[i].y + 45);
				}
			} else if (table.getSeats()[i] == null) {
				g.drawString("empty", sPos[i].x, sPos[i].y);
			}
		}
		
		//toChips(pot, table.getPot());
		for (int q = 14, denom=0, count = 0; q >= 0; q--) {
			count=0; 
			if (pot[q] != 0) {
				denom++;
				for (int z = 0; z < pot[q]; z++) {
					count++;
					g.drawImage(getChipImg(q), 240+denom*29, 200 - count * 4, 29, 23,
							null);
				}

			}

		}
//		for (int j = 0; j < 15; j++) {
//			if (pot[j] != 0) {
//				System.out.println(pot[j] + " times " + chipVals[j]);
//			}
//		}

		g.drawString("Pot:   "+format1.format(table.getPot()), 350, 50);

		switch (table.getRound()) {

		case 0:
			break;
		case 1:
			for (int i = 0; i < 3; i++) {
				g.drawImage(getCardImage(table.getBoard()[i]), 284 + i * 44,
						105, 44, 60, null);
			}
			break;

		case 2:
			for (int i = 0; i < 4; i++) {
				g.drawImage(getCardImage(table.getBoard()[i]), 284 + i * 44,
						105, 44, 60, null);
			}
			break;
		case 3:
			for (int i = 0; i < 5; i++) {
				g.drawImage(getCardImage(table.getBoard()[i]), 284 + i * 44,
						105, 44, 60, null);
			}
			break;

		}

	}

	private BufferedImage getChipImg(int i) {

		return chips.getSubimage(i % 5 * 24, i / 5 * 19, 24, 19);
		// return chips.getSubimage(i%5*29, i/5*23, 29, 23);

	}

	

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		switch (){
//		
//		}
	
		
		
		if (e.getSource() == betPot) {
			if(table.legalActions[3]){
		table.getSeats()[table.getToAct()].action = 3;
		table.getSeats()[table.getToAct()].amount = (table.getSeats()[table
				.getToAct()].getStack() > (table.getPot()
				+ table.getToCall() - table.getSeats()[table.getToAct()]
				.getContributed())) ? table.getPot() + table.getToCall()
				//- table.getSeats()[table.getToAct()].getContributed()
				: table.getSeats()[table.getToAct()].getStack()+
				table.getSeats()[table.getToAct()].getContributed();
			}
			
			else if(table.legalActions[2]){
				table.getSeats()[table.getToAct()].action = 2;
				table.getSeats()[table.getToAct()].amount = (table.getSeats()[table
						.getToAct()].getStack() > table.getPot()
						+ table.getToCall()) ? table.getPot() + table.getToCall()
						: table.getSeats()[table.getToAct()].getStack();
				}
		return;
	}
		
		
		if (e.getSource() == fold) {
			if(table.legalActions[0]){
				table.getSeats()[table.getToAct()].action = 0;
				table.getSeats()[table.getToAct()].amount = 0;
			}
			return;
		}

		if (e.getSource() == check) {
			if(table.legalActions[1]){
				table.getSeats()[table.getToAct()].action = 1;
				table.getSeats()[table.getToAct()].amount = 0;
			}
			return;
		}
		if (e.getSource() == call) {
			if(table.legalActions[6]){
				table.getSeats()[table.getToAct()].action = 6;
				
				
				table.getSeats()[table.getToAct()].amount = table.getToCall();
			
			}
			return;
		}
		if (e.getSource() == bet) {
			if(table.legalActions[2]){
				if(((Number)table.settings.amountField
					.getValue()).doubleValue() >= table.getBB()
					){
					
					table.getSeats()[table.getToAct()].action = 2;
					table.getSeats()[table.getToAct()].amount = 
						((Number)table.settings.amountField
								.getValue()).doubleValue();
				}
			
			}
		
			return;
		}
		
		
		if (e.getSource() == raise) {
				if(table.legalActions[3]){
					double lastToCall;
//					int index=table.getState().get(table.getRound()).size()-1;
//					Vector<Action> round=table.getState().get(table.getRound());
//					
//					while(index>=0 && !(round.get(index).action
//							>1 && round.get(index).action
//							<6)){
//						index--;
//						}
				//	lastToCall=table.getState().get(table.getRound()).get(index).wager;
					if(((Number)table.settings.amountField
							.getValue()).doubleValue() >= table.getToCall()+table.minRaise
							){
						table.getSeats()[table.getToAct()].action = 3;
						table.getSeats()[table.getToAct()].amount=((Number)table.settings.amountField
								.getValue()).doubleValue();
						
						//System.out.println("last toCall: "+(lastToCall));
						//System.out.println("min raise: "+(table.getToCall()+lastToCall));
						
					}
//			table.getSeats()[table.getToAct()].action = 3;
//			
//			
//			
//			
//			table.getSeats()[table.getToAct()].amount = (table.getSeats()[table
//					.getToAct()].getStack() > (table.getPot()
//					+ table.getToCall() - table.getSeats()[table.getToAct()]
//					.getContributed())) ? table.getPot() + table.getToCall()
//					//- table.getSeats()[table.getToAct()].getContributed()
//					: table.getSeats()[table.getToAct()].getStack()+
//					table.getSeats()[table.getToAct()].getContributed();
				}
			return;
		}
		
		
		if(e.getActionCommand().startsWith("aut")){
			int tmp=(e.getActionCommand().charAt(3)-48);
			if(table.getSeats()[tmp]!=null &&table.getSeats()[tmp].autonomous){
				
				table.getSeats()[tmp].autonomous=false;
			}
			else if(table.getSeats()[tmp]!=null){
				
				table.getSeats()[tmp].autonomous=true;
			}
			
			return;
		}
		if(e.getActionCommand().startsWith("rel")){
			//e.getActionCommand().charAt(5);
			rebuy(e.getActionCommand().charAt(3)-48);
			return;
		}
		
		if (e.getSource() == allAut) {
			if(allAut.isSelected()){
				for(int i=0;i<10;i++){
				
				//autonomous[i].setEnabled(true);
			
					if(!autonomous[i].isSelected()){
						autonomous[i].doClick(0);
					}
				
				}
				
			}
			
			else{
				for(int i=0;i<10;i++){
				
						if(autonomous[i].isSelected()){
							autonomous[i].doClick(0);
						}
					
					}
				
			}
			return;
		}
		


	}


	public void rebuy(int i){
		if(table.getSeats()[i]!=null &&table.getSeats()[i].canReload()){
			table.getSeats()[i].setStack(
					table.getDefaultStackSize
						-table.getSeats()[i].getStack()
					);
				//table.getSeats()[i].setSittingOut(false);
				table.getSeats()[i].isWaiting=true;
				repaint();
			
		}
		return;
	}
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void setPlayer(Player p) {
		player = p;
	}

	public void clearChipCount(int[] pot2) {
		for(int i=0;i<pot2.length; i++){
			pot2[i]=0;
		}
		
	}
}
