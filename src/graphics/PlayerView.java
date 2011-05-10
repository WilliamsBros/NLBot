package graphics;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

import player.Player;

public class PlayerView extends JPanel implements MouseInputListener,
		ActionListener, ChangeListener {

	JFrame frame;
	Player player;
	JMenuBar menu = new JMenuBar();

	JButton fold = new JButton("fold");
	JButton check = new JButton("check");
	JButton bet = new JButton("bet");
	JButton raise = new JButton("raise");
	JButton call = new JButton("call");

	// JButton clear=new JButton("clear");
	// JSlider amount=new JSlider(0,(int)player.getStack(),0);

	public PlayerView(Player p) {
		player = p;
		frame = player.frame;

		frame.setSize(460, 100);
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
		
//		g.
//		for(int i=0)
//		g.drawString(arg0, arg1, arg2)
		
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
			if (player == player.getTable().getSeats()[player.getTable()
					.getToAct()]) {
				player.action = 0;
				player.amount = 0;
			}

			return;
		}

		if (e.getSource() == check) {
			if (player == player.getTable().getSeats()[player.getTable()
					.getToAct()]) {
				player.action = 1;
				player.amount = 0;
			}
			return;
		}
		if (e.getSource() == call) {
			if (player == player.getTable().getSeats()[player.getTable()
					.getToAct()]) {
				player.action = 6;
				player.amount = player.getTable().getToCall();
			}

			return;
		}
		if (e.getSource() == bet) {
			if (player == player.getTable().getSeats()[player.getTable()
					.getToAct()]) {
				player.action = 2;
				player.amount = (player.getStack() > player.getTable().getPot()
						+ player.getTable().getToCall()) ? player.getTable()
						.getPot()
						+ player.getTable().getToCall() : player.getStack();
			}

			return;
		}
		if (e.getSource() == raise) {
			if (player == player.getTable().getSeats()[player.getTable()
					.getToAct()]) {

				player.action = 2;
				player.amount = (player.getStack() > (player.getTable().getPot()
						+ player.getTable().getToCall()-player.getContributed())) ? player.getTable()
						.getPot()
						+ player.getTable().getToCall()-player.getContributed() : player.getStack();
			}

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
