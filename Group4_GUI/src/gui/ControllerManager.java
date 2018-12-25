package gui;

import java.util.ArrayList;
import java.util.List;

import net.sf.javabdd.BDD;
import net.sf.javabdd.BDD.BDDIterator;
import tau.smlab.syntech.gamemodel.PlayerModule;
import tau.smlab.syntech.games.util.SaveLoadWithDomains;
import tau.smlab.syntech.jtlv.BDDPackage;
import tau.smlab.syntech.jtlv.Env;
import misc.ControllerExecutor;
import misc.ControllerExecutorException;


public class ControllerManager {
	

	/*** ENV vars ***/
	int ENVrainPower; // 0-none, 1-light, 2-moderate, 3-heavy
	int ENVtime;
	int ENVtemperature; // 0-cold, 1-medium, 2-hot
	int ENVmode; //0-auto, 1-manual
	int ENVmanualModeUserFlow;
	int ENVmoistureLevel;
	int ENVlowerBound;
	int ENVupperBound;
	int[] ENVschedule = new int[24];

	/*** SYS vars ***/
	int SYSirrigationFlow;
	int SYStemperatureDecEffect;
	boolean SYSdeviationAlert;

	/*** Controller ***/
	private BDD currentState;
	private PlayerModule ctrl;
	private boolean initialState = true;
	private int stateNum = 0; //our counter for counting states.
	ControllerExecutor ctrlExec;

	
	public ControllerManager() {
		ENVtime = (int) Math.floor(Math.random() * 24);
		ENVmoistureLevel = (int) Math.floor(Math.random() * 15);
		//loadController();
		ctrlExec = new ControllerExecutor();
	}
	
	/**
	 * 
	 */
	public void updateState() {
		
		ENVmoistureLevel = 3;
		//Set controller values
		try {
			ctrlExec.setInputValue("rainPower", "" + ENVrainPower);
			ctrlExec.setInputValue("hour", "" + ENVtime);
			ctrlExec.setInputValue("temperature", "" + ENVtemperature);
			ctrlExec.setInputValue("mode", "" + ENVmode);
			ctrlExec.setInputValue("manualModeUserFlow", "" + ENVmanualModeUserFlow);
			ctrlExec.setInputValue("moistureLevel", "" + ENVmoistureLevel);
			ctrlExec.setInputValue("lowerBound", "" + ENVlowerBound);
			ctrlExec.setInputValue("upperBound", "" + ENVupperBound);
		} catch (ControllerExecutorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//etc.
		
		
		//Try to update the state of the controller, provided the above user inputs
		//The update step randomly picks a valid next state
		try {
			ctrlExec.updateState(true);
		} catch (ControllerExecutorException ce) {
			//The above inputs violate the assumptions
			System.err.println(ce.getMessage());
		}
		
		if(ctrlExec.reachedEnvDeadlock()) {
			//An environment deadlock has been reached
			//The controller execution has terminated
			return;
		}
	}
		
		
		
		//TODO: get values from controller 
		//for example:
		
//		curValues = ctrlExec.getCurValues(BANANA_0, BANANA_1, MONKEY_0, MONKEY_1);
//		//Next input values
//		banana[0] = Integer.parseInt(curValues.get(BANANA_0));
//		banana[1] = Integer.parseInt(curValues.get(BANANA_1));
//		
//		//Next output values
//		monkey[0] = Integer.parseInt(curValues.get(MONKEY_0));
//		monkey[1] = Integer.parseInt(curValues.get(MONKEY_1));
		
		
		
		
		
		
		
//		for (String s : stateVals) {
//			String[] val = s.split(":");
//			if ("rainPower".equals(val[0])) {
//				ENVrainPower = Integer.parseInt(val[1]);
//				System.out.print("rainPower: " + ENVrainPower + "," );
//			}
//			if ("hour".equals(val[0])) {
//				ENVtime = Integer.parseInt(val[1]);
//				System.out.print("time: " + ENVtime + "," );
//			}
//			if ("temperature".equals(val[0])) {
//				ENVtemperature = Integer.parseInt(val[1]);
//				System.out.print("temp: " + ENVtemperature + "," );
//			}
//			if ("mode".equals(val[0])) {
//				ENVmode = Integer.parseInt(val[1]);
//				System.out.print("mode: " + ENVmode + "," );
//			}
//			if ("manualModeUserFlow".equals(val[0])) {
//				ENVmanualModeUserFlow = Integer.parseInt(val[1]);
//				System.out.print("manualModeUserFlow: " + ENVmanualModeUserFlow + "," );
//			}
//			if ("moistureLevel".equals(val[0])) {
//				ENVmoistureLevel = Integer.parseInt(val[1]);
//				System.out.print("moistureLevel: " + ENVmoistureLevel + "," );
//			}
//			if ("lowerBound".equals(val[0])) {
//				ENVlowerBound = Integer.parseInt(val[1]);
//				System.out.print("lowerBound: " + ENVlowerBound + "," );
//			}
//			if ("upperBound".equals(val[0])) {
//				ENVupperBound = Integer.parseInt(val[1]);
//				System.out.print("upperBound: " + ENVupperBound + "," );
//			}
//			if ("irrigationFlow".equals(val[0])) {
//				SYSirrigationFlow = Integer.parseInt(val[1]);
//				System.out.print("irrigationFlow: " + SYSirrigationFlow + "," );
//			}
//			if ("deviationAlert".equals(val[0])) {
//				SYSdeviationAlert = Boolean.parseBoolean(val[1]);
//				System.out.print("deviationAlert: " + SYSdeviationAlert + "," );
//			}
//			if ("temperatureDecEffect".equals(val[0])) {
//				SYStemperatureDecEffect = Integer.parseInt(val[1]);
//				System.out.print("temperatureDecEffect: " + SYStemperatureDecEffect + "," );
//			}
//		}
//	}

	private void loadController() {
		BDDPackage.setCurrPackage(BDDPackage.JTLV);

		try {
			SaveLoadWithDomains.loadStructureAndDomains("out/vars.doms");
			BDD init = Env.loadBDD("out/controller.init.bdd");
			init = init.exist(Env.globalPrimeVars());
			BDD trans = Env.loadBDD("out/controller.trans.bdd");

			ctrl = new PlayerModule();
			ctrl.conjunctTrans(trans);
			ctrl.conjunctInitial(init);
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentState = ctrl.initial().id();
		initialState = true;
	}

}