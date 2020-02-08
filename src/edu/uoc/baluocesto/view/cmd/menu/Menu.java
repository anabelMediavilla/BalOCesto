package edu.uoc.baluocesto.view.cmd.menu;

import java.util.Scanner;

public interface Menu {
	
	public final String MSG_ERR_TYPE_NUMBER = "[ERROR] You must type a number!!!";
	public final String MSG_ERR_INVALID_OPTION = "[ERROR] Your option is incorrect!! Try again!!";
	public final String MSG_CONTINUE = "Press any character and enter to continue...";
	public final String MSG_CHOOSE_OPTION = "Please choose one of the following options:";
	public final String MSG_DATA_OK = "Are all data OK? (Y/N): ";
	
	public final Scanner in = new Scanner(System.in);
	
	public void beginMenuSelectionLoop();

    public void displayMenuOptions();

}
