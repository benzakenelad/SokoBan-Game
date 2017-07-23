package controller;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import controller.general.Controller;
import controller.sokobancommands.Command;
import controller.sokobancommands.DisplayCommand;
import controller.sokobancommands.HintCommand;
import controller.sokobancommands.LoadLevelCommand;
import controller.sokobancommands.MoveCommand;
import controller.sokobancommands.QuickSolveCommand;
import controller.sokobancommands.SafeExitCommand;
import controller.sokobancommands.SaveLevelCommand;
import controller.sokobancommands.SokobanCommand;
import controller.sokobancommands.SolveCommand;
import model.Model;
import view.GUI.View;

/**
 * <p> SokoBan controller (MVC) </p>
 * @author Elad Ben Zaken
 *
 */
public class SokobanController implements Observer {
	
	// Data members
	private Model model = null;
	private View view = null;
	private Controller controller = null;
	
	// mapped commands generator
	private HashMap<String, SokobanCommand> sokoCommandHashMap = null;
	
	
	// MyController C'TOR
	public SokobanController(Model model, View view, String note) {
		this.model = model;
		this.view = view;
		
		this.controller = new Controller(); // commands execution controller
		controller.start(); // start to execute commands
		
		this.initializeSokoBanCommandsGenerator();
		
	}

	@Override 
	public void update(Observable arg0, Object arg1) // update when ever the observable objects are changed
	{
		if(arg1 == null)
			return;
		
		if(arg1 instanceof String){
			String s = (String)arg1;
			s = s.toLowerCase();
			String note[] = s.split(" ");
			Command command = this.generateACommand(note);
			
			controller.insertCommand(command);
		}		
	}	

	
	private Command generateACommand(String[] note){
		if(note == null)
			return null;
		
		SokobanCommand command = sokoCommandHashMap.get(note[0]);
		
		if(command != null)
		{
			if(note.length >= 2)	
				command.setOrder(note[1]);
			command.setModel(this.model);
			command.setView(this.view);
			return command;
		}
		
		return null;
	}

	public void exit() // stop the commands execution controller thread, view resources, and server resources
	{
		view.safeExit();
		controller.stop();
		this.model.close();
		
	}
	
	private void initializeSokoBanCommandsGenerator()
	{
		sokoCommandHashMap = new HashMap<String, SokobanCommand>();
		sokoCommandHashMap.put("move", new MoveCommand());
		sokoCommandHashMap.put("load", new LoadLevelCommand());
		sokoCommandHashMap.put("save", new SaveLevelCommand());
		sokoCommandHashMap.put("exit", new SafeExitCommand(this));
		sokoCommandHashMap.put("display", new DisplayCommand());
		sokoCommandHashMap.put("solve", new SolveCommand());
		sokoCommandHashMap.put("quicksolve", new QuickSolveCommand());
		sokoCommandHashMap.put("hint", new HintCommand());
	}	
	
}
