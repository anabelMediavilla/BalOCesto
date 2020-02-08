package edu.uoc.baluocesto.view.cmd.menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.stream.Collectors;

import edu.uoc.baluocesto.controller.Manager;
import edu.uoc.baluocesto.model.League;
import edu.uoc.baluocesto.model.Team;
import edu.uoc.baluocesto.view.cmd.CmdApp;

public class NewGameMenu implements Menu{

	private String MSG_CHOOSE_LEAGUE = "Type the number of the league you want to see:";
	private String MSG_CHOOSE_TEAM = "Type the number of the team you want to see:";
	private String MSG_TYPE_MANAGER_NAME = "Please type your name as a manager:";
		
	public NewGameMenu() throws Exception{
		beginMenuSelectionLoop();
	}
	
	@Override
	public void beginMenuSelectionLoop(){			
		System.out.println("\n**********************New Game*****************************");		
		displayMenuOptions();		
	}
	
	@Override
	/**
	 * This method is used to display all the options that area available to the user when the game starts.
	 * @author David García Solórzano
	 */
	public void displayMenuOptions(){		
		ArrayList<League> leagues = new ArrayList<League>(CmdApp.getDatabase().getLeagues());
		boolean quit = false;
		int i = 1, choice = 0;
		
		do{
			i = 1;
			System.out.println("\n[0] Go back!\n[1-"+leagues.size()+"] "+MSG_CHOOSE_LEAGUE);
			
			for(var league : leagues) {
				System.out.println("\t"+(i++)+". "+league.getLongName()+" ("+league.getShortName()+")");
			}
			
			try{
				choice = in.nextInt();
				if(choice<0 || choice>leagues.size()) { 
					System.err.println(MSG_ERR_INVALID_OPTION);
				}else if(choice==0) {
					quit = true;
				}else {
					ArrayList<Team> teams = new ArrayList<Team>(CmdApp.getDatabase().getTeamsByLeague(leagues.get(choice-1).getShortName()).stream().map((Team team) -> team.clone()).collect(Collectors.toList()));
					
					i = 1;
					if(teams.size()!=0) {
						System.out.println("\n[0] Go back!\n[1-"+teams.size()+"] "+MSG_CHOOSE_TEAM);
					
					
						for(var team : teams) {
							System.out.println("\t"+(i++)+". "+team.getLongName());
						}
					
						try{
							choice = in.nextInt();
							if(choice!=0) {
								int teamIndex = choice-1;
								System.out.println(MSG_TYPE_MANAGER_NAME);
								String managerName = in.next();							
								System.out.println(MSG_DATA_OK+teams.get(teamIndex).getShortName()+", "+managerName);
								
								if (in.next().toUpperCase().equals("Y")) {								
									try{
										CmdApp.setManager(new Manager(managerName, teams, teams.get(teamIndex)));									
										new ManagerMenu();
										quit = true;				
									}catch(Exception e) {
										System.err.println(e.getMessage());
									}
								}
							}					
						}catch(IndexOutOfBoundsException | NumberFormatException e) {
							System.err.println(MSG_ERR_INVALID_OPTION);
							in.next();// To avoid infinite loop
						}
					}
				}
			}catch(InputMismatchException e) {
				System.err.println(MSG_ERR_TYPE_NUMBER);
				in.next();// To avoid infinite loop
			}
			
		}while(!quit);		
	}
}
