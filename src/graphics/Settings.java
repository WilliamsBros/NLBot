package graphics;

import game.Table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class Settings extends JPanel implements ItemListener, 
ChangeListener,PropertyChangeListener{

	Table table;
	Vector<JSlider> sliders=new Vector<JSlider>();
	//JCheckBoxMenuItem bodiesDisappear = new JCheckBoxMenuItem("Bodies Disappear");
	 public JFormattedTextField amountField;
	 private NumberFormat amountFormat;
	 public JLabel amountLabel = new JLabel("Wager");
	
	 public Settings(Table t) {
		 super(new BorderLayout());

//		 JMenuBar menuBar;
//			menuBar = new JMenuBar();
//			JMenu menu = new JMenu("Options");
//			menu.setMnemonic(KeyEvent.VK_A);
//			menu.getAccessibleContext().setAccessibleDescription(
//			        "The only menu in this program that has menu items");
//			
//			menuBar.add(menu);
//			menu.add(bodiesDisappear);
			//this.add(menuBar);

		 
		 
		table=t;
		amountFormat = NumberFormat.getNumberInstance();
		amountFormat.setMaximumFractionDigits(2);
		amountField = new JFormattedTextField(amountFormat);
	    amountField.setValue(new Double(0));
	    amountField.setColumns(10);
	    amountField.addPropertyChangeListener("value", this);
		
		JSlider sleep = new JSlider(SwingConstants.HORIZONTAL,
                0, 40, 1);
		sleep.addChangeListener(this);

		//Turn on labels at major tick marks.
		//sleep.setMajorTickSpacing(10);
		sleep.setMinorTickSpacing(10);
		//maxCreatures.setBounds(100, 100, 100, 20);
		//maxCreatures.setPreferredSize(new Dimension(20,300));
		sleep.setPaintTicks(true);
		sleep.setPaintLabels(true);
		sleep.setName("Sleep");
		sleep.setVisible(true);
		Hashtable<Integer, JLabel> lbls=new Hashtable<Integer,JLabel>();
		lbls.put(0, new JLabel("Fast"));
		lbls.put(40, new JLabel("Slow"));
		sleep.setLabelTable(lbls);
		sleep.setPaintLabels(true);
		//maxCreatures.setBorder(
        //        BorderFactory.createEmptyBorder(0,0,0,10));

		JSlider amount = new JSlider(SwingConstants.HORIZONTAL,
                0, 1000, 1);
		amount.addChangeListener(this);

		//Turn on labels at major tick marks.
		amount.setMajorTickSpacing(250);
		//amount.setMinorTickSpacing(50);
		//maxCreatures.setBounds(100, 100, 100, 20);
		//maxCreatures.setPreferredSize(new Dimension(20,300));
		amount.setPaintTicks(true);
		
		Hashtable<Integer, JLabel> lbls2=new Hashtable<Integer,JLabel>();
		lbls2.put(250, new JLabel("25%"));
		lbls2.put(1000, new JLabel("100%"));
		lbls2.put(500, new JLabel("50%"));
		lbls2.put(750, new JLabel("75%"));
		lbls2.put(0, new JLabel("0%"));
		
		amount.setLabelTable(lbls2);
		amount.setPaintLabels(true);
		amount.setVisible(true);
		
		
//		JSlider maxCreatureO = new JSlider(SwingConstants.HORIZONTAL,
//                0, 1000, 300);
//		maxCreatureO.addChangeListener(this);
//
//		//Turn on labels at major tick marks.
//		maxCreatureO.setMajorTickSpacing(500);
//		maxCreatureO.setMinorTickSpacing(100);
//		//maxCreatures.setBounds(100, 100, 100, 20);
//		//maxCreatures.setPreferredSize(new Dimension(20,300));
//		maxCreatureO.setPaintTicks(true);
//		maxCreatureO.setPaintLabels(true);
//		maxCreatureO.setVisible(true);
		
		sliders.add(sleep);
		sliders.add(amount);
//		sliders.add(maxCreatureO);
		
		//table.frame.getJMenuBar().add(sleep);
//	GridBagLayout grid=new GridBagLayout();
//	GridBagConstraints constraints = new GridBagConstraints();
//	 constraints.weighty = 100;
//     constraints.gridwidth = 1;
//     constraints.gridheight = 1;
//     constraints.gridx = 0;
//     constraints.gridy = 0;
//	
//	
//		this.setLayout(grid);
//
//		
//		constraints.gridy=0;
//		 constraints.gridx = 0;
//	      constraints.anchor = GridBagConstraints.CENTER;
//	      constraints.fill = GridBagConstraints.NONE;
//	      constraints.weightx = .1;
//		this.add(new JLabel("Max Plant Creatures"),constraints);
//		
//		constraints.gridy=0;
//		 constraints.gridx = 1;
//	      constraints.anchor = GridBagConstraints.WEST;
//	      constraints.fill = GridBagConstraints.NONE;
//	      constraints.weightx = .9;
//		this.add(maxCreaturesP,constraints);
//		
//		constraints.gridy=2;
//		 constraints.gridx = 0;
//	      constraints.anchor = GridBagConstraints.CENTER;
//	      constraints.fill = GridBagConstraints.NONE;
//	      constraints.weightx = .1;
//		//grid.setConstraints(this, constraints);
//		this.add(new JLabel("Mutation Rate"),constraints);
//		
//		constraints.gridy=2;
//		 constraints.gridx = 1;
//	      constraints.anchor = GridBagConstraints.WEST;
//	      constraints.fill = GridBagConstraints.NONE;
//	      constraints.weightx = .9;
//		this.add(mutationRate,constraints);
//		
//			constraints.gridy=1;
//			constraints.gridx = 0;
//			constraints.anchor = GridBagConstraints.CENTER;
//			constraints.fill = GridBagConstraints.NONE;
//			constraints.weightx = .1;
//		this.add(new JLabel("Max Eater Creatures"), constraints);
//		
//		constraints.gridy=1;
//		constraints.gridx=1;
//		constraints.anchor = GridBagConstraints.WEST;
//		constraints.fill = GridBagConstraints.NONE;
//		constraints.weightx = .9;
//		this.add(maxCreatureO,constraints);
		//setBorder(BorderFactory.createEmptyBorder(200,200,800,800));
		
	}

public void draw(){
		
		repaint();
	}
	public Settings(LayoutManager arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public Settings(boolean arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public Settings(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		

	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
	
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
	    JSlider source = (JSlider)e.getSource();
	    //if (!source.getValueIsAdjusting()) {
	    	
	    	
	    	if(source.equals(sliders.get(0))){
	    		table.sleep=(source.getValue());
	    		
	    	}
	    	
	    	else if(source.equals(sliders.get(1))){
	    		amountField.setValue(source.getValue()*
	    				(table.getSeats()[table.getToAct()].getStack()+
	    				table.getSeats()[table.getToAct()].getContributed())/1000);
	    		
	    	}
//
//	    	else if(source.equals(sliders.get(2))){
//	    		world.setMaxCreaturesO(source.getValue());
//	    		
//	    	}

	   // }
	}

	
	public void resetWager(){
		amountField.setValue(0);
		sliders.get(1).setValue(0);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		
		if(amountField==arg0.getSource()){

			double amt=((Number)(amountField.getValue())).doubleValue();
			
			if(amt>table.getSeats()[table.getToAct()].getStack()){
				
				amountField.setValue(table.getSeats()[table.getToAct()].getStack()
						+table.getSeats()[table.getToAct()].getContributed()
						);
				//sliders.get(1).setValue(1000);
			}
			else if(amt<0){
				amountField.setValue(0);
				//sliders.get(1).setValue(0);
			}
			else{
				amountField.setValue(amt);
				//sliders.get(1).setValue((int)(1000*amt/table.getSeats()[table.getToAct()].getStack()));
			}
			
		}
		//System.out.println(amountField.getValue());
		
	}


}
