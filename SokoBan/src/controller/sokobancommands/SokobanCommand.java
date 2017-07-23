package controller.sokobancommands;

import model.Model;
import view.GUI.View;

/**
 * <p> SokoBan Command - for execution initialize model, view and order, using the setModel, setView and setOrder methods </p>
 * @author Elad Ben Zaken
 *
 */
public abstract class SokobanCommand implements Command{
	
	// Data members
	protected String order = null;
	protected Model model = null;
	protected View view = null;
	
	// getters and setters
	public void setModel(Model model) {
		this.model = model;
	}

	public void setView(View view) {
		this.view = view;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	
}
