package graphics;

import game.Table;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
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

import UofAHandEval.ca.ualberta.cs.poker.Card;

import player.Player;

public class TableView extends JPanel implements MouseInputListener,
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
	BufferedImage cardImgs;
	Point[] sPos = new Point[10];

	// JButton clear=new JButton("clear");
	// JSlider amount=new JSlider(0,(int)player.getStack(),0);

	public TableView(Table t) {
		try {
			tbl = ImageIO.read(new File("C:"
					+ "\\Documents and Settings\\ccadmin.LT-SF-0XX\\Desktop\\"
					+ "java\\NLBot\\src\\Texas_Holdem_Poker_Table.jpg"));
			cardImgs = ImageIO.read(new File("C:"
					+ "\\Documents and Settings\\ccadmin.LT-SF-0XX\\Desktop\\"
					+ "java\\NLBot\\src\\clip_image004_2.gif"));
		} catch (IOException e) {
			System.out.println("Image could not be read");
			System.exit(1);
		}

		setBackground(Color.white);
		// C:" +
		// "\\Documents and Settings\\ccadmin.LT-SF-0XX\\Desktop\\" +
		// "java\\NLBot\\src\\Texas_Holdem_Poker_Table.jpg
		// C:\\Documents and
		// Settings\\ccadmin.LT-SF-0XX\\Desktop\\java\\NLBot\\src\\clip_image004_2.gif
		table = t;
		frame = table.frame;

		frame.setSize(780, 480);
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

		sPos[0] = new Point(130, 50);
		sPos[1] = new Point(610, 50);
		sPos[2] = new Point(680, 120);
		sPos[3] = new Point(710, 210);
		sPos[4] = new Point(560, 340);
		sPos[5] = new Point(440, 340);
		sPos[6] = new Point(320, 340);
		sPos[7] = new Point(200, 340);
		sPos[8] = new Point(40, 210);
		sPos[9] = new Point(70, 120);

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

		for (int i = 0; i < 10; i++) {
			// g.setColor(Color.black);
			if (i == table.getToAct()) {
				g.setColor(Color.red);
				// g.fillOval(sPos[i].x+2, sPos[i].y+30, 10, 10);

			}
			g.drawRoundRect(sPos[i].x, sPos[i].y, 45, 45, 10, 10);
			g.setColor(Color.black);
			
			if (table.getSeats()[i] != null) {
				if (!table.getSeats()[i].isLive()) {
					if (table.getSeats()[i].isSittingOut()) {
						g.drawString(table.getSeats()[i].getName(), sPos[i].x,
								sPos[i].y);
						g.drawString("sitting out", sPos[i].x, sPos[i].y + 10);
					} else {
//						g
//								.drawImage(getCardImage(table.getSeats()[i]
//										.getHand().cardA), sPos[i].x - 88,
//										sPos[i].y, 44, 60, null);
//						g
//								.drawImage(getCardImage(table.getSeats()[i]
//										.getHand().cardB), sPos[i].x - 44,
//										sPos[i].y, 44, 60, null);
						g.drawString(table.getSeats()[i].getName(), sPos[i].x,
								sPos[i].y);
						g.drawString(Double.toString(table.getSeats()[i]
								.getStack()), sPos[i].x, sPos[i].y + 15);
						g.drawString("folded", sPos[i].x, sPos[i].y + 30);
					}
				} else {
					if(i>3 && i<8){
						g.drawImage(
								getCardImage(table.getSeats()[i].getHand().cardA),
								sPos[i].x -5, sPos[i].y
										-70, 44, 60, null);
						
						g.drawImage(
								getCardImage(table.getSeats()[i].getHand().cardB),
								sPos[i].x+6, sPos[i].y-70, 44, 60, null);
					}
					else if(i>0 && i<4){
					g.drawImage(
							getCardImage(table.getSeats()[i].getHand().cardA),
							sPos[i].x -60, sPos[i].y
									-10, 44, 60, null);
					
					
					g.drawImage(
							getCardImage(table.getSeats()[i].getHand().cardB),
							sPos[i].x-49, sPos[i].y-10, 44, 60, null);
					
				
					}
					
					
					else{
						
						g.drawImage(
								getCardImage(table.getSeats()[i].getHand().cardB),
								sPos[i].x+49, sPos[i].y, 44, 60, null);
						g.drawImage(
								getCardImage(table.getSeats()[i].getHand().cardA),
								sPos[i].x +60, sPos[i].y
										, 44, 60, null);
						
						
						
					}
					
					
					g.drawString(table.getSeats()[i].getName(), sPos[i].x,
							sPos[i].y);
					
					g.drawString(Double
							.toString(table.getSeats()[i].getStack()),
							sPos[i].x, sPos[i].y + 15);
					g.drawString(Double.toString(table.getSeats()[i]
							.getContributed()), sPos[i].x, sPos[i].y + 30);
				}
			} else if (table.getSeats()[i] == null) {
				g.drawString("empty", sPos[i].x, sPos[i].y);
			}
		}

		switch (table.getRound()) {

		case 0:
			break;
		case 1:
			for (int i = 0; i < 3; i++) {
				g.drawImage(getCardImage(table.getBoard()[i]), 240 + i * 44,
						145, 44, 60, null);
			}
			break;

		case 2:
			for (int i = 0; i < 4; i++) {
				g.drawImage(getCardImage(table.getBoard()[i]), 240 + i * 44,
						145, 44, 60, null);
			}
			break;
		case 3:
			for (int i = 0; i < 5; i++) {
				g.drawImage(getCardImage(table.getBoard()[i]), 240 + i * 44,
						145, 44, 60, null);
			}
			break;

		}

	}

	private int cardOffsetY(int i) {
		switch (i) {

		case 0:
			return (-10);
		case 1:
			return (-10);
		case 2:
			return (-10);
		case 3:
			return (-10);
		case 4:
			return (-60);
		case 5:
			return (-60);
		case 6:
			return (-60);
		case 7:
			return (-60);
		case 8:
			return (0);
		case 9:
			return (0);
		}
		return 0;
	}

	private int cardOffsetX(int i) {
		switch (i) {

		case 0:
			return (44);
		case 1:
			return (-44);
		case 2:
			return (-44);
		case 3:
			return (-44);
		case 4:
			return (22);
		case 5:
			return (0);
		case 6:
			return (0);
		case 7:
			return (0);
		case 8:
			return (0);
		case 9:
			return (0);
		}
		return 0;
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
			table.getSeats()[table.getToAct()].amount = (table.getSeats()[table
					.getToAct()].getStack() > table.getPot()
					+ table.getToCall()) ? table.getPot() + table.getToCall()
					: table.getSeats()[table.getToAct()].getStack();

			return;
		}
		if (e.getSource() == raise) {

			table.getSeats()[table.getToAct()].action = 2;
			table.getSeats()[table.getToAct()].amount = (table.getSeats()[table
					.getToAct()].getStack() > (table.getPot()
					+ table.getToCall() - table.getSeats()[table.getToAct()]
					.getContributed())) ? table.getPot() + table.getToCall()
					- table.getSeats()[table.getToAct()].getContributed()
					: table.getSeats()[table.getToAct()].getStack();

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

	public void setPlayer(Player p) {
		player = p;
	}
}
