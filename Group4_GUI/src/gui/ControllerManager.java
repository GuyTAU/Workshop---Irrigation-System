package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	boolean SYSdeviationAlert;

	/*** Used for parsing controller output ***/
	Map<String, String> curValues;
	/*** Controller ***/
	private int stateNum = 0; //our counter for counting states.
	ControllerExecutor ctrlExec;

	
	public ControllerManager() {
		ENVtime = (int) Math.floor(Math.random() * 24);
		ENVmoistureLevel = (int) Math.floor(Math.random() * 15);
		//loadController();
		ctrlExec = new ControllerExecutor();
		curValues = new HashMap<String, String>();
	}
	
	/**
	 * @throws ControllerExecutorException 
	 * 
	 */
	public void updateState() throws ControllerExecutorException {
		
		//Need to get a random value for moisture.
		ENVmoistureLevel = 3;
		//Set controller values
		ctrlExec.setInputValue("rainPower", "" + ENVrainPower);
		ctrlExec.setInputValue("hour", "" + ENVtime);
		ctrlExec.setInputValue("temperature", "" + ENVtemperature);
		ctrlExec.setInputValue("mode", "" + ENVmode);
		ctrlExec.setInputValue("manualModeUserFlow", "" + ENVmanualModeUserFlow);
		ctrlExec.setInputValue("lowerBound", "" + ENVlowerBound);
		ctrlExec.setInputValue("upperBound", "" + ENVupperBound);
		//ctrlExec.setInputValue("moistureLevel", "" + ENVmoistureLevel);
		
		
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
		
		
		curValues = ctrlExec.getCurValues("rainPower", "hour", "temperature", "mode", "manualModeUserFlow", "manualModeUserFlow",
				"moistureLevel", "lowerBound", "upperBound", "irrigationFlow", "deviationAlert");
		//Next input values
		ENVrainPower = Integer.parseInt(curValues.get("rainPower"));
		ENVtime = Integer.parseInt(curValues.get("hour"));
		ENVtemperature = Integer.parseInt(curValues.get("temperature"));
		ENVmode = Integer.parseInt(curValues.get("mode"));
		ENVmanualModeUserFlow = Integer.parseInt(curValues.get("manualModeUserFlow"));
		ENVmoistureLevel = Integer.parseInt(curValues.get("moistureLevel"));
		ENVlowerBound = Integer.parseInt(curValues.get("lowerBound"));
		ENVupperBound = Integer.parseInt(curValues.get("upperBound"));
		//Next output values
		SYSirrigationFlow = Integer.parseInt(curValues.get("irrigationFlow"));
		SYSdeviationAlert = Boolean.parseBoolean(curValues.get("deviationAlert"));
		
	}
		

}