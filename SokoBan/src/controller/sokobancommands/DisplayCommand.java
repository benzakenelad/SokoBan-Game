package controller.sokobancommands;

/**
 * <p> updating the matching view </p>
 * @author Elad Ben Zaken
 *
 */
public class DisplayCommand extends SokobanCommand {
    
	
	public void execute() { // invoke the matching receiver
		view.Display(model.getLvl());
	}

}
