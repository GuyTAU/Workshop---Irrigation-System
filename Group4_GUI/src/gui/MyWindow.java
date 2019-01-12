package gui;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Button;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import misc.ControllerExecutor;
import misc.ControllerExecutorException;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.TextField;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Random;

public class MyWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	//Controller manager
	public ControllerManager gm = new ControllerManager();
	//Graphics
	private JPanel contentPane;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private final ButtonGroup buttonGroup_3 = new ButtonGroup();
	private JLabel alertLabel;
	private JLabel moistureLevelLabel;
	private JLabel timeLabel;
	private JLabel temperatureLabel;
	private JLabel modeLabel;
	private JLabel[] flowerIcon = new JLabel[4];
	private JLabel[] cloudIcon = new JLabel[8];
	private JLabel[] tapIcon = new JLabel[2];
	private JLabel[] dropIcon = new JLabel[14];
	private TextField upperBoundTextField; //Here user enters upper bound
	private TextField lowerBoundTextField; //Here user enters lower bound
	public Button but_sim1, but_sim2, but_sim3, but_sim4, but_stopSim, but_updateState; 
	private Color nightBgCol = new Color(56, 66, 105);
	private Color dayBgCol = new Color(255, 233, 224);
	private Color nightLabelCol = new Color(153, 162, 196);
	private Color dayLabelCol = new Color(204, 175, 175);
	private Color dayButtonCol = new Color(240,211,211);
	private Color nightButtonCol = new Color(61,73,117);
	private int activeFlower = 0;
	private int activeCloud = 0;
	private int activeDrop = 0;
	
	Font labelFont;
	Font updateStateFont;
	Font largeSimFont;
	Font smallSimFont;
	Font upperLowerFont;
	
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
		//configure fonts
		try {
			labelFont = Font.createFont(Font.TRUETYPE_FONT, MyWindow.class.getResourceAsStream("Assistant-SemiBold.ttf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateStateFont = labelFont.deriveFont(Font.PLAIN, 14f);
		largeSimFont = labelFont.deriveFont(Font.BOLD, 15f);
		smallSimFont = labelFont.deriveFont(Font.BOLD, 14f);
		upperLowerFont = labelFont.deriveFont(Font.PLAIN, 13f);
		labelFont = labelFont.deriveFont(Font.BOLD, 18f);
		//set frame icons
		ImageIcon iconLogoSmall = new ImageIcon("img/icon-small.png");
		ImageIcon iconLogoLarge = new ImageIcon("img/icon-large.png");
		ArrayList<Image> icons = new ArrayList<>();
		icons.add(iconLogoSmall.getImage());
		icons.add(iconLogoLarge.getImage());
		this.setIconImages(icons);
		
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
				ScheduledSettingsWindow scheduledWindow = new ScheduledSettingsWindow(gm,
																						selfRef.getLocation().x + selfRef.getWidth()/7,
																						selfRef.getLocation().y + selfRef.getHeight()/3);
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
		
		JCheckBoxMenuItem[] checkBoxMenuItem = new JCheckBoxMenuItem[7];
		for(int i = 0; i <= 6; i++) {
			checkBoxMenuItem[i] = new JCheckBoxMenuItem(i+"");
			buttonGroup_3.add(checkBoxMenuItem[i]);
			mnManualIrrigationFlow.add(checkBoxMenuItem[i]);
		}
		
		checkBoxMenuItem[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 0;
				irrigationFlowSet = true;
			}
		});
		checkBoxMenuItem[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 1;
				irrigationFlowSet = true;
			}
		});
		checkBoxMenuItem[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 2;
				irrigationFlowSet = true;
			}
		});
		checkBoxMenuItem[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 3;
				irrigationFlowSet = true;
			}
		});
		checkBoxMenuItem[4].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 4;
				irrigationFlowSet = true;
			}
		});
		checkBoxMenuItem[5].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 5;
				irrigationFlowSet = true;
			}
		});
		checkBoxMenuItem[6].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gm.ENVmanualModeUserFlow = 6;
				irrigationFlowSet = true;
			}
		});
				
		
		/*
		 * Update state button
		 */
		but_updateState = new Button("Update state");
		but_updateState.setFont(updateStateFont);
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
				if (upperBound - lowerBound < 2) {
					JOptionPane.showMessageDialog(contentPane, "Upper bound has to be at least 2 moisture units above lower bound", "Input is invalid", JOptionPane.ERROR_MESSAGE);
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
		
		panel = new JPanel();
		panel.setBounds(10, 11, 591, 553);
		panel.setBorder(null);
		panel.setLayout(null);
		
		
		/*
		 * Moisture level label.
		 */
		moistureLevelLabel = new JLabel("Moisture Level:");
		moistureLevelLabel.setFont(labelFont);
		moistureLevelLabel.setBounds(10, 10, 204, 47);
		panel.add(moistureLevelLabel);
		
		/*
		 * Time label.
		 */
		timeLabel = new JLabel("");
		timeLabel.setBounds(10, 50, 204, 47);
		panel.add(timeLabel);
		timeLabel.setFont(labelFont);
		timeLabel.setText("Time: " + String.valueOf(gm.ENVtime) + ":00");
		
		
		/*
		 * Temperature label.
		 */
		temperatureLabel = new JLabel("Temperature:");
		temperatureLabel.setFont(labelFont);
		temperatureLabel.setBounds(10, 90, 204, 47);
		panel.add(temperatureLabel);
		
		
		/*
		 * Mode label
		 */
		modeLabel = new JLabel("Mode:");
		modeLabel.setFont(labelFont);
		modeLabel.setBounds(10, 130, 204, 47);
		panel.add(modeLabel);
		
		
		

		
		/*
		 * Text field for lower bound
		 */
		upperBoundTextField = new TextField();
		upperBoundTextField.setFont(upperLowerFont);
		upperBoundTextField.setBounds(185, 218, 24, 19);
		panel.add(upperBoundTextField);

		
		
		/*
		 * Text field for upper bound
		 */
		lowerBoundTextField = new TextField();
		lowerBoundTextField.setFont(upperLowerFont);
		lowerBoundTextField.setBounds(185, 257, 24, 19);
		panel.add(lowerBoundTextField);

		
		
		/*
		 * Alert label
		 */
		alertLabel = new JLabel("");
		alertLabel.setForeground(Color.WHITE);
		alertLabel.setBounds(50, 350, 70, 79);
		alertLabel.setForeground(Color.WHITE);
		alertLabel.setIcon(new ImageIcon("img/caution-sign.png"));
		panel.add(alertLabel);
		this.alertLabel.setVisible(false);
		
		
		/*
		 * Moisture level label
		 */
		
		
		
		
		/*
		 * Simulation buttons
		 */
		but_sim1 = new Button("Drought Simulation");
		but_sim1.setFont(largeSimFont);
		but_sim1.setBounds(611, 240, 167, 50);
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
		but_sim2.setFont(largeSimFont);
		but_sim2.setBounds(611, 303, 167, 50);
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
		but_sim3.setFont(largeSimFont);
		but_sim3.setBounds(611, 366, 167, 50);
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
		
		but_sim4 = new Button("Normal Weather Sim");
		but_sim4.setFont(smallSimFont);
		but_sim4.setBounds(611, 429, 167, 50);
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
		but_stopSim.setFont(largeSimFont);
		but_stopSim.setBounds(611, 323, 167, 50);
		but_stopSim.setVisible(false);
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
		
		
		//flowers
		String deadOrAlive = "";
		String dayOrNight = "day";
		for(int i = 0; i < 4; i++) {
		flowerIcon[i] = new JLabel();
		flowerIcon[i].setBounds(187, 306, 278, 232);
		if(i == 2) dayOrNight = "night";
		deadOrAlive = i%2 == 0 ? "" : "dead-";
		flowerIcon[i].setIcon(new ImageIcon("img/"+dayOrNight+"/"+deadOrAlive+"flower.png"));
		panel.add(flowerIcon[i]);
		flowerIcon[i].setVisible(false);
		}
		
		
		//clouds
		dayOrNight = "day";
		int k;
		for(int j = 0; j < 2; j++) {
			if(j == 1) dayOrNight = "night";
			for(int i = 0; i < 4; i++) {
				k = j*4 + i;
				cloudIcon[k] = new JLabel();
				cloudIcon[k].setBounds(224, 31, 205, 319);
				cloudIcon[k].setIcon(new ImageIcon("img/"+dayOrNight+"/cloud"+i+".png"));
				panel.add(cloudIcon[k]);
				cloudIcon[k].setVisible(false);
			}
		}
		
		
		//taps
		tapIcon[0] = new JLabel();
		tapIcon[0].setBounds(482,367,37,43);
		tapIcon[0].setIcon(new ImageIcon("img/day/tap.png"));
		tapIcon[1] = new JLabel();
		tapIcon[1].setBounds(482,367,37,43);
		tapIcon[1].setIcon(new ImageIcon("img/night/tap.png"));
		panel.add(tapIcon[0]);
		panel.add(tapIcon[1]);
		tapIcon[0].setVisible(false);
		tapIcon[1].setVisible(false);
		
		//drops
		dayOrNight = "day";
		for(int j = 0; j < 2; j++) {
			if(j == 1) dayOrNight = "night";
			for(int i = 0; i < 7; i++) {
				k = j*7 + i;
				dropIcon[k] = new JLabel();
				dropIcon[k].setBounds(478, 415, 25, 39);
				dropIcon[k].setIcon(new ImageIcon("img/"+dayOrNight+"/drop"+i+".png"));
				panel.add(dropIcon[k]);
				dropIcon[k].setVisible(false);
			}
		}
		
		/*
		 * Constant pictures and texts
		 */
		
		//logo		
		JLabel groupLogo = new JLabel();
		groupLogo.setBounds(620, 11, 150, 150);
		groupLogo.setBackground(Color.WHITE);
		groupLogo.setIcon(new ImageIcon("img/logo.png"));
		contentPane.setLayout(null);
		
		
		//upper and lower bound image
		JLabel boundsImg = new JLabel("");
		boundsImg.setBounds(3, 210, 192, 91);
		boundsImg.setIcon(new ImageIcon("img/upper-lower-bound.png"));
		boundsImg.setForeground(Color.WHITE);
		panel.add(boundsImg);
		
		
		contentPane.add(panel);
		contentPane.add(groupLogo);
		contentPane.add(but_stopSim);
		contentPane.add(but_sim1);
		contentPane.add(but_sim2);
		contentPane.add(but_sim3);
		
		
		boolean isNight = (gm.ENVtime > 21 || gm.ENVtime < 5);
		if(isNight) {
			updateNightPicture(false);
			//set all flowers' visibility to false except the living night flower
		}
		else {
			updateDayPicture(false);
			//set all flowers' visibility to false except the dead night flower
		}
		
	}
	
	
	/**
	 * Updates the images, background, labels, buttons according to the values given to and by the controller, with the day time design. 
	 */
	void updateDayPicture() {
		updateDayPicture(true);
	}
	
	
	/**
	 * Updates the images, background, labels, buttons according to the values given to and by the controller, with the day time design. 
	 * @param takeValuesFromController If set to false, sets the background color (according to the time), visible tap to night tap and the color of the buttons and labels
	 */
	void updateDayPicture(boolean takeValuesFromController) {
		
		//update from night to day
		if(gm.ENVtime == 6 || !takeValuesFromController) {
			//update background color
			panel.setBackground(dayBgCol);
			contentPane.setBackground(dayBgCol);
			//update tap
			tapIcon[0].setVisible(true);
			tapIcon[1].setVisible(false);
			//update labels' colors
			moistureLevelLabel.setForeground(dayLabelCol);
			timeLabel.setForeground(dayLabelCol);
			temperatureLabel.setForeground(dayLabelCol);
			modeLabel.setForeground(dayLabelCol);
			//update buttons' colors
			but_sim1.setBackground(dayButtonCol);
			but_sim1.setForeground(dayLabelCol);
			but_sim2.setBackground(dayButtonCol);
			but_sim2.setForeground(dayLabelCol);
			but_sim3.setBackground(dayButtonCol);
			but_sim3.setForeground(dayLabelCol);
			but_sim4.setBackground(dayButtonCol);
			but_sim4.setForeground(dayLabelCol);
			but_stopSim.setBackground(dayButtonCol);
			but_stopSim.setForeground(dayLabelCol);
			
		}
		flowerIcon[activeFlower].setVisible(false);
		cloudIcon[activeCloud].setVisible(false);
		dropIcon[activeDrop].setVisible(false);
		activeFlower = (gm.SYSdeviationAlert ? 1 : 0);
		activeCloud = gm.ENVrainPower;
		activeDrop = gm.SYSirrigationFlow;
		flowerIcon[activeFlower].setVisible(true);
		cloudIcon[activeCloud].setVisible(true);
		dropIcon[activeDrop].setVisible(true);

	}
	
	/**
	 * Updates the images, background, labels, buttons according to the values given to and by the controller, with the night time design. 
	 */
	void updateNightPicture() {
		updateNightPicture(true);
	}
	
	
	/**
	 * Updates the images, background, labels, buttons according to the values given to and by the controller, with the night time design. 
	 * @param takeValuesFromController If set to false, sets the background color (according to the time), visible tap to night tap and the color of the buttons and labels
	 */
	void updateNightPicture(boolean takeValuesFromController) {
		//update from day to night
		if(gm.ENVtime == 22 || !takeValuesFromController) {
			//update background color
			panel.setBackground(nightBgCol);
			contentPane.setBackground(nightBgCol);
			//update tap
			tapIcon[0].setVisible(false);
			tapIcon[1].setVisible(true);
			//update labels' colors
			moistureLevelLabel.setForeground(nightLabelCol);
			timeLabel.setForeground(nightLabelCol);
			temperatureLabel.setForeground(nightLabelCol);
			modeLabel.setForeground(nightLabelCol);
			//update buttons' colors
			but_sim1.setBackground(nightButtonCol);
			but_sim1.setForeground(nightLabelCol);
			but_sim2.setBackground(nightButtonCol);
			but_sim2.setForeground(nightLabelCol);
			but_sim3.setBackground(nightButtonCol);
			but_sim3.setForeground(nightLabelCol);
			but_sim4.setBackground(nightButtonCol);
			but_sim4.setForeground(nightLabelCol);
			but_stopSim.setBackground(nightButtonCol);
			but_stopSim.setForeground(nightLabelCol);
		}
		flowerIcon[activeFlower].setVisible(false);
		cloudIcon[activeCloud].setVisible(false);
		dropIcon[activeDrop].setVisible(false);
		activeFlower = (gm.SYSdeviationAlert ? 3 : 2);
		activeCloud = gm.ENVrainPower+4;
		activeDrop = gm.SYSirrigationFlow+7;
		flowerIcon[activeFlower].setVisible(true);
		cloudIcon[activeCloud].setVisible(true);
		dropIcon[activeDrop].setVisible(true);

	}
	
	
	/**
	 * Changes the appearance of the frame's labels, buttons, images and background
	 * according to the values given by and to the controller.
	 */
	void updatePicture() {
		boolean isNight = (gm.ENVtime >= 22 || gm.ENVtime <= 5);
		if(isNight) {
			updateNightPicture();
		}
		else {
			updateDayPicture();
		}
		
		lowerBoundTextField.setText(gm.ENVlowerBound+"");
		upperBoundTextField.setText(gm.ENVupperBound+"");

		this.alertLabel.setVisible(gm.SYSdeviationAlert);
		this.upperBoundTextField.setEditable(false); //once decided = cannot be changed
		this.lowerBoundTextField.setEditable(false); //once decided = cannot be changed
		minutes++;
		if(minutes == 6) minutes = 0;
		this.timeLabel.setText(("Time: " + String.valueOf(gm.ENVtime) + ":"+minutes+"0"));
		this.moistureLevelLabel.setText(("Moisture Level: " + String.valueOf(gm.ENVmoistureLevel)));
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
	}
	
	
	/**
	 * Enables or disables all of the buttons and menus in MyWindow window, except for the 
	 * Stop Simulation button (which is set to the opposite value), according to the value
	 * of toEnable.
	 */
	private static void enableOrDisableButtons(MyWindow window, boolean toEnable) {
		JMenuBar menu = window.getJMenuBar();
		JMenu item;
		for(int i=0; i < menu.getMenuCount(); i++) {
			item = menu.getMenu(i);
			if(item != null) item.setEnabled(toEnable);
		}
		window.but_sim1.setVisible(toEnable);
		window.but_sim2.setVisible(toEnable);
		window.but_sim3.setVisible(toEnable);
		window.but_sim4.setVisible(toEnable);
		window.but_updateState.setEnabled(toEnable);
		window.but_stopSim.setVisible(!toEnable);

	}
	
	
	
	/**
	 * Disables all buttons in MyWindow except for the "Stop simulation" button, and sets 
	 * the color of the stop button to be red with a white label.
	 */
	public static void disableButtons(MyWindow window) {
		enableOrDisableButtons(window, false);
	}
	
	
	/**
	 * Enables all buttons in MyWindow except for the "Stop simulation" button, and sets
	 * the color of the stop button to the default.
	 */
	public static void reenableButtons(MyWindow window) {
		enableOrDisableButtons(window, true);
	}	
	
	
	/**
	 * Checks that the lower/upper bounds are set correctly (if not -- error message),
	 * and activates the simulation according to the action listener.
	 */
	public static void activateSimulation(final MyWindow window, ActionListener listener) {
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
		if (upperBound - lowerBound < 2) {
			JOptionPane.showMessageDialog(window.contentPane, "Upper bound has to be at least 2 moisture units above lower bound", "Input is invalid", JOptionPane.ERROR_MESSAGE);
			return;
		}
		window.gm.ENVlowerBound = Integer.parseInt(window.lowerBoundTextField.getText());
		window.lowerBoundSet = true;
		timer = new Timer(1000,listener);
		timer.setInitialDelay(0);
		timer.start();
	}
	
	
	/**
	 * Drought Simulation
	 */
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
		
		activateSimulation(window, simListener);
	}
	
	
	/**
	 * Rainy Simulation
	 */
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
		
		activateSimulation(window, simListener);
	}
	
	
	/**
	 * Mode Switch Simulation
	 */
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
		
		activateSimulation(window, simListener);
	}
	
	
	/**
	 * Normal Weather Simulation
	 */
	public static void simulation4(final MyWindow window) throws InterruptedException, ControllerExecutorException {
		
		ActionListener simListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableButtons(window);
				window.gm.ENVmode = 0;
				//arrays holding the attributes of each hour
				int [] temprature = {1,0,1,1,0,2,2,1,2,2,0,2,2,1,0,2,2,1,2,0,0,1,0,0};
				int [] rainPower  = {0,0,0,0,0,0,2,0,0,1,0,0,0,0,0,0,0,1,0,0,0,1,0,0};
				//set temperature and rain power according to the arrays
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
		
		activateSimulation(window, simListener);
	}
	

}
