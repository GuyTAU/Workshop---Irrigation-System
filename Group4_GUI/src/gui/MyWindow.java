package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import sun.misc.GC;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Scrollbar;
import java.awt.Button;
import java.awt.Label;
import java.awt.Canvas;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import misc.ControllerExecutorException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.TextArea;
import javax.swing.JTextPane;
//import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyWindow extends JFrame {

	//Controller manager
	public ControllerManager gm = new ControllerManager();
	//Graphics
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private final ButtonGroup buttonGroup_3 = new ButtonGroup();
	private JLabel alertLabel;
	private JLabel moistureLevelLabel;
	private JLabel timeLabel;
	private JLabel temperatureLabel;
	private JLabel modeLabel;
	private JLabel irrigationFlowLabel;
	private JLabel[] cloudLabels = new JLabel[3];
	private TextField lowerBoundField;
	private TextField upperBoundField;
	private TextField textField_1; //Here user enters upper bound
	private TextField textField_3; //Here user enters lower bound
	
	//flags for set values
	private boolean tempSet = false;
	private boolean modeSet = false;
	private boolean irrigationFlowSet = false;
	private boolean rainSet = false;
	boolean lowerBoundSet = false;
	boolean upperBoundSet = false;


	
	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyWindow frame = new MyWindow();
					//frame.gm = new ControllerManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		setResizable(false);
		setTitle("Irrigation System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		final MyWindow selfRef = this;
		
		/*
		 * Temperature buttons
		 */
		JMenu mnTemperature = new JMenu("Temperature");
		menuBar.add(mnTemperature);
		
		JCheckBoxMenuItem chckbxmntmCold = new JCheckBoxMenuItem("Cold");
		chckbxmntmCold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVtemperature = 0;
				tempSet = true;
			}
		});
		buttonGroup.add(chckbxmntmCold);
		mnTemperature.add(chckbxmntmCold);
		
		JCheckBoxMenuItem chckbxmntmMedium = new JCheckBoxMenuItem("Medium");
		chckbxmntmMedium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVtemperature = 1;
				tempSet = true;
			}
		});
		buttonGroup.add(chckbxmntmMedium);
		mnTemperature.add(chckbxmntmMedium);
		
		JCheckBoxMenuItem chckbxmntmHot = new JCheckBoxMenuItem("Hot");
		chckbxmntmHot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVtemperature = 2;
				tempSet = true;
			}
		});
		buttonGroup.add(chckbxmntmHot);
		mnTemperature.add(chckbxmntmHot);
		
		
		
		
		/*
		 * Rain buttons
		 */
		JMenu mnRainPower = new JMenu("Rain Power");
		menuBar.add(mnRainPower);
		
		JCheckBoxMenuItem chckbxmntmNone = new JCheckBoxMenuItem("None");
		chckbxmntmNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVrainPower = 0;
				rainSet = true;
			}
		});
		buttonGroup_1.add(chckbxmntmNone);
		mnRainPower.add(chckbxmntmNone);
		
		JCheckBoxMenuItem chckbxmntmLight = new JCheckBoxMenuItem("Light");
		chckbxmntmLight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVrainPower = 1;
				rainSet = true;
			}
		});
		buttonGroup_1.add(chckbxmntmLight);
		mnRainPower.add(chckbxmntmLight);
		
		JCheckBoxMenuItem chckbxmntmModerate = new JCheckBoxMenuItem("Moderate");
		chckbxmntmModerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVrainPower = 2;
				rainSet = true;
			}
		});
		buttonGroup_1.add(chckbxmntmModerate);
		mnRainPower.add(chckbxmntmModerate);
		
		JCheckBoxMenuItem chckbxmntmHeavy = new JCheckBoxMenuItem("Heavy");
		chckbxmntmHeavy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVrainPower = 3;
				rainSet = true;
			}
		});
		buttonGroup_1.add(chckbxmntmHeavy);
		mnRainPower.add(chckbxmntmHeavy);
		
		
		/*
		 * Mode buttons
		 */
		JMenu mnNewMenu = new JMenu("Mode");
		menuBar.add(mnNewMenu);
		
		JCheckBoxMenuItem chckbxmntmAutomatic = new JCheckBoxMenuItem("Automatic");
		chckbxmntmAutomatic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmode = 0;
				modeSet = true;
			}
		});
		buttonGroup_2.add(chckbxmntmAutomatic);
		mnNewMenu.add(chckbxmntmAutomatic);
		
		JCheckBoxMenuItem chckbxmntmManual = new JCheckBoxMenuItem("Manual");
		chckbxmntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmode = 1;
				modeSet = true;
			}
		});
		buttonGroup_2.add(chckbxmntmManual);
		mnNewMenu.add(chckbxmntmManual);
		
		JCheckBoxMenuItem chckbxmntmScheduled = new JCheckBoxMenuItem("Scheduled");
		chckbxmntmScheduled.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmode = 2;
				modeSet = true;
				Frame frame = Frame.getFrames()[0];
				ScheduledSettingsWindow scheduledWindow = new ScheduledSettingsWindow(gm.ENVschedule,
																						frame.getLocation().x + frame.getWidth()/7,
																						frame.getLocation().y + frame.getHeight()/3);
				scheduledWindow.setVisible(true);
			}
		});
		buttonGroup_2.add(chckbxmntmScheduled);
		mnNewMenu.add(chckbxmntmScheduled);
		
		
		
		/*
		 * Manual Irrigation Flow buttons
		 */
		JMenu mnManualIrrigationFlow = new JMenu("Manual Irrigation Flow");
		menuBar.add(mnManualIrrigationFlow);
		
		JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("0");
		checkBoxMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 0;
				irrigationFlowSet = true;
			}
		});
		buttonGroup_3.add(checkBoxMenuItem);
		mnManualIrrigationFlow.add(checkBoxMenuItem);
		
		JCheckBoxMenuItem checkBoxMenuItem_1 = new JCheckBoxMenuItem("1");
		checkBoxMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 1;
				irrigationFlowSet = true;
			}
		});
		buttonGroup_3.add(checkBoxMenuItem_1);
		mnManualIrrigationFlow.add(checkBoxMenuItem_1);
		
		JCheckBoxMenuItem checkBoxMenuItem_2 = new JCheckBoxMenuItem("2");
		checkBoxMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 2;
				irrigationFlowSet = true;
			}
		});
		buttonGroup_3.add(checkBoxMenuItem_2);
		mnManualIrrigationFlow.add(checkBoxMenuItem_2);
		
		JCheckBoxMenuItem checkBoxMenuItem_3 = new JCheckBoxMenuItem("3");
		checkBoxMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 3;
				irrigationFlowSet = true;
			}
		});
		buttonGroup_3.add(checkBoxMenuItem_3);
		mnManualIrrigationFlow.add(checkBoxMenuItem_3);
		
		JCheckBoxMenuItem checkBoxMenuItem_4 = new JCheckBoxMenuItem("4");
		checkBoxMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 4;
				irrigationFlowSet = true;
			}
		});
		buttonGroup_3.add(checkBoxMenuItem_4);
		mnManualIrrigationFlow.add(checkBoxMenuItem_4);
		
		JCheckBoxMenuItem checkBoxMenuItem_5 = new JCheckBoxMenuItem("5");
		checkBoxMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 5;
				irrigationFlowSet = true;
			}
		});
		buttonGroup_3.add(checkBoxMenuItem_5);
		mnManualIrrigationFlow.add(checkBoxMenuItem_5);
		
		
		/*
		 * Update state button
		 */
		Button button_3 = new Button("Update state");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//take value of lower bound and upper bound
				if (textField_1.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Upper bound value was not inserted", "Input is missing", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					gm.ENVupperBound = Integer.parseInt(textField_1.getText());
					if (gm.ENVupperBound > 15 || gm.ENVupperBound < 0) {
						JOptionPane.showMessageDialog(contentPane, "Upper bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
						return;
					}
					upperBoundSet = true;
				}
				if (textField_3.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Lower bound value was not inserted", "Input is missing", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					gm.ENVlowerBound = Integer.parseInt(textField_3.getText());
					if (gm.ENVlowerBound > 15 || gm.ENVlowerBound < 0) {
						JOptionPane.showMessageDialog(contentPane, "Lower bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
						return;
					}
					lowerBoundSet = true;
				}
				//Check all value were set by user
				if (!tempSet) {
					JOptionPane.showMessageDialog(contentPane, "You must set Temperature in order to proceed", "Input is missing", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!rainSet) {
					JOptionPane.showMessageDialog(contentPane, "You must set Rain Power in order to proceed", "Input is missing", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!modeSet) {
					JOptionPane.showMessageDialog(contentPane, "You must set Mode in order to proceed", "Input is missing", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!irrigationFlowSet) {
					JOptionPane.showMessageDialog(contentPane, "You must set Manual Irrigation Flow in order to proceed", "Input is missing", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Push value to controller and get outputs.
				try {
					gm.updateState();
				} catch (ControllerExecutorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updatePicture();
			}
		});
		menuBar.add(button_3);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 591, 518);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		/*
		 * Time label.
		 */
		JLabel label = new JLabel("");
		label.setBounds(10, 58, 204, 47);
		panel.add(label);
		label.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
		label.setText("Time: " + String.valueOf(gm.ENVtime) + ":00");
		timeLabel = label;
		
		
		/*
		 * Temperature label.
		 */
		JLabel lblTemperatue = new JLabel("Temperature:");
		lblTemperatue.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
		lblTemperatue.setBounds(10, 107, 204, 47);
		panel.add(lblTemperatue);
		temperatureLabel = lblTemperatue;
		
		
		/*
		 * Mode label
		 */
		JLabel lblMode = new JLabel("Mode:");
		lblMode.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
		lblMode.setBounds(10, 165, 204, 47);
		panel.add(lblMode);
		modeLabel = lblMode;
		
		
		
		/*
		 * Clouds labels
		 */
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(224, 31, 250, 159);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setIcon(new ImageIcon("img/cloud 1.png"));
		panel.add(lblNewLabel_2);
		cloudLabels[0] = lblNewLabel_2;
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(224, 31, 250, 159);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setIcon(new ImageIcon("img/cloud 2.png"));
		panel.add(lblNewLabel_3);
		cloudLabels[1] = lblNewLabel_3;
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(224, 31, 250, 159);
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setIcon(new ImageIcon("img/cloud 3.png"));
		panel.add(lblNewLabel_4);
		cloudLabels[2] = lblNewLabel_4;
		
		

		
		/*
		 * Text field for lower bound
		 */
		textField_1 = new TextField();
		textField_1.setBounds(157, 256, 24, 19);
		panel.add(textField_1);
		lowerBoundField = textField_1;

		
		
		/*
		 * Text field for upper bound
		 */
		textField_3 = new TextField();
		textField_3.setBounds(157, 295, 24, 19);
		panel.add(textField_3);
		upperBoundField = textField_3;

		
		
		/*
		 * Alert label
		 */
		JLabel label_1 = new JLabel("");
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(464, 461, 38, 46);
		label_1.setForeground(Color.WHITE);
		label_1.setIcon(new ImageIcon("img/alert.png"));
		panel.add(label_1);
		alertLabel = label_1;
		
		
		/*
		 * Moisture level label
		 */
		JLabel lblMoistureLevel = new JLabel("Moisture Level:");
		lblMoistureLevel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
		lblMoistureLevel.setBounds(10, 11, 204, 47);
		panel.add(lblMoistureLevel);
		moistureLevelLabel = lblMoistureLevel;
		
		
		
		
		/*
		 * Simulation buttons
		 */
		Button button = new Button("Simulation 1");
		button.setBounds(634, 234, 140, 74);
		contentPane.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					simulation1(selfRef);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ControllerExecutorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Button button_1 = new Button("Simulation 2");
		button_1.setBounds(634, 343, 140, 74);
		contentPane.add(button_1);
		
		Button button_2 = new Button("Simulation 3");
		button_2.setBounds(634, 455, 140, 74);
		contentPane.add(button_2);
		
		
		
		/*
		 * Constant pictures and texts.
		 */
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(187, 213, 256, 294);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon("img/tree.jpg"));
		panel.add(lblNewLabel);

		
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setIcon(new ImageIcon("img/irrigation logo.jpg"));
		lblNewLabel_1.setBounds(634, 11, 140, 178);
		contentPane.add(lblNewLabel_1);
		
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(453, 367, 81, 54);
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setIcon(new ImageIcon("img/tap.jpg"));
		panel.add(lblNewLabel_5);
		
		
		TextField textField = new TextField();
		textField.setBackground(Color.RED);
		textField.setEditable(false);
		textField.setFont(new Font("Dialog", Font.PLAIN, 14));
		textField.setText("Required Lower Bound");
		textField.setBounds(10, 295, 143, 19);
		panel.add(textField);
		
		
		TextField textField_2 = new TextField();
		textField_2.setBackground(Color.CYAN);
		textField_2.setText("Required Upper Bound");
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 14));
		textField_2.setEditable(false);
		textField_2.setBounds(10, 256, 143, 19);
		panel.add(textField_2);
		
		
		JLabel lblOutputs = new JLabel("Outputs:");
		lblOutputs.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		lblOutputs.setBounds(464, 292, 88, 47);
		panel.add(lblOutputs);
		
		JLabel label_2 = new JLabel("");
		label_2.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		label_2.setBounds(543, 367, 38, 47);
		panel.add(label_2);
		irrigationFlowLabel = label_2;
		
		
	}
	
	void updatePicture() {
		this.alertLabel.setVisible(gm.SYSdeviationAlert);
		this.upperBoundField.setEditable(false); //once decided = cannot be changed
		this.lowerBoundField.setEditable(false); //once decided = cannot be changed
		this.timeLabel.setText(("Time: " + String.valueOf(gm.ENVtime) + ":00"));
		this.moistureLevelLabel.setText(("Moisture Level: " + String.valueOf(gm.ENVmoistureLevel)));
		this.irrigationFlowLabel.setText((String.valueOf(gm.SYSirrigationFlow)));
		//Update Mode label
		if (gm.ENVmode == 0) {
			this.modeLabel.setText("Mode: Automatic");
		}
		else if (gm.ENVmode == 1) {
			this.modeLabel.setText("Mode: Manual");
		}
		if (gm.ENVtemperature == 0) {
			this.temperatureLabel.setText("Temperature: Cold");
		}
		//Update Temperature label
		if (gm.ENVtemperature == 1) {
			this.temperatureLabel.setText("Temperature: Medium");
		}
		if (gm.ENVtemperature == 2) {
			this.temperatureLabel.setText("Temperature: Hot");
		}
		//Update Clouds labels
		if (gm.ENVrainPower == 0) {
			this.cloudLabels[0].setVisible(false);
			this.cloudLabels[1].setVisible(false);
			this.cloudLabels[2].setVisible(false);
		}
		if (gm.ENVrainPower == 1) {
			this.cloudLabels[0].setVisible(true);
			this.cloudLabels[1].setVisible(false);
			this.cloudLabels[2].setVisible(false);
		}
		if (gm.ENVrainPower == 2) {
			this.cloudLabels[0].setVisible(false);
			this.cloudLabels[1].setVisible(true);
			this.cloudLabels[2].setVisible(false);
		}
		if (gm.ENVrainPower == 3) {
			this.cloudLabels[0].setVisible(false);
			this.cloudLabels[1].setVisible(false);
			this.cloudLabels[2].setVisible(true);
		}
	}
	
	
	
	
	public static void simulation1(MyWindow window) throws InterruptedException, ControllerExecutorException {
		int i=0;
		while (i<10) {
			System.out.println("here");
			window.gm.ENVrainPower = 0;
			window.gm.ENVtemperature = 2;
			window.gm.ENVmode = 0;
			window.gm.ENVmanualModeUserFlow = 0; //Doesn't matter
			window.gm.ENVlowerBound = 10;
			window.gm.ENVupperBound = 12;
			window.gm.updateState();
			window.updatePicture();
			window.revalidate();
			Thread.sleep(1000);
			i++;
		}
	}
}