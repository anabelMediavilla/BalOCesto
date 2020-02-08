package edu.uoc.baluocesto.view.cmd;

import edu.uoc.baluocesto.controller.Database;
import edu.uoc.baluocesto.controller.Manager;
import edu.uoc.baluocesto.view.cmd.menu.MainMenu;

/**
* CmdApp function of the BalUOCesto Software JAVA Practice
* 
* @author David García Solórzano
* @version 1.0
*/
public class CmdApp {

	private static Manager manager = null;
	private static Database database;
		
	/**
	 * Default constructor. It creates an instance of Game/Controller.
	 */
	public CmdApp() {
		// not necessary if extending Object.
		super();
		database = new Database("./files");
		new MainMenu();
	}
			
	public static void main(String[] args) {
		new CmdApp();
	}	
	
	public static Manager getManager() {
		return manager;
	}
	
	public static void setManager(Manager manager) {
		CmdApp.manager = manager;
	}
	
	public static Database getDatabase() {
		return database;
	}
}
