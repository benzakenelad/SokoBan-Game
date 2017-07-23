package controller.sokobancommands;

public class HintCommand extends SokobanCommand {

	@Override
	public void execute() {
		model.hint();
	}

}
