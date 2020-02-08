package edu.uoc.baluocesto.view.cmd.menu;

import java.io.IOException;

import edu.uoc.baluocesto.controller.Manager;
import edu.uoc.baluocesto.view.cmd.CmdApp;

public class LoadGameMenu implements Menu{

	private final String MSG_ERR_FILE_NOT_FOUND = "[ERROR] The file 'manager.dat' does not exist!!";
	
	public LoadGameMenu(){
		beginMenuSelectionLoop();
	}
	
	@Override
	public void beginMenuSelectionLoop() {
		CmdApp.setManager(new Manager());
		displayMenuOptions();
	}

	@Override
	public void displayMenuOptions() {
		try{
			CmdApp.getManager().loadGame();
			new ManagerMenu();			
		}catch(IOException e) {
			System.err.println(MSG_ERR_FILE_NOT_FOUND);
		}	
	}
}
