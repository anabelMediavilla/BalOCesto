package edu.uoc.baluocesto.view.cmd.menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

import edu.uoc.baluocesto.model.League;
import edu.uoc.baluocesto.model.Player;
import edu.uoc.baluocesto.model.Team;
import edu.uoc.baluocesto.view.cmd.CmdApp;

public class DatabaseMenu implements Menu{

	private String MSG_DISPLAY_PLAYER_MENU = "Type the name of the player you want to see:";
	private String MSG_DISPLAY_TEAM_MENU_1 = "Type the number of the league you want to see:";
	private String MSG_DISPLAY_TEAM_MENU_2 = "Type the number of the team you want to see:";
	
	public DatabaseMenu() {
		beginMenuSelectionLoop();
	}
	
	@Override
	public void beginMenuSelectionLoop() {		
		boolean quit = false;
		do{
			displayMenuOptions();
			try {
				switch (in.nextInt()) {
					case 0:
						quit = true;
						break;
					case 1:
						displayTeamMenu();
						break;
					case 2:
						displayPlayerMenu();
						break;
						
				}
			} catch (InputMismatchException e) {
					System.err.println(MSG_ERR_INVALID_OPTION);
					in.next();// To avoid infinite loop
			}			
			
		}while(!quit);		
	}

	@Override
	public void displayMenuOptions() {
		System.out.println("------------------------------------------------------------");
		System.out.println("Database");
		System.out.println("------------------------------------------------------------");
		System.out.println("Please choose one of the following options:");
		System.out.println("[0] Go back!");
		System.out.println("[1] Choose team");
		System.out.println("[2] Choose player");		
	}
	
	public void displayTeamMenu() {
		int i, choice;
		boolean quitLeague = false, quitTeam = false;
		
		ArrayList<League> leagues = new ArrayList<League>(CmdApp.getDatabase().getLeagues());
		
							
		do{
			i = 1;
			System.out.println("\n[0] Go back!\n[1-"+leagues.size()+"] "+MSG_DISPLAY_TEAM_MENU_1);
			
			for(var league : leagues) {
				System.out.println("\t"+i+". "+league.getLongName()+" ("+league.getShortName()+")");
				i++;
			}
		
			try{
				choice = in.nextInt();
				ArrayList<Team> teams = new ArrayList<Team>(leagues.get(choice-1).getTeams());
				
				if(choice<0 || teams == null) { 
					System.err.println(MSG_ERR_INVALID_OPTION);
				}else if(choice==0 || teams.size()==0) {
					quitLeague = true;
				}else{
					do {
						quitLeague = false;
						quitTeam = false;
						i = 1;
						System.out.println("\n[0] Go back!\n[1-"+teams.size()+"] "+MSG_DISPLAY_TEAM_MENU_2);
							
						for(var team : teams) {
							System.out.println("\t"+i+". "+team.getLongName());
							i++;
						}
										
						choice = in.nextInt();
						if(choice<0 || choice>teams.size()) { 
							System.err.println(MSG_ERR_INVALID_OPTION);
						}else if(choice!=0){
							Team team = teams.get(choice-1);
							System.out.println(team);						
						}else {
							quitTeam = true;
						}						
					}while(!quitTeam);	
				}
			}catch(InputMismatchException e) {
				System.err.println(MSG_ERR_TYPE_NUMBER);
				in.next();// To avoid infinite loop
			}			
		}while(!quitLeague);
						
	}	
	
	public void displayPlayerMenu() {		
		System.out.println(MSG_DISPLAY_PLAYER_MENU);
		
		String playerToFind = in.next();
		
		//Usando Stream y lambda expressions de Java 8
		List<Player> players =  CmdApp.getDatabase().getPlayersByName(playerToFind);
				
		players.stream().forEach(System.out::println);		
	}	
}
