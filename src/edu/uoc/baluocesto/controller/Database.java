package edu.uoc.baluocesto.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.uoc.baluocesto.model.Country;
import edu.uoc.baluocesto.model.League;
import edu.uoc.baluocesto.model.Player;
import edu.uoc.baluocesto.model.Position;
import edu.uoc.baluocesto.model.Stadium;
import edu.uoc.baluocesto.model.Team;

/**
 * This class represents the inventory of the program. It loads and manages library's data.
 * 
 * @author David García Solórzano
 * @version 1.0
 *  
 */
public class Database {

	private HashMap<String,League> leagues;
	
	/**
	 * Constructor with arguments.
	 * @param folderName Name of the folder where the data are.
	 */
	public Database(String folderName){
		leagues = new HashMap<String,League>();
		loadLeagues(folderName);
		loadTeams(folderName);
		loadPlayers(folderName);
	}
				
	/**
	 * Manages the reading of the file and stores league in the inventory as a list.
	 * @param folderName Name of the folder where the data are.
	 */
	private void loadLeagues(String folderName){		
		List<String> list = new ArrayList<String>();
		
		try (Stream<String> stream = Files.lines(Paths.get(folderName,"leagues.txt"),StandardCharsets.ISO_8859_1)) {
			//Convert it into a List
			list = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String item : list){
			String[] elements = item.split("\\*");
								
			try{
				leagues.put(elements[0], new League(elements[0],//shortName
						elements[1],//longName
						Country.valueOf(elements[2])//country
					));
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
			
	}
	
	/**
	 * Returns the whole leagues list.
	 * @return List with the leagues
	 */
	public List<League> getLeagues() {
		return new ArrayList<League>(leagues.values());
	}
	
	/**
	 * Manages the reading of the file and stores teams in the inventory as a list.
	 * @param folderName Name of the folder where the data are.
	 */
	private void loadTeams(String folderName){		
		List<String> list = new ArrayList<String>();
		Team team = null;
		Stadium stadium = null;
				
		try (Stream<String> stream = Files.lines(Paths.get(folderName,"teams.txt"),StandardCharsets.ISO_8859_1)) {
			//Convert it into a List
			list = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String item : list){
			String[] elements = item.split("\\*");
								
			try{
				team = new Team(elements[0],//shortName
								elements[1],//longName
								elements[2],//imageSrc
								elements[3],//president
								elements[4],//sponsor
								Integer.parseInt(elements[5]),//members
								Integer.parseInt(elements[6]),//budget
								Integer.parseInt(elements[7]));//founded
				
				stadium = new Stadium(elements[8],//name
								elements[9],//address
								elements[10],//location
								Integer.parseInt(elements[11]),//capacity
								Integer.parseInt(elements[12]),//parking lots
								LocalDate.parse(elements[13],DateTimeFormatter.ofPattern("dd/MM/yyyy")) //created
						);
				
				team.setStadium(stadium);				
				team.setLeague(leagues.get(elements[14]));		
				
				leagues.get(elements[14]).addTeam(team);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	/**
	 * Returns a list with all the teams of the database.
	 * @return List with all the teams of the database.
	 */
	public List<Team> getTeams() {
		return leagues.values().stream().map((League l) -> l.getTeams()).flatMap(s -> s.stream()).collect(Collectors.toList());
	}
	
	/**
	 * Returns a list with the teams of the given league.
	 * @param leagueShortName League's short name.
	 * @return List with the teams of the given league.
	 */
	public List<Team> getTeamsByLeague(String leagueShortName) {
		return leagues.get(leagueShortName).getTeams();
	}
	
	/**
	 * Returns the Team object whose short name meets the parameter shortName.
	 * @param teamShortName Team's short name.
	 * @return Team object which is required.
	 */
	public Team getTeamByShortName(String teamShortName) {
		return getTeams().stream().filter(t -> t.getShortName().equals(teamShortName)).findFirst().get();
	}
		
	private void loadPlayers(String folderName){		
		List<String> list = new ArrayList<String>();
		Player player = null;
		
				
		try (Stream<String> stream = Files.lines(Paths.get(folderName,"players.txt"),StandardCharsets.ISO_8859_1)) {
			//Convert it into a List
			list = stream.collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String item : list){
			String[] elements = item.split("\\*");
								
			try{
				Team team = getTeamByShortName(elements[8]);
				player = new Player(elements[0],//name
								elements[1],//surname
								elements[2],//nick
								LocalDate.parse(elements[3],DateTimeFormatter.ofPattern("dd/MM/yyyy")),//birthdate
								Country.valueOf(elements[4]),//country
								Integer.parseInt(elements[5])*1000000,//salary
								Integer.parseInt(elements[6])*1000000,//cancellationClause
								Integer.parseInt(elements[7]),//contractYears
								team, //team
								Integer.parseInt(elements[9]),//number
								Integer.parseInt(elements[10]),//height
								Double.parseDouble(elements[11]),//weight
								Integer.parseInt(elements[12]),//numInternational
								Integer.parseInt(elements[13]),//speed
								Integer.parseInt(elements[14]),//defense
								Integer.parseInt(elements[15]),//jump
								Integer.parseInt(elements[16]),//energy
								Integer.parseInt(elements[17]),//ft
								Integer.parseInt(elements[18]),//fg2p
								Integer.parseInt(elements[19]),//fg3p
								Integer.parseInt(elements[20]),//rebounds
								Integer.parseInt(elements[21]),//assists
								Position.valueOf(elements[22]),//position
								elements[23]//imageSrc
						);
				
				team.getSquad().add(player);				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	public List<Player> getPlayersByName(String name){
		//TODO
	}
}