
package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.org.glassfish.gmbal.GmbalException;

import sun.awt.resources.awt;

import java.awt.Window.Type;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Panel;
import java.awt.Choice;
import java.awt.Color;

import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Canvas;
import java.awt.Checkbox;
import javax.swing.JTable;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ScheduledSettingsWindow extends JDialog {
	
	int[] schedule;
	int[] newSchedule;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
/*		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ScheduledSettingsWindow dialog = new ScheduledSettingsWindow();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}

	/**
	 * Create the frame.
	 */
	public ScheduledSettingsWindow(int[] schedule, int x, int y) {
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.schedule = schedule;
		newSchedule = new int[24];
		setTitle("Scheduled mode settings");
		setType(Type.POPUP);
		setBounds(x, y, 586, 207);
		getContentPane().setLayout(null);
		
		JComboBox<String> cbStartingHour = new JComboBox<String>();
		for(int i = 0; i < 24; i++) {
			cbStartingHour.addItem((i<10?"0":"")+i+":00");
		}
		cbStartingHour.setBounds(62, 12, 70, 23);
		getContentPane().add(cbStartingHour);
		
		JLabel lblStartingHour = new JLabel("Start:");
		lblStartingHour.setBounds(20, 16, 40, 14);
		getContentPane().add(lblStartingHour);
		
		JComboBox<String> cbEndingHour = new JComboBox<String>();
		cbEndingHour.setBounds(200, 12, 70, 23);
		getContentPane().add(cbEndingHour);
		
		JLabel lblEndingHour = new JLabel("End:");
		lblEndingHour.setBounds(165, 16, 40, 14);
		getContentPane().add(lblEndingHour);
		for(int i = 0; i < 24; i++) {
			cbEndingHour.addItem((i<10?"0":"")+i+":00");
		}
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 42, 550, 13);
		getContentPane().add(separator);
		
		JComboBox<Integer> cbFlowRate = new JComboBox<Integer>();
		cbFlowRate.setBounds(340, 12, 57, 23);
		getContentPane().add(cbFlowRate);
		for(int i = 0; i < 5; i++) {
			cbFlowRate.addItem(i+1);
		}
		
		JLabel lblRate = new JLabel("Rate:");
		lblRate.setBounds(300, 16, 40, 14);
		getContentPane().add(lblRate);
		
		JButton btnAdd = new JButton("Add");
		
		TextField[] t_hour = new TextField[24];
		JLabel[] l_hour = new JLabel[24];

		for(int i = 0; i < 24; i++) {
			t_hour[i] = new TextField("0");
			t_hour[i].setEditable(false);
			t_hour[i].setBackground(new Color(0xffffff));
			t_hour[i].setBounds(10+i*23, 85, 24, 19);
			getContentPane().add(t_hour[i]);
			l_hour[i] = new JLabel(i+"");
			l_hour[i].setBounds(10+i*23, 65, 24, 14);
			getContentPane().add(l_hour[i]);
		}
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int startingHour, endingHour, flowRate;
				
				startingHour = Integer.parseInt(((String)cbStartingHour.getSelectedItem()).substring(0, 2));
				endingHour = Integer.parseInt(((String)cbEndingHour.getSelectedItem()).substring(0, 2));
				flowRate = (Integer)cbFlowRate.getSelectedItem();
				if(startingHour == endingHour) {
					showErrorMessage("INVALID PARAMETERS", "Start and end have to be in different hours.");
				} else {
					int i = startingHour;
					while(i != endingHour && i != endingHour+24) {
						if(i == 24) i = 0;
						// tried to set contradicting values
						if(newSchedule[i] != 0 && newSchedule[i] != flowRate) {
							showErrorMessage("CONTRADICTING VALUES", "Contradicting values selected.\nPlease change values and try again.");
							i = -1; // used to indicate that an error occurred
							break;
						}
						i++;
					}
					if(i != -1) i = startingHour;
					while(i!= -1 && i != endingHour && i != endingHour+24) {
						if(i == 24) i = 0;
						newSchedule[i] = flowRate;
						t_hour[i].setText(flowRate+"");
						i++;
					}
				}
			}
		});
		btnAdd.setBounds(483, 12, 70, 23);
		getContentPane().add(btnAdd);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			// closes and updates the schedule
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < 24; i++) {
					schedule[i] = newSchedule[i];
				}
				dispose();
			}
		});
		btnOk.setBounds(378, 138, 89, 23);
		getContentPane().add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			// closes without updating the schedule
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(471, 138, 89, 23);
		getContentPane().add(btnCancel);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	
	private static void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}
}