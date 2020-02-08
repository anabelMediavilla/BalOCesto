package edu.uoc.baluocesto.view.cmd.menu;

import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

import edu.uoc.baluocesto.model.Match;
import edu.uoc.baluocesto.model.Player;
import edu.uoc.baluocesto.model.Position;
import edu.uoc.baluocesto.model.Team;
import edu.uoc.baluocesto.model.TrainingType;
import edu.uoc.baluocesto.view.cmd.CmdApp;

public class ManagerMenu implements Menu{

	private final int MILLION = 1000000;
	
	private final String MSG_ERR_NO_PREVIOUS_DAY = "You are in Day 1, so there are not more previous match days.";
	private final String MSG_ERR_NO_NEXT_DAY = "You are in the last day of the season, so there are not more next match days.";
	private final String MSG_ERR_PLAYER_NOT_EXIST = "The number you have written is not assigned to any player of your team!!";
	private final String MSG_ERR_SAVE_GAME = "There has been an error while saving your game!!";
	private final String MSG_STADIUM_NO_HOME_TEAM ="Your team does not play at home. So you cannot modify ticket and billboard prices.";
	
	private final String MSG_SAVE_GAME_OK = "Your game has been saved correctly!!";
	
	public ManagerMenu() {
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
						displayLeagueTable();
						break;
					case 2:
						displayResultsCalendar();						
						break;
					case 3:
						displayLineupMenu();
						break;
					case 4:
						displaySeeOpponent();
						break;
					case 5:
						displayTransferPlayers();
						break;
					case 6:
						displaySquad();
						break;
					case 7:
						displayCashBalance();
						break;
					case 8: 
						displayStadium();
						break;					
					case 9:
						CmdApp.getManager().advance();
						break;
					case 10:
						if(CmdApp.getManager().saveGame()) {
							System.out.println(MSG_SAVE_GAME_OK);
							System.out.println(MSG_CONTINUE);
							in.next();
						}else {
							System.err.println(MSG_ERR_SAVE_GAME);
						}
						break;
						
				}
			} catch (InputMismatchException e) {
					System.err.println(MSG_ERR_INVALID_OPTION);
					in.next();// To avoid infinite loop
			} catch (Exception e) {
					System.err.println(e.getMessage());
			}
			
		}while(!quit);		
	}

	@Override
	public void displayMenuOptions() {
		System.out.println("\n******************"+CmdApp.getManager().getTeamName(true)+", "+CmdApp.getManager().getManagerName()+"*************************");
		Match match = CmdApp.getManager().getCurrentMyMatch();
		System.out.println("\nDay "+(CmdApp.getManager().getCurrentMatchDayIndex()+1)+" / "+CmdApp.getManager().getCurrentMatchDay().getDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))+": "+match.getHomeTeam().getShortName()+" - "+match.getAwayTeam().getShortName());
		
		System.out.println("\nCompetition\t\t\tCoach");
		System.out.println("\t[1] League Table\t\t[3] Lineup");
		System.out.println("\t[2] Results/Calendar\t\t[4] Opponent");
		System.out.println("\nTransfer\t\t\tFinance");
		System.out.println("\t[5] Buy\t\t\t\t[7] Cash balance");
		System.out.println("\t[6] Squad\t\t\t[8] Stadium");
		System.out.println("--------------------------------------------------------------");
		System.out.println("[0] Go back!\t\t[9] Continue\t\t[10] Save game");
	}
	
	
	private void displayLeagueTable() {
		System.out.printf("%S%n","---------------------- League Table ---------------------");
		System.out.printf("|%-20s %6s %6s %6s %6s %6s|%n","Team","P","W","L","PF","PA");
		System.out.println("---------------------------------------------------------");		
		CmdApp.getManager().getLeagueTable().stream().forEach(standing -> System.out.printf("|%-20s %6d %6d %6d %6d %6d|%n",standing.getTeam().getLongName(),standing.getPlayedGames(),standing.getWins(),standing.getLosses(),standing.getPointsFor(),standing.getPointsAgainst()));
		System.out.println("---------------------------------------------------------");
		System.out.println("\n"+MSG_CONTINUE);
		in.next();
	}
	
	private void displayResultsCalendar() {
		int today = CmdApp.getManager().getCurrentMatchDayIndex();
		int choice = 0, maxDays =  CmdApp.getManager().getNumSeasonDays();
		int dayToSee = today;
		boolean quit = false;
		
		System.out.printf("%S%n","----------- Results/Calendar ----------");
		
		do{
			System.out.printf("%20S%n"," Day "+(dayToSee+1));
			System.out.printf("%S%n","---------------------------------------");
			for(var match : CmdApp.getManager().getMatchDay(dayToSee).getMatches()) {
				System.out.printf("|%30s |%n",match.getHomeTeam().getShortName()+" - "+match.getAwayTeam().getShortName()+":\t"+match.getPointsHomeTeam()+" - "+match.getPointsAwayTeam());
				
			}
			System.out.printf("%S%n","---------------------------------------");
						
			System.out.printf("%n%10s %10s %10s", "[0] Go back!", ((dayToSee+1) < maxDays)?"[1] Next day":"", (dayToSee>0)?"[2] Previous day":"");
				
			try{
				choice = in.nextInt();
				switch(choice) {
					case 0:
						quit = true;
						break;
					case 1:
						if((dayToSee+1) < maxDays) {
							dayToSee++;
						}else {
							System.err.println(MSG_ERR_NO_NEXT_DAY);
						}
						break;
					case 2:
						if(dayToSee>0) {
							dayToSee--;
						}else {
							System.err.println(MSG_ERR_NO_PREVIOUS_DAY);
							in.next();// To avoid infinite loop
						}
						break;
					default: 
						System.err.println(MSG_ERR_INVALID_OPTION);
						in.next();// To avoid infinite loop
						break;
				}
			} catch (InputMismatchException e) {
				System.err.println(MSG_ERR_INVALID_OPTION);
				in.next();// To avoid infinite loop
			}
		}while(!quit);	
	}
	
	private void displayLineupMenu() {
		Player player = null;
		List<Player> squad = CmdApp.getManager().getMySquad();
		boolean quit = false;
		int choice = 0;
		
		do{
			System.out.printf("%S%n","------------Starting Lineup-----------");
			
			System.out.printf("|%-3s %-12s %-2s %4s %11s |%n","#","Player","P","Avg.","Training");
			System.out.printf("%S%n","--------------------------------------");	
			
			for(int i = 0; i<squad.size();i++) {
				player = squad.get(i);
				TrainingType trainingType = CmdApp.getManager().trainingTypeByPlayer(player.getNumber());
				System.out.printf("|%2d. %-12s %-2s %4d %11s |%n",player.getNumber(),player.getNick(),player.getPosition().getAbbreviation(),player.getAttributes().getAverage(),trainingType!=null?trainingType:"-");
				
				if(i==4) {
					System.out.printf("%S%n","----------------Bench-----------------");
					System.out.printf("|%-3s %-12s %-2s %4s %11s |%n","#","Player","P","Avg.","Training");
					System.out.printf("%S%n","--------------------------------------");		
				}
			}
			System.out.printf("%S%n","--------------------------------------");		
			System.out.println("[0] Go back!\t[1] Swap two players\t[2] Training");
			
			try{
				choice = in.nextInt();
						
				if(choice==0) {
					quit = true;						
				}else if(choice==1){					
					displaySwapPlayersMenu();
				}else if(choice==2){
					displayTrainingMenu();
				}else {				
					System.err.println(MSG_ERR_INVALID_OPTION);	
					in.next();// To avoid infinite loop
				}
			} catch (InputMismatchException e) {
				System.err.println(MSG_ERR_INVALID_OPTION);
				in.next();// To avoid infinite loop
			}			
		}while(!quit);		
	}
	
	private void displaySwapPlayersMenu() {
		boolean quit = false;
				
		do {
			System.out.println("Type the number of the first player to swap: ");
			int firstPlayerNumber = in.nextInt();
			System.out.println("Type the number of the second player to swap: ");
			int secondPlayerNumber = in.nextInt();
				
			try{
				CmdApp.getManager().swapPlayers(firstPlayerNumber,secondPlayerNumber);				
				quit = true;				
			}catch(NoSuchElementException e) {
				System.err.println(MSG_ERR_PLAYER_NOT_EXIST);
				in.next();// To avoid infinite loop
			}
		}while(!quit);
		
	}
	
	private void displayTrainingMenu(){
		boolean quit = false;
					
		
		do {
			System.out.println("\n[0] Go back\t[1] Assign training\t[2]Remove training");
			int choice = in.nextInt();
			switch(choice) {
				case 0:
						quit = true;
						break;
				case 1:
						displayAssignTraining();						
						break;
				case 2:						
						displayRemoveTraining();
						break;
			
			}
		}while(!quit);
		
	}
	
	private void displayAssignTraining()  {
		boolean quit = false;
		int playerNumber = 0;
		TrainingType trainingType = null;
		
		do{
			System.out.print("[1] Assists ("+CmdApp.getManager().trainingTypeVacancies(TrainingType.ASSISTS)+")");
			System.out.print("\t[2] Defense ("+CmdApp.getManager().trainingTypeVacancies(TrainingType.DEFENSE)+")");
			System.out.println("\t[3] Rebounds ("+CmdApp.getManager().trainingTypeVacancies(TrainingType.REBOUNDS)+")");
			
			System.out.print("[4] Ft ("+CmdApp.getManager().trainingTypeVacancies(TrainingType.FT)+")");
			System.out.print("\t[5] Fg2p ("+CmdApp.getManager().trainingTypeVacancies(TrainingType.FG2P)+")");
			System.out.println("\t[6] Fg3p ("+CmdApp.getManager().trainingTypeVacancies(TrainingType.FG3P)+")");
			
			System.out.println("\nType the number of the training type:");
			
			switch(in.next()) {
				case "1":
						trainingType = TrainingType.ASSISTS;
						quit = true;
						break;
				case "2":
						trainingType = TrainingType.DEFENSE;
						quit = true;
						break;
				case "3":
						trainingType = TrainingType.REBOUNDS;
						quit = true;
						break;
				case "4":
						trainingType = TrainingType.FT;
						quit = true;
						break;
				case "5":
						trainingType = TrainingType.FG2P;
						quit = true;
						break;
				case "6":
						trainingType = TrainingType.FG3P;
						quit = true;
						break;				
				default:
						System.err.println(MSG_ERR_INVALID_OPTION);
			}
		}while(!quit);
			
		quit = false;
			
		do{
			System.out.println("Type the number of the player:");
			try{
				playerNumber = in.nextInt();
				quit = true;
			}catch(InputMismatchException e){
				System.err.println(MSG_ERR_TYPE_NUMBER);
			}
		}while(!quit);
			
		try {
			CmdApp.getManager().assignTraining2Player(playerNumber, trainingType);
		}catch(NoSuchElementException e) {
			System.err.println(MSG_ERR_PLAYER_NOT_EXIST);
		}catch (Exception e) {			
			System.err.println(e.getMessage());
		}
	}
	
	private void displayRemoveTraining() {
		System.out.println("Type the number of the player who has a training assigned:");
		int playerNumber = in.nextInt();
		CmdApp.getManager().removeTraining2Player(playerNumber);
	}
	
	private void displaySeeOpponent() {
		
		Team teamHome = CmdApp.getManager().getCurrentMyMatch().getHomeTeam();
		Team teamAway = CmdApp.getManager().getCurrentMyMatch().getAwayTeam();
		
		System.out.printf("%S%n","----------- See Opponent ---------");
		System.out.printf("|%10S %9s %10S|", teamHome.getShortName()," vs ",teamAway.getShortName());
		System.out.printf("%n%S","----------------------------------");
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getSpeedAverage(), "Speed", teamAway.getSpeedAverage());
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getJumpAverage(), "Jump", teamAway.getJumpAverage());
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getEnergyAverage(), "Energy", teamAway.getEnergyAverage());
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getFtAverage(), "Free throw", teamAway.getFtAverage());
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getFg2pAverage(), "2 points", teamAway.getFg2pAverage());
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getFg3pAverage(), "3 points", teamAway.getFg3pAverage());
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getAssistsAverage(), "Assists", teamAway.getAssistsAverage());
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getDefenseAverage(), "Defense", teamAway.getDefenseAverage());
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getReboundsAverage(), "Rebounds", teamAway.getReboundsAverage());
		System.out.printf("%n%S","----------------------------------");
		System.out.printf("%n|%-10.0f %10s %10.0f|", teamHome.getAverage(), "Average", teamAway.getAverage());
		System.out.printf("%n%S","----------------------------------");
		System.out.println("\n"+MSG_CONTINUE);
		in.next();		
	}
	
	private void displayTransferPlayers() {
		int index = 1;
		
		System.out.printf("%S%n","---------------------Transfer list---------------------");
		for(var player : CmdApp.getManager().getTransferList()) {			
			System.out.printf("|%-3s %-12s %-2s %4s %10s %10s %6s |%n","#","Player","P","Avg.","Clause","Salary","Years");
			System.out.printf("%S%n","-------------------------------------------------------");
			System.out.printf("|%-2d. %-12s %-2s %4d %8d m %8d m %6d |%n",index,player.getNick(),player.getPosition().getAbbreviation(),player.getAttributes().getAverage(),player.getContract().getCancellationClause()/MILLION,player.getContract().getSalary()/MILLION,player.getContract().getContractYears());
			index++;
		};
		System.out.printf("%S%n","-------------------------------------------------------");
		System.out.println("[0] Go back!\t[1-"+CmdApp.getManager().getTransferList().size()+"] Make an offer (type the number of the player)");
		int choice = in.nextInt();
		if(choice!=0) {			
			displayOffer(CmdApp.getManager().getTransferList().get(choice-1));			
		}
	
		System.out.println("\n"+MSG_CONTINUE);
		in.next();	
	}
	
	private void displayOffer(Player player) {
		try{
			System.out.println("Type your offer: ");
				int offer = in.nextInt();
			System.out.println("Type clause (in millions): ");
				int clause = in.nextInt();
			System.out.println("Type salary (in millions): ");
				int salary = in.nextInt();
			System.out.println("Type number of years: ");
				int years = in.nextInt();
									
			System.out.println(MSG_DATA_OK+offer+" m, "+clause+" m, "+salary+" m, "+years+" years for "+player.getNick()+"?");
		
			if (in.next().toUpperCase().equals("Y")) {								
				try{
					CmdApp.getManager().createOffer(player,offer*MILLION,clause*MILLION,salary*MILLION,years);
					System.out.println("You have just made an offer for "+player.getNick()+"!!");
				}catch(Exception e) {
					System.err.println(e.getMessage());
				}
			}		
		}catch(InputMismatchException e) {
			System.err.println(MSG_ERR_TYPE_NUMBER);
			in.next();// To avoid infinite loop
		}catch(NoSuchElementException e) {
			System.err.println(MSG_ERR_PLAYER_NOT_EXIST);
			in.next();// To avoid infinite loop
		}		
	}
	
	private void displaySquad() {
		boolean quit = false;
		int choice = 0;
		
		do{
		
			System.out.printf("%S%n","----------------------- Squad --------------------------");
			System.out.printf("|%-3s %-12s %7s %5s %8s %8s %5s |%n","#","Player","Height","Avg.","Clause","Salary","Years");
			System.out.printf("%S%n","--------------------------------------------------------");
			
			
			//Bases
			if(CmdApp.getManager().getMySquadByPosition(Position.PG).size()>0)
				System.out.printf("%-50S%n",Position.PG+" --------------------------------------------");
			
			CmdApp.getManager().getMySquadByPosition(Position.PG).stream().
														forEach(player -> 
																System.out.printf("|%2d. %-12s %7s %5d %,6d m %,6d m %5d |"+(CmdApp.getManager().isPlayerInTransferList(player)?"*":"")+"%n",player.getNumber(),player.getNick(),player.getAttributes().getHeight(),player.getAttributes().getAverage(),player.getContract().getCancellationClause()/MILLION,player.getContract().getSalary()/MILLION,player.getContract().getContractYears())
																);
			
			//Escoltas
			if(CmdApp.getManager().getMySquadByPosition(Position.SG).size()>0) 
				System.out.printf("%-50S%n",Position.SG+" -----------------------------------------");
			
			CmdApp.getManager().getMySquadByPosition(Position.SG).stream().forEach(player -> 
				System.out.printf("|%2d. %-12s %7s %5d %,6d m %,6d m %5d |"+(CmdApp.getManager().isPlayerInTransferList(player)?"*":"")+"%n",player.getNumber(),player.getNick(),player.getAttributes().getHeight(),player.getAttributes().getAverage(),player.getContract().getCancellationClause()/MILLION,player.getContract().getSalary()/MILLION,player.getContract().getContractYears())
			);
			
			//Aleros
			if(CmdApp.getManager().getMySquadByPosition(Position.SF).size()>0) 
				System.out.printf("%-50S%n",Position.SF+" ------------------------------------------");
			
			CmdApp.getManager().getMySquadByPosition(Position.SF).stream().forEach(player -> 
				System.out.printf("|%2d. %-12s %7s %5d %,6d m %,6d m %5d |"+(CmdApp.getManager().isPlayerInTransferList(player)?"*":"")+"%n",player.getNumber(),player.getNick(),player.getAttributes().getHeight(),player.getAttributes().getAverage(),player.getContract().getCancellationClause()/MILLION,player.getContract().getSalary()/MILLION,player.getContract().getContractYears())
			);
			
			//Ala-Pivots
			if(CmdApp.getManager().getMySquadByPosition(Position.PF).size()>0)
				System.out.printf("%-50S%n",Position.PF+" ------------------------------------------");
			
			CmdApp.getManager().getMySquadByPosition(Position.PF).stream().forEach(player -> 
				System.out.printf("|%2d. %-12s %7s %5d %,6d m %,6d m %5d |"+(CmdApp.getManager().isPlayerInTransferList(player)?"*":"")+"%n",player.getNumber(),player.getNick(),player.getAttributes().getHeight(),player.getAttributes().getAverage(),player.getContract().getCancellationClause()/MILLION,player.getContract().getSalary()/MILLION,player.getContract().getContractYears())
			);
			
			//Pivots
			if(CmdApp.getManager().getMySquadByPosition(Position.C).size()>0)
				System.out.printf("%-50S%n",Position.C+" -------------------------------------------------");
			
			CmdApp.getManager().getMySquadByPosition(Position.C).stream().forEach(player -> 
				System.out.printf("|%2d. %-12s %7s %5d %,6d m %,6d m %5d |"+(CmdApp.getManager().isPlayerInTransferList(player)?"*":"")+"%n",player.getNumber(),player.getNick(),player.getAttributes().getHeight(),player.getAttributes().getAverage(),player.getContract().getCancellationClause()/MILLION,player.getContract().getSalary()/MILLION,player.getContract().getContractYears())
			);
			
			System.out.printf("%S%n","--------------------------------------------------------");
			
			System.out.printf("%-15s %20s %15s%n[0] Go back!","[1] Renew","[2] Transfer/Untransfer","[3] Fire");
			
			try{
				choice = in.nextInt();
				switch(choice) {
					case 0:
							quit = true;
							break;
					case 1:
							//Renew	
							displayRenewPlayer();
							break;
					case 2:
							//Transfer/Untransfer
							displayTransferPlayer();													
							break;
					case 3:
							//Fire
							displayFirePlayer();
							break;							
				}
			} catch (InputMismatchException e) {
				System.err.println(MSG_ERR_INVALID_OPTION);
				in.next();// To avoid infinite loop
			}				
			
		}while(!quit);
	}
	
	private void displayRenewPlayer() {
		int playerNumber = 0;
		
		System.out.println("Type the number of the player you want to renew: ");
		playerNumber = in.nextInt();
		
		try{
			Player player = CmdApp.getManager().getPlayerFromMySquadByNumber(playerNumber);
			
			System.out.printf("|%-3s %-12s %7s %5s %8s %8s %5s |%n","#","Player","Height","Avg.","Clause","Salary","Years");
			System.out.printf("%S%n","--------------------------------------------------------");			
			System.out.printf("|%2d. %-12s %7s %5d %,6d m %,6d m %5d |%n",player.getNumber(),player.getNick(),player.getAttributes().getHeight(),player.getAttributes().getAverage(),player.getContract().getCancellationClause()/MILLION,player.getContract().getSalary()/MILLION,player.getContract().getContractYears());
			System.out.printf("%S%n","--------------------------------------------------------");
						
			System.out.println("Type your offer: ");
				int offer = in.nextInt();
			System.out.println("Type clause (in millions): ");
				int clause = in.nextInt();
			System.out.println("Type salary (in millions): ");
				int salary = in.nextInt();
			System.out.println("Type number of years: ");
				int years = in.nextInt();
									
			System.out.println(MSG_DATA_OK+clause+" m, "+salary+" m, "+years+" years for "+player.getNick()+"?");
		
			if (in.next().toUpperCase().equals("Y")) {								
				try{
					CmdApp.getManager().createOffer(player,offer*MILLION,clause*MILLION,salary*MILLION,years);
					System.out.println("You have just made an offer for "+player.getNick()+"!!");
				}catch(Exception e) {
					System.err.println(e.getMessage());
				}
			}		
		}catch(InputMismatchException e) {
			System.err.println(MSG_ERR_TYPE_NUMBER);
			in.next();// To avoid infinite loop
		}catch(NoSuchElementException e) {
			System.err.println(MSG_ERR_PLAYER_NOT_EXIST);
			in.next();// To avoid infinite loop
		}		
	}
	
	private void displayTransferPlayer() {
		int playerNumber = 0;
		
		System.out.println("Type the number of the player you want to transfer/untransfer: ");
		playerNumber = in.nextInt();
		
		try {
			if(CmdApp.getManager().transferPlayer(playerNumber)) {
				System.out.println(CmdApp.getManager().getPlayerFromMySquadByNumber(playerNumber).getNick()+" has been added to the transfer list!!");
			}else {
				System.out.println(CmdApp.getManager().getPlayerFromMySquadByNumber(playerNumber).getNick()+" has been removed from the transfer list!!");
			}
			System.out.println(MSG_CONTINUE);
			in.next();
		}catch(NoSuchElementException e) {
			System.err.println(MSG_ERR_PLAYER_NOT_EXIST);
			in.next();// To avoid infinite loop
		}	
	}
	
	private void displayFirePlayer() {
		int playerNumber = 0;
		
		System.out.println("Type the number of the player you want to fire: ");
		playerNumber = in.nextInt();
		
		try {
			System.out.println("Are you sure that you want to fire "+CmdApp.getManager().getPlayerFromMySquadByNumber(playerNumber).getNick()+"? (Y/N)");
			
			if (in.next().toUpperCase().equals("Y")) {
			
					String playerNick = CmdApp.getManager().getPlayerFromMySquadByNumber(playerNumber).getNick();
					if(CmdApp.getManager().firePlayer(playerNumber)) {
						System.out.println(playerNick+" has been fired!!");						
					}
					System.out.println(MSG_CONTINUE);					
					in.next();				
			}
		}catch(NullPointerException|NoSuchElementException e) {					
			System.err.println(MSG_ERR_PLAYER_NOT_EXIST);
			System.out.println(MSG_CONTINUE);
			in.next();// To avoid infinite loop
		}		
	}
	
	private void displayCashBalance() {
		int today = CmdApp.getManager().getCurrentMatchDayIndex();
		int choice = 0;
		int dayToSee = today;
		boolean quit = false;
		
		System.out.printf("%S%n","----------------------------------------------- Cash Balance -----------------------------------------------");
		
		do{
			System.out.printf("%-30s %40s%n","Day "+(dayToSee+1)+" ("+CmdApp.getManager().getMatchDay(dayToSee).getDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))+") ",CmdApp.getManager().getMyMatch(dayToSee),"|");
			
			System.out.print(CmdApp.getManager().getWeeklyCash(dayToSee));
			System.out.printf("%100s %,15d ptas.|%n", "Budget|",CmdApp.getManager().getBudget());
			System.out.format("%s%n", "---------------------------------------------------------------------------------------------------------------------------");
			
			System.out.printf("%10s %10s %10s", "[0] Go back!", ((dayToSee) < today)?"[1] Next day":"", (dayToSee>0)?"[2] Previous day":"");
						
			
			try{
				choice = in.nextInt();
				switch(choice) {
					case 0:
						quit = true;
						break;
					case 1:
						if((dayToSee) < today) {
							dayToSee++;
						}else {
							System.err.println(MSG_ERR_NO_NEXT_DAY);
						}
						break;
					case 2:
						if(dayToSee>0) {
							dayToSee--;
						}else {
							System.err.println(MSG_ERR_NO_PREVIOUS_DAY);
							in.next();// To avoid infinite loop
						}
						break;
					default: 
						System.err.println(MSG_ERR_INVALID_OPTION);
						in.next();// To avoid infinite loop
						break;
				}
			} catch (InputMismatchException e) {
				System.err.println(MSG_ERR_INVALID_OPTION);
				in.next();// To avoid infinite loop
			}
		}while(!quit);	
	}
	
	private void displayStadium() {
		System.out.printf("%S%n","------------------------- Stadium decisions -------------------------");
		if(CmdApp.getManager().isPlayingAtHome()) {
			//Si nuestro equipo juega en casa, podremos gestionar los precios de las entradas y vallas.
			boolean quit = false;
			
				do{
					System.out.printf("|%-25s %,6d ptas.| [1] Increase\t[2] Decrease%n", "Tickets", CmdApp.getManager().getTicketPrice());
					System.out.printf("|%-25s %,6d ptas.| [3] Increase\t[4] Decrease%n", "Billboards", CmdApp.getManager().getBillboardPrice());
					System.out.printf("%S","---------------------------------------------------------------------");
					System.out.println("\n[0] Go back!");
					
					int choice = in.nextInt();
					try{
						switch(choice) {
							case 0:
									quit = true;
									break;
							case 1:
									CmdApp.getManager().increaseTicketPrice();
									break;
							case 2:
									CmdApp.getManager().decreaseTicketPrice();
									break;
							case 3:
									CmdApp.getManager().increaseBillboardPrice();
									break;
							case 4:
									CmdApp.getManager().decreaseBillboardPrice();;
									break;						
						}
					}catch(Exception e) {
						System.err.println(e.getMessage());						
					}
				}while(!quit);
			
			
		}else {
			System.out.println("\n"+MSG_STADIUM_NO_HOME_TEAM);
			System.out.println("\n"+MSG_CONTINUE);
			in.next();
		}
	}
}
