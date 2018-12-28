
package gui;

import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import java.awt.Font;

public class ScheduledSettingsWindow extends JDialog {
	
	private static final long serialVersionUID = 1L;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	}
	

	/**
	 * Create the frame.
	 */
	public ScheduledSettingsWindow(ControllerManager gm, int x, int y) {
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Scheduled mode settings");
		setType(Type.POPUP);
		setBounds(x, y, 586, 207);
		getContentPane().setLayout(null);
		
		final JComboBox<String> cbStartingHour = new JComboBox<String>();
		for(int i = 0; i < 24; i++) {
			cbStartingHour.addItem((i<10?"0":"")+i+":00");
		}
		cbStartingHour.setBounds(52, 12, 70, 23);
		getContentPane().add(cbStartingHour);
		
		JLabel lblStartingHour = new JLabel("Start:");
		lblStartingHour.setBounds(10, 16, 40, 14);
		getContentPane().add(lblStartingHour);
		
		final JComboBox<String> cbEndingHour = new JComboBox<String>();
		cbEndingHour.setBounds(186, 12, 70, 23);
		getContentPane().add(cbEndingHour);
		
		JLabel lblEndingHour = new JLabel("End:");
		lblEndingHour.setBounds(151, 16, 40, 14);
		getContentPane().add(lblEndingHour);
		for(int i = 0; i < 24; i++) {
			cbEndingHour.addItem((i<10?"0":"")+i+":00");
		}
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 42, 550, 13);
		getContentPane().add(separator);
		
		final JComboBox<Integer> cbFlowRate = new JComboBox<Integer>();
		cbFlowRate.setBounds(52, 138, 40, 23);
		getContentPane().add(cbFlowRate);
		for(int i = 0; i < 5; i++) {
			cbFlowRate.addItem(i+1);
		}
		cbFlowRate.setSelectedIndex(gm.ENVmanualModeUserFlow-1);
		
		JLabel lblRate = new JLabel("Rate:");
		lblRate.setBounds(10, 142, 40, 14);
		getContentPane().add(lblRate);
		
		JButton btnCheck = new JButton("Check");
		
		final JCheckBox[] cb_hour = new JCheckBox[24];
		JLabel[] l_hour = new JLabel[24];

		for(int i = 0; i < 24; i++) {
			cb_hour[i] = new JCheckBox();
			cb_hour[i].setBounds(10+i*23, 85, 24, 19);
			//tick if in schedule
			cb_hour[i].setSelected(gm.ENVschedule[i]);
			getContentPane().add(cb_hour[i]);
			l_hour[i] = new JLabel(i+"");
			l_hour[i].setBounds(15+i*23, 65, 24, 19);
			getContentPane().add(l_hour[i]);
		}
		
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkActionListener(true, cbStartingHour, cbEndingHour, cb_hour);
			}
		});
		btnCheck.setBounds(399, 12, 70, 23);
		getContentPane().add(btnCheck);
		
		JButton btnUncheck = new JButton("Uncheck");
		btnUncheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				checkActionListener(false, cbStartingHour, cbEndingHour, cb_hour);
			}
		});
		btnUncheck.setBounds(487, 12, 73, 23);
		getContentPane().add(btnUncheck);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			// closes and updates the schedule
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < 24; i++) {
					gm.ENVschedule[i] = cb_hour[i].isSelected();
					gm.ENVmanualModeUserFlow = cbFlowRate.getSelectedIndex()+1;
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


	
	
	private static void showErrorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}
	
	
	public static void checkActionListener(boolean toCheck, JComboBox<String> cbStartingHour, JComboBox<String> cbEndingHour, JCheckBox[] cb_hour) {
		int startingHour, endingHour;
		
		startingHour = Integer.parseInt(((String)cbStartingHour.getSelectedItem()).substring(0, 2));
		endingHour = Integer.parseInt(((String)cbEndingHour.getSelectedItem()).substring(0, 2));
		if(startingHour == endingHour) {
			showErrorMessage("INVALID PARAMETERS", "Start and end must be in different hours.");
		} else {
			int i = startingHour;
			if(i != -1) i = startingHour;
			while(i!= -1 && i != endingHour && i != endingHour+24) {
				if(i == 24) i = 0;
				cb_hour[i].setSelected(toCheck);
				i++;
			}
		}
		
	}
}