package edu.uoc.baluocesto.view.cmd.menu;

import java.util.InputMismatchException;

public class MainMenu implements Menu{
	private final String MSG_THANKS = "Thanks for playing!";
	
	public MainMenu() {
		beginMenuSelectionLoop();
	}
			
	@Override
	public void beginMenuSelectionLoop() {
		boolean quit = false;
		do{
			displayMenuOptions();
			try{
				switch(in.nextInt()) {			
					case 0://Exit Game
						System.out.println(MSG_THANKS);
						quit = true;
						break;
					case 1: // [1] Start New Game						
						new NewGameMenu();
						break;
					case 2: // [2] Continue Saved Game
						new LoadGameMenu();
						break;
					case 3: // [3] Display database
						new DatabaseMenu();
						break;
					default:
						System.err.println(MSG_ERR_INVALID_OPTION);
					}			
				
			}catch(InputMismatchException e) {
				System.err.println(MSG_ERR_TYPE_NUMBER);
				in.next();// To avoid infinite loop
			}catch(Exception e) {
				System.err.println("[ERROR] "+e.getMessage());
				in.next();// To avoid infinite loop
			}
		}while(!quit);
		in.close();
		System.exit(0);
	}
	
	@Override
	/**
	 * This method is used to display all the options that area available to the user when the game starts.
	 * @author David García Solórzano
	 */
	public void displayMenuOptions(){
	
		System.out.println("\n**********************Welcome to PC BalUOCesto*****************************");
		System.out.println(MSG_CHOOSE_OPTION);		
		System.out.println("\n[1] Start New Game");
		System.out.println("\n[2] Continue Saved Game");
		System.out.println("\n[3] Display Database");
		System.out.println("\n[0] Exit Game");
		System.out.println("*********************************************************************");
	}
	
	
}
