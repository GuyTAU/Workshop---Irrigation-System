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

import com.sun.xml.internal.ws.util.StringUtils;

import misc.ControllerExecutor;
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
import javax.swing.Timer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
	private TextField upperBoundTextField; //Here user enters upper bound
	private TextField lowerBoundTextField; //Here user enters lower bound
	public Button but_sim1, but_sim2, but_sim3, but_sim4, but_stopSim, but_updateState; 
	
	//flags for set values
	private boolean tempSet = false;
	private boolean modeSet = false;
	private boolean irrigationFlowSet = false;
	private boolean rainSet = false;
	boolean lowerBoundSet = false;
	boolean upperBoundSet = false;

	private static Timer timer;
	private int minutes;
	
	
	

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
				ScheduledSettingsWindow scheduledWindow = new ScheduledSettingsWindow(gm,
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
		JMenu mnManualIrrigationFlow = new JMenu("Manual/Scheduled Irrigation Flow");
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
		but_updateState = new Button("Update state");
		but_updateState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//take value of lower bound and upper bound
				int lowerBound, upperBound;
				try {
					upperBound = Integer.parseInt(upperBoundTextField.getText());
					lowerBound = Integer.parseInt(lowerBoundTextField.getText());
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(contentPane, "Upper bound and lower bound must be integers.", "Invalid values", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(upperBound <= lowerBound) {
					JOptionPane.showMessageDialog(contentPane, "Upper bound must be higher than the lower bound.", "Invalid values", JOptionPane.ERROR_MESSAGE);
					return;
				}
				gm.ENVupperBound = Integer.parseInt(upperBoundTextField.getText());
				if (upperBound > 15 || upperBound < 0) {
					JOptionPane.showMessageDialog(contentPane, "Upper bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
				gm.ENVupperBound = upperBound;
				upperBoundSet = true;
				if (lowerBound > 15 || lowerBound < 0) {
					JOptionPane.showMessageDialog(contentPane, "Lower bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}
				gm.ENVlowerBound = Integer.parseInt(lowerBoundTextField.getText());
				lowerBoundSet = true;
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
					e1.printStackTrace();
				}
				updatePicture();
			}
		});
		menuBar.add(but_updateState);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 591, 518);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setLayout(null);
		
		
		/*
		 * Time label.
		 */
		timeLabel = new JLabel("");
		timeLabel.setBounds(10, 58, 204, 47);
		panel.add(timeLabel);
		timeLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
		timeLabel.setText("Time: " + String.valueOf(gm.ENVtime) + ":00");
		
		
		/*
		 * Temperature label.
		 */
		temperatureLabel = new JLabel("Temperature:");
		temperatureLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
		temperatureLabel.setBounds(10, 107, 204, 47);
		panel.add(temperatureLabel);
		
		
		/*
		 * Mode label
		 */
		modeLabel = new JLabel("Mode:");
		modeLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
		modeLabel.setBounds(10, 165, 204, 47);
		panel.add(modeLabel);
		
		
		
		/*
		 * Clouds labels
		 */
		cloudLabels[0] = new JLabel("");
		cloudLabels[0].setBounds(224, 31, 250, 159);
		cloudLabels[0].setForeground(Color.WHITE);
		cloudLabels[0].setIcon(new ImageIcon("img/cloud 1.png"));
		panel.add(cloudLabels[0]);
		
		cloudLabels[1] = new JLabel("");
		cloudLabels[1].setBounds(224, 31, 250, 159);
		cloudLabels[1].setForeground(Color.WHITE);
		cloudLabels[1].setIcon(new ImageIcon("img/cloud 2.png"));
		panel.add(cloudLabels[1]);
		
		cloudLabels[2] = new JLabel("");
		cloudLabels[2].setBounds(224, 31, 250, 159);
		cloudLabels[2].setForeground(Color.WHITE);
		cloudLabels[2].setIcon(new ImageIcon("img/cloud 3.png"));
		panel.add(cloudLabels[2]);
		
		

		
		/*
		 * Text field for lower bound
		 */
		upperBoundTextField = new TextField();
		upperBoundTextField.setBounds(157, 256, 24, 19);
		panel.add(upperBoundTextField);

		
		
		/*
		 * Text field for upper bound
		 */
		lowerBoundTextField = new TextField();
		lowerBoundTextField.setBounds(157, 295, 24, 19);
		panel.add(lowerBoundTextField);

		
		
		/*
		 * Alert label
		 */
		alertLabel = new JLabel("");
		alertLabel.setForeground(Color.WHITE);
		alertLabel.setBounds(464, 461, 38, 46);
		alertLabel.setForeground(Color.WHITE);
		alertLabel.setIcon(new ImageIcon("img/alert.png"));
		panel.add(alertLabel);
		
		
		/*
		 * Moisture level label
		 */
		moistureLevelLabel = new JLabel("Moisture Level:");
		moistureLevelLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 22));
		moistureLevelLabel.setBounds(10, 11, 204, 47);
		panel.add(moistureLevelLabel);
		
		
		
		
		/*
		 * Simulation buttons
		 */
		but_sim1 = new Button("Drought Simulation");
		but_sim1.setBounds(611, 270, 167, 50);
		but_sim1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					simulation1(selfRef);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ControllerExecutorException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		but_sim2 = new Button("Rainy Simulation");
		but_sim2.setBounds(611, 333, 167, 50);
		but_sim2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					simulation2(selfRef);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ControllerExecutorException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		but_sim3 = new Button("Mode Switch Sim");
		but_sim3.setBounds(611, 396, 167, 50);
		but_sim3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					simulation3(selfRef);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ControllerExecutorException e1) {
					e1.printStackTrace();
				}
			}
		});
		Button button = new Button("");
		
		
		but_sim4 = new Button("Normal Weather Sim");
		but_sim4.setBounds(611, 459, 167, 50);
		contentPane.add(but_sim4);
		but_sim4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					simulation4(selfRef);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (ControllerExecutorException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		but_stopSim = new Button("Stop Simulation");
		but_stopSim.setBounds(611, 200, 167, 50);
		but_stopSim.setEnabled(false);
		but_stopSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(timer != null) {
				timer.stop();
				reenableButtons(selfRef);
				selfRef.gm.ctrlExec = new ControllerExecutor();
				selfRef.upperBoundTextField.setEditable(true);
				selfRef.lowerBoundTextField.setEditable(true);

				}
			}
		});
		
		
		
		/*
		 * Constant pictures and texts.
		 */
		JLabel treeIcon = new JLabel("");
		treeIcon.setBounds(187, 213, 256, 294);
		treeIcon.setForeground(Color.WHITE);
		treeIcon.setIcon(new ImageIcon("img/tree.jpg"));
		panel.add(treeIcon);

		
		
		JLabel groupLogo = new JLabel("");
		groupLogo.setBounds(626, 11, 140, 178);
		groupLogo.setBackground(Color.WHITE);
		groupLogo.setIcon(new ImageIcon("img/irrigation logo.jpg"));
		contentPane.setLayout(null);
		
		
		JLabel tapIcon = new JLabel("");
		tapIcon.setBounds(453, 367, 81, 54);
		tapIcon.setForeground(Color.WHITE);
		tapIcon.setIcon(new ImageIcon("img/tap.jpg"));
		panel.add(tapIcon);
		
		
		lowerBoundField = new TextField();
		lowerBoundField.setBackground(Color.RED);
		lowerBoundField.setEditable(false);
		lowerBoundField.setFont(new Font("Dialog", Font.PLAIN, 14));
		lowerBoundField.setText("Required Lower Bound");
		lowerBoundField.setBounds(10, 295, 143, 19);
		panel.add(lowerBoundField);
		
		
		upperBoundField = new TextField();
		upperBoundField.setBackground(Color.CYAN);
		upperBoundField.setText("Required Upper Bound");
		upperBoundField.setFont(new Font("Dialog", Font.PLAIN, 14));
		upperBoundField.setEditable(false);
		upperBoundField.setBounds(10, 256, 143, 19);
		panel.add(upperBoundField);
		
		
		JLabel lblOutputs = new JLabel("Outputs:");
		lblOutputs.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		lblOutputs.setBounds(464, 292, 88, 47);
		panel.add(lblOutputs);
		
		irrigationFlowLabel = new JLabel("");
		irrigationFlowLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 25));
		irrigationFlowLabel.setBounds(543, 367, 38, 47);
		panel.add(irrigationFlowLabel);
		contentPane.add(panel);
		contentPane.add(groupLogo);
		contentPane.add(but_stopSim);
		contentPane.add(but_sim1);
		contentPane.add(but_sim2);
		contentPane.add(but_sim3);
		

		
		
	}
	
	void updatePicture() {
		lowerBoundTextField.setText(gm.ENVlowerBound+"");
		upperBoundTextField.setText(gm.ENVupperBound+"");

		this.alertLabel.setVisible(gm.SYSdeviationAlert);
		this.upperBoundTextField.setEditable(false); //once decided = cannot be changed
		this.lowerBoundTextField.setEditable(false); //once decided = cannot be changed
		minutes++;
		if(minutes == 6) minutes = 0;
		this.timeLabel.setText(("Time: " + String.valueOf(gm.ENVtime) + ":"+minutes+"0"));
		this.moistureLevelLabel.setText(("Moisture Level: " + String.valueOf(gm.ENVmoistureLevel)));
		this.irrigationFlowLabel.setText((String.valueOf(gm.SYSirrigationFlow)));
		//Update Mode label
		if (gm.ENVmode == 0) {
			this.modeLabel.setText("Mode: Automatic");
		}
		else if (gm.ENVmode == 1) {
			this.modeLabel.setText("Mode: Manual");
		}
		else if (gm.ENVmode == 2) {
			this.modeLabel.setText("Mode: Scheduled");
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
	
	
	/*
	 * Disables all buttons in MyWindow except for the "Stop simulation" button.
	 */
	public static void disableButtons(MyWindow window) {
		JMenuBar menu = window.getJMenuBar();
		JMenu item;
		for(int i=0; i < menu.getMenuCount(); i++) {
			item = menu.getMenu(i);
			if(item != null) item.setEnabled(false);
		}
		window.but_sim1.setEnabled(false);
		window.but_sim2.setEnabled(false);
		window.but_sim3.setEnabled(false);
		window.but_sim4.setEnabled(false);
		window.but_updateState.setEnabled(false);
		window.but_stopSim.setEnabled(true);
	}
	
	
	/*
	 * Enables all buttons in MyWindow except for the "Stop simulation" button.
	 */
	public static void reenableButtons(MyWindow window) {
		JMenuBar menu = window.getJMenuBar();
		JMenu item;
		for(int i=0; i < menu.getMenuCount(); i++) {
			item = menu.getMenu(i);
			if(item != null) item.setEnabled(true);
		}
		window.but_sim1.setEnabled(true);
		window.but_sim2.setEnabled(true);
		window.but_sim3.setEnabled(true);
		window.but_sim4.setEnabled(true);
		window.but_updateState.setEnabled(true);
		window.but_stopSim.setEnabled(false);
	}	

	public static void simulation1(final MyWindow window) throws InterruptedException, ControllerExecutorException {
		
		ActionListener simListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableButtons(window);
				window.gm.ENVrainPower = 0;
				if ((window.gm.ENVtime >= 21 & window.gm.ENVtime <= 23) | (window.gm.ENVtime >= 0 & window.gm.ENVtime <= 4)) {
					window.gm.ENVtemperature = 0;
				}
				else {
					window.gm.ENVtemperature = 2;
				}
				window.gm.ENVmode = 0;
				window.gm.ENVmanualModeUserFlow = 0; //Doesn't matter
				//take value of lower bound and upper bound
				
				try {
					window.gm.updateState(true);
				} catch (ControllerExecutorException e1) {
					e1.printStackTrace();
				}
				window.updatePicture();
				window.revalidate();
			}
		};
		int lowerBound, upperBound;
		try {
			upperBound = Integer.parseInt(window.upperBoundTextField.getText());
			lowerBound = Integer.parseInt(window.lowerBoundTextField.getText());
		} catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound and lower bound must be integers.", "Invalid values", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(upperBound <= lowerBound) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound must be higher than the lower bound.", "Invalid values", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVupperBound = Integer.parseInt(window.upperBoundTextField.getText());
		if (upperBound > 15 || upperBound < 0) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVupperBound = upperBound;
		window.upperBoundSet = true;
		if (lowerBound > 15 || lowerBound < 0) {
			JOptionPane.showMessageDialog(window.contentPane, "Lower bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVlowerBound = Integer.parseInt(window.lowerBoundTextField.getText());
		window.lowerBoundSet = true;
		timer = new Timer(1000,simListener);
		timer.setInitialDelay(0);
		timer.start();
	}
	
	
	
	public static void simulation2(final MyWindow window) throws InterruptedException, ControllerExecutorException {
		
		ActionListener simListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableButtons(window);
				Random rand = new Random();
				int randomValue = rand.nextInt(4)+1;
				window.gm.ENVrainPower = Math.min(randomValue, 3);
				if ((window.gm.ENVtime >= 21 & window.gm.ENVtime <= 23) | (window.gm.ENVtime >= 0 & window.gm.ENVtime <= 4)) {
					window.gm.ENVtemperature = 0;
				}
				else {
					randomValue = rand.nextInt(3);
					window.gm.ENVtemperature = randomValue;
				}
				window.gm.ENVmode = 0;
				window.gm.ENVmanualModeUserFlow = 0; //Doesn't matter
				//take value of lower bound and upper bound
				
				try {
					window.gm.updateState();
				} catch (ControllerExecutorException e1) {
					e1.printStackTrace();
				}
				window.updatePicture();
				window.revalidate();
			}
		};
		int lowerBound, upperBound;
		try {
			upperBound = Integer.parseInt(window.upperBoundTextField.getText());
			lowerBound = Integer.parseInt(window.lowerBoundTextField.getText());
		} catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound and lower bound must be integers.", "Invalid values", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(upperBound <= lowerBound) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound must be higher than the lower bound.", "Invalid values", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVupperBound = Integer.parseInt(window.upperBoundTextField.getText());
		if (upperBound > 15 || upperBound < 0) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVupperBound = upperBound;
		window.upperBoundSet = true;
		if (lowerBound > 15 || lowerBound < 0) {
			JOptionPane.showMessageDialog(window.contentPane, "Lower bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVlowerBound = Integer.parseInt(window.lowerBoundTextField.getText());
		window.lowerBoundSet = true;
		timer = new Timer(1000,simListener);
		timer.setInitialDelay(0);
		timer.start();
	}
	
	
	
	public static void simulation3(final MyWindow window) throws InterruptedException, ControllerExecutorException {
		
		ActionListener simListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableButtons(window);
				Random rand = new Random();
				int randomValue = rand.nextInt(3);
				window.gm.ENVrainPower = 0;
				if ((window.gm.ENVtime >= 21 & window.gm.ENVtime <= 23) | (window.gm.ENVtime >= 0 & window.gm.ENVtime <= 4)) {
					window.gm.ENVtemperature = 0;
				}
				else {
					window.gm.ENVtemperature = randomValue;
				}
				if (window.gm.ENVtime % 3 == 0) { //switch mode every hour
					window.gm.ENVmode = 0;
				}
				else if (window.gm.ENVtime % 3 == 1) {
					window.gm.ENVmode = 1;
				}
				else {
					window.gm.ENVmode = 2;
				}
				//take value of lower bound and upper bound
				
				try {
					window.gm.updateState();
				} catch (ControllerExecutorException e1) {
					e1.printStackTrace();
				}
				window.updatePicture();
				window.revalidate();
			}
		};
		int lowerBound, upperBound;
		try {
			upperBound = Integer.parseInt(window.upperBoundTextField.getText());
			lowerBound = Integer.parseInt(window.lowerBoundTextField.getText());
		} catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound and lower bound must be integers.", "Invalid values", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(upperBound <= lowerBound) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound must be higher than the lower bound.", "Invalid values", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVupperBound = Integer.parseInt(window.upperBoundTextField.getText());
		if (upperBound > 15 || upperBound < 0) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVupperBound = upperBound;
		window.upperBoundSet = true;
		if (lowerBound > 15 || lowerBound < 0) {
			JOptionPane.showMessageDialog(window.contentPane, "Lower bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVlowerBound = Integer.parseInt(window.lowerBoundTextField.getText());
		window.lowerBoundSet = true;
		timer = new Timer(1000,simListener);
		timer.setInitialDelay(0);
		timer.start();
	}
	
	
	
	public static void simulation4(final MyWindow window) throws InterruptedException, ControllerExecutorException {
		
		ActionListener simListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableButtons(window);
				window.gm.ENVmode = 0;
				//arrays holding the attributed=s of each hour
				int [] temprature = {1,0,1,1,0,2,2,1,2,2,0,2,2,1,0,2,2,1,2,0,0,1,0,0};
				int [] rainPower =  {0,0,0,0,0,0,2,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0};
				window.gm.ENVtemperature = temprature[window.gm.ENVtime];
				window.gm.ENVrainPower = rainPower[window.gm.ENVtime];
				//take value of lower bound and upper bound
				try {
					window.gm.updateState();
				} catch (ControllerExecutorException e1) {
					e1.printStackTrace();
				}
				window.updatePicture();
				window.revalidate();
			}
		};
		int lowerBound, upperBound;
		try {
			upperBound = Integer.parseInt(window.upperBoundTextField.getText());
			lowerBound = Integer.parseInt(window.lowerBoundTextField.getText());
		} catch(NumberFormatException nfe) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound and lower bound must be integers.", "Invalid values", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(upperBound <= lowerBound) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound must be higher than the lower bound.", "Invalid values", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVupperBound = Integer.parseInt(window.upperBoundTextField.getText());
		if (upperBound > 15 || upperBound < 0) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVupperBound = upperBound;
		window.upperBoundSet = true;
		if (lowerBound > 15 || lowerBound < 0) {
			JOptionPane.showMessageDialog(window.contentPane, "Lower bound value must be from 0 to 15", "Input is invalid", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVlowerBound = Integer.parseInt(window.lowerBoundTextField.getText());
		window.lowerBoundSet = true;
		timer = new Timer(1000,simListener);
		timer.setInitialDelay(0);
		timer.start();
	}
	
	

}
