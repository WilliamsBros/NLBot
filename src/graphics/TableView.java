package graphics;

	import game.Table;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JMenuBar;
	import javax.swing.JPanel;
	import javax.swing.JSlider;
	import javax.swing.event.ChangeEvent;
	import javax.swing.event.ChangeListener;
	import javax.swing.event.MouseInputListener;

import player.Player;

public class TableView  extends JPanel implements MouseInputListener,
			ActionListener, ChangeListener {

		JFrame frame;
		Player player;
		Table table;
		JMenuBar menu = new JMenuBar();

		JButton fold = new JButton("fold");
		JButton check = new JButton("check");
		JButton bet = new JButton("bet");
		JButton raise = new JButton("raise");
		JButton call = new JButton("call");
		BufferedImage tbl;
		

	
		// JButton clear=new JButton("clear");
		// JSlider amount=new JSlider(0,(int)player.getStack(),0);

		public TableView(Table t) {
			 try {
				  tbl=ImageIO.read(new File("C:" +
		 		"\\Documents and Settings\\ccadmin.LT-SF-0XX\\Desktop\\" +
		 		"java\\NLBot\\src\\Texas_Holdem_Poker_Table.jpg"));
		        } catch (IOException e) {
		            System.out.println("Image could not be read");
		            System.exit(1);
		        }
		        setBackground(Color.white);
		       // C:" +
		 		//"\\Documents and Settings\\ccadmin.LT-SF-0XX\\Desktop\\" +
		 		//"java\\NLBot\\src\\Texas_Holdem_Poker_Table.jpg
				//C:\\Documents and Settings\\ccadmin.LT-SF-0XX\\Desktop\\java\\NLBot\\src\\clip_image004_2.gif    
			table=t;
			frame = table.frame;

			frame.setSize(600, 300);
			// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(this);

			fold.addActionListener(this);
			check.addActionListener(this);
			call.addActionListener(this);
			bet.addActionListener(this);
			raise.addActionListener(this);
			// amount.addChangeListener(this);

			menu.add(fold);
			menu.add(check);
			menu.add(call);
			menu.add(bet);
			menu.add(raise);
			// menu.add(amount);

			// frame.setTitle(player.getName());
			frame.setJMenuBar(menu);
			addMouseListener(this);
			frame.setVisible(true);

		}

		
		
		
		public void draw() {

			repaint();
			
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//g.setColor(Color.red);
			g.drawImage(tbl, 0, 0, 572, 240, new Color(255,255,255), this);
			//g.draw3DRect(10, 10, 200, 200, true);
//			for(int i=0)
//			g.drawString(arg0, arg1, arg2)
			
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
			if (e.getSource() == fold) {
				 
					table.getSeats()[table.getToAct()].action = 0;
					table.getSeats()[table.getToAct()].amount = 0;
				

				return;
			}

			if (e.getSource() == check) {
				
				table.getSeats()[table.getToAct()].action = 1;
				table.getSeats()[table.getToAct()].amount = 0;
				
				return;
			}
			if (e.getSource() == call) {
				
				table.getSeats()[table.getToAct()].action = 6;
				table.getSeats()[table.getToAct()].amount = table.getToCall();
				

				return;
			}
			if (e.getSource() == bet) {
				
				table.getSeats()[table.getToAct()].action = 2;
				table.getSeats()[table.getToAct()].amount = 
					(table.getSeats()[table.getToAct()].getStack() > table.getPot()
							+ table.getToCall()) ? table
							.getPot()
							+ table.getToCall() : table.getSeats()[table.getToAct()].getStack();
				

				return;
			}
			if (e.getSource() == raise) {

				table.getSeats()[table.getToAct()].action = 2;
				table.getSeats()[table.getToAct()].amount = (table.getSeats()[table.getToAct()].getStack() > (table.getPot()
							+ table.getToCall()-table.getSeats()[table.getToAct()].getContributed())) ? table
							.getPot()
							+ table.getToCall()-table.getSeats()[table.getToAct()].getContributed() : table.getSeats()[table.getToAct()].getStack();
				

				return;
			}
			// if(e.getSource()==clear){
			// world.clrImstruct();
			// repaint();
			// return;
			// }

		}

		@Override
		public void stateChanged(ChangeEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void setPlayer(Player p){
			player=p;
		}
	}


