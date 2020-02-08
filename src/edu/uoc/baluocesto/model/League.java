package edu.uoc.baluocesto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class League{
	
	private String shortName, longName;
	private Country country;
	
	private HashMap<String,Team> teams;
	
	private final static String MSG_REMOVE_TEAM_NOT_EXISTS_ERROR = "[ERROR] The team that you want to remove does not exist!!";
	private final static String MSG_GET_TEAM_NOT_EXISTS_ERROR = "[ERROR] The team that you want to retrieve does not exist!!";
	
	public League(String shortName,String longName, Country country) {
		setShortName(shortName);
		setLongName(longName);
		setCountry(country);
		teams = new HashMap<String,Team>();
	}

	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public void addTeam(Team team) {
		teams.put(team.getShortName(),team);
	}
	
	public void removeTeam(String shortName) throws Exception{
		if(teams.remove(shortName)==null) {
			throw new Exception(MSG_REMOVE_TEAM_NOT_EXISTS_ERROR);
		};
	}
	
	public void removeAll() {
		teams.clear();
	}
	
	public Team getTeam(String shortName) throws Exception{
		Team team = teams.get(shortName);
		if(team!=null) {
			return team;
		}
		throw new Exception(MSG_GET_TEAM_NOT_EXISTS_ERROR);
	}
	
	public List<Team> getTeams(){
		ArrayList<Team> list = new ArrayList<Team>();
		list.addAll(teams.values());
		return list;
	}
	
	@Override
	public String toString() {
		return getLongName()+" ("+getShortName()+", "+getCountry()+"): "+teams.size();
	}	
}