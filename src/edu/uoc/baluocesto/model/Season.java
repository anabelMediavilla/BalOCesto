package edu.uoc.baluocesto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;;

/**
 * Class to simplify the creation of a new season.
 * @author David García Solórzano
 */
public class Season implements Serializable{

	private static final long serialVersionUID = 1L;

	private final String MSG_ERR_NO_TEAMS = "[ERROR] There are no teams in this league!!";
	private final String MSG_ERR_NO_TEAM = "[ERROR] The team is not in this league!!";
	
	private final int NUM_MATCH_DAYS_ROUND, HALF_TEAMS;
	
	List<MatchDay> calendar;
	List<Standing> leagueTable;
	
	
	public Season(LocalDate startDate, List<Team> teams) throws Exception{
		if(teams.size()==0 || teams == null) {
			throw new Exception(MSG_ERR_NO_TEAMS);
		}
		
		//Clasificacion
				leagueTable = new ArrayList<Standing>(teams.size());
				
				for(Team team : teams) {
					leagueTable.add(new Standing(team));
				}
				
				Collections.sort(leagueTable);
				
				//leagueTable.stream().sorted((s1,s2) -> s1.getTeam().getLongName().compareTo(s2.getTeam().getLongName()));
				
				
		
		//Calendario
				
		Collections.shuffle(teams);
				
		NUM_MATCH_DAYS_ROUND = teams.size() - 1;
		HALF_TEAMS = teams.size() / 2;
		
		calendar = new ArrayList<MatchDay>(NUM_MATCH_DAYS_ROUND*2);
		LocalDate today = startDate;
		
		//Partidos de ida
		
		for(int day = 0; day < NUM_MATCH_DAYS_ROUND; day++) {
			MatchDay matchDay = new MatchDay(today);
			//Emparejamos los equipos para la jornada de liga
			for(var i = 0; i<HALF_TEAMS; i++) {				
				matchDay.addMatch(new Match(teams.get(i),teams.get(HALF_TEAMS+i)));
			}
			
			calendar.add(matchDay);
			
			//Desplazamos los equipos menos el primero...
			teams.add(1,teams.remove(HALF_TEAMS)); //El primer equipo visitante pasa a ser el segundo equipo que juega en casa
			teams.add(teams.remove(HALF_TEAMS)); //El ultimo equipo que juega en casa pasa a ser el ultimo que juga como visitante
					
			today = today.plusDays(7);
		}
		
		//Partidos de vuelta
		
		for(int day = 0; day < NUM_MATCH_DAYS_ROUND; day++) {
			MatchDay matchDayFirstLeg = calendar.get(day);
			MatchDay matchDaySecondLeg = new MatchDay(today);
			
			for(var i = 0; i<matchDayFirstLeg.getMatches().size(); i++) {
				matchDaySecondLeg.addMatch(new Match(matchDayFirstLeg.getMatch(i).getAwayTeam(),matchDayFirstLeg.getMatch(i).getHomeTeam()));
			}
				
			calendar.add(matchDaySecondLeg);
			today = today.plusDays(7);
		}		
	}

	public List<MatchDay> getCalendar(){
		return calendar;
	}
	
	public List<Standing> getLeagueTable(){
		return leagueTable;
	}
	
	public int getTeamPosition(Team team) throws Exception{
		//TODO
	}
	
	@Override
	public String toString() {
		return "Season "+", NUM_MATCH_DAYS_ROUND=" + NUM_MATCH_DAYS_ROUND
				+ ", HALF_TEAMS=" + HALF_TEAMS + ", calendar=" + getCalendar() + "]";
	}
	
	
}