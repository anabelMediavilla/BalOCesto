package edu.uoc.baluocesto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatchDay implements Serializable{

	private static final long serialVersionUID = 1L;
	
	LocalDate date;
	List<Match> matches;
	
	public MatchDay(LocalDate date) {
		setDate(date);
		matches = new ArrayList<Match>();		
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}
	
	public void addMatch(Match match) {
		matches.add(match);
	}
	
	public Match getMatch(int index) {
		return matches.get(index);
	}
	
	public Match findMatchByTeam(Team team) {
		for(var match : matches) {
			if(match.getHomeTeam().getLongName().equals(team.getLongName()) || match.getAwayTeam().getLongName().equals(team.getLongName())) {
				return match;
			}
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for(var match: matches) {
			str.append(match);
		}
		
		return str.toString();
	}
	
}
