/**
 * 
 */
package application;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * CS 4500/5500 Advanced OO Design and GUI Techniques.
 * <br>This class is written as part of the course cs4500/5500 
 * under guidance and instruction of Dr. Dusan Sormaz.
 * <br>This class is used to create a dialog when user wants to create a new arc.
 * The dialog allows user to set arc type and relation.
 * @author Arif
 * @Date 11 Sep 2013
 * 
 */
@SuppressWarnings("serial")
public class NewArcDialog extends JDialog {

	public final static String UNDIRECTED = "Undirected";
	public final static String DIRECTED = "Directed";
	private String[] arcTypesString = { UNDIRECTED, DIRECTED };
	private JComboBox<String> arcType = new JComboBox<String>(arcTypesString);
	private JLabel nodeLabel1 = new JLabel("Node 1:"); 
	private JLabel nodeLabel2 = new JLabel("Node 2:");
	private JTextField relation;
	
	public NewArcDialog(JFrame parent, DialogCloseListener caller, String nodeName1, final String nodeName2) {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setTitle("Add new arc");
		
		JPanel content = new JPanel(new GridLayout(5,1));
				
		JPanel content1 = new JPanel();		
		JPanel content2 = new JPanel();		
		JPanel content3 = new JPanel();		
		JPanel content4 = new JPanel();		
		JPanel content5 = new JPanel();	
			
		JLabel typeLabel = new JLabel("Arc Type:");		
		arcType.addActionListener(new arcTypeChangeListener());
		arcType.setSelectedIndex(0);
		
		JLabel relationLabel = new JLabel("Relation:");
		relation = new JTextField(12);	

		JButton addArcButton = new JButton("Ok");
		
		class AddArc implements ActionListener {
			
			private ArrayList<DialogCloseListener> observers = new ArrayList<DialogCloseListener>();
			
			public AddArc(DialogCloseListener caller) {
				addListener(caller);
			}
			
			public void addListener(DialogCloseListener observer) {
				observers.add(observer);
			}
			
			public void notifyOfDialogClose() {
				
				for (DialogCloseListener observer: observers) {
					observer.onDialogClose();
				}
				
			}
			
			public void actionPerformed(ActionEvent event) {		

				if (!relation.getText().equals("")) {
					notifyOfDialogClose();
					dispose();
				}
			}
			
		}
		
		addArcButton.addActionListener(new AddArc(caller));
		
		Border etched = BorderFactory.createEtchedBorder();
		content1.setBorder(etched);
		content2.setBorder(etched);
		content3.setBorder(etched);
		content4.setBorder(etched);
		content5.setBorder(etched);
		
		content1.add(typeLabel);
		content1.add(arcType);
		content2.add(nodeLabel1);
		content2.add(new JLabel(nodeName1));
		content3.add(nodeLabel2);
		content3.add(new JLabel(nodeName2));
		content4.add(relationLabel);
		content4.add(relation);		
		content5.add(addArcButton);
		
		content.add(content1);
		content.add(content2);
		content.add(content3);
		content.add(content4);
		content.add(content5);
		
		setContentPane(content);
	}
	
	private class arcTypeChangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
	        @SuppressWarnings("rawtypes")
			JComboBox source = (JComboBox)e.getSource();
	        String arcType = (String) source.getSelectedItem();

	        if (arcType.equals(arcTypesString[0])) {
	        	nodeLabel1.setText("Node 1:");
	        	nodeLabel2.setText("Node 2:");
	        } else {
	        	nodeLabel1.setText("Parent Node:"); 
	        	nodeLabel2.setText("Child Node:");
			}
		}
		
	}
	
	public String getArcType() {
        return arcTypesString[arcType.getSelectedIndex()];
	}
	
	public String getRelation() {
		return relation.getText().replace(" ", "_");
	}
	
}
