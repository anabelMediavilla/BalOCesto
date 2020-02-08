package edu.uoc.baluocesto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Team implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String shortName, longName, imageSrc, president, sponsor;
	private int members, budget, founded;
	
	private Stadium stadium;
	private League league;
	
	private final int MAX_PLAYERS = 12;
	private final int MIN_PLAYERS = 8;
	private List<Player> squad;
	
	private final int NUM_COACHES = 6;
	private List<Coach> coaches;	
	
	private final String MSG_SHORTNAME_ERROR ="[ERROR] Short name cannot be longer than 40 characters!!";	
	private final String MSG_MEMBERS_ERROR = "[ERROR] Members must be equal or greater than 0!!";
	private final String MSG_BUDGET_ERROR = "[ERROR] Budget must be equal or greater than 1900!!";
	private final String MSG_FOUNDED_ERROR = "[ERROR] Year foundation must be equal or greater than 1900!!";
	
	private final String MSG_ADD_PLAYER_EXISTS_ERROR ="[ERROR] The player who you want to add is already in the team!!";
	private final String MSG_ADD_PLAYER_MAX_ERROR = "Your team cannot have more than "+MAX_PLAYERS+" players!!";
	private final String MSG_REMOVE_PLAYER_NOT_EXISTS_ERROR = "[ERROR] The player you want to remove does not exist in your team!!";
	private final String MSG_REMOVE_PLAYER_MIN_ERROR = "[ERROR] Your team cannot have less than "+MIN_PLAYERS+" players!!";
	private final String MSG_GET_PLAYER_INCORRECT_INPUT_ERROR = "[ERROR] ] The number is incorrect. The number must be 0 or positive!!";
	private final String MSG_GET_PLAYER_NOT_EXISTS_ERROR = "[ERROR] The player you want to retrieve does not exist in your team!!";
				
	public Team() throws Exception{
		this("Default", "Team Default", "./", "Dummy", "UOC",1000,1000000,2019);
	}
	
	public Team(String shortName, String longName, String imageSrc, String president, String sponsor, int members, int budget, int founded) throws Exception{
		setShortName(shortName);
		setLongName(longName);
		setImageSrc(imageSrc);
		setPresident(president);
		setSponsor(sponsor);
		setMembers(members);
		setBudget(budget);
		setFounded(founded);
		squad = new ArrayList<Player>(MAX_PLAYERS);		
		setupCoaches();
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) throws Exception{
		if(shortName.length()>40) throw new Exception(MSG_SHORTNAME_ERROR);
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {		
		this.longName = longName;
	}
	
	public String getImageSrc() {
		return imageSrc;
	}
	
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getPresident() {
		return president;
	}

	public void setPresident(String president) {
		this.president = president;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public int getMembers() {
		return members;
	}

	public void setMembers(int members) throws Exception{
		if(members<0) throw new Exception(MSG_MEMBERS_ERROR);
		this.members = members;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) throws Exception{
		if(budget<0) {
			throw new Exception(MSG_BUDGET_ERROR);
		}
		this.budget = budget;
	}

	public int getFounded() {
		return founded;
	}

	public void setFounded(int founded) throws Exception{
		if(founded<1900) {
			throw new Exception(MSG_FOUNDED_ERROR);
		}
		this.founded = founded;
	}

	public Stadium getStadium() {
		return stadium;
	}

	public void setStadium(Stadium stadium) {
		this.stadium = stadium;
	}
	
	public League getLeague() {
		return league;
	}
	
	public void setLeague(League league) {
		this.league = league;
	}

	public List<Player> getSquad(){
		return squad;
	}
	
	public void setSquad(List<Player> squad) throws Exception{
		if(squad == null || squad.size()<8)  throw new Exception(MSG_REMOVE_PLAYER_MIN_ERROR);
		if(squad.size()>12) throw new Exception(MSG_ADD_PLAYER_MAX_ERROR); 
		this.squad = squad;		
	}
	
	public List<Player> getLineup(){ //Jugadores en pista...
		//Los nulls se controlan solos...		
				if(getSquad()!=null) 
					return getSquad().subList(0, ( getSquad().size()<5)? getSquad().size():5);
				else
					return null;
				
				/**
				 * FORMA 2
				 * 
				 * List<Player> lineup = new ArrayList<Player>();
				
					for(int i = 0;  getSquad()!=null && i<5; i++) {
						lineup.add(getSquad().get(i));
					}
				
					return lineup;
				 * 
				 */
	}
	
	public Player getPlayerByNumber(int number) throws TeamException{
		if(number<0) throw new TeamException(MSG_GET_PLAYER_INCORRECT_INPUT_ERROR);
		
		for(var player:getSquad()) {
			if(player.getNumber() == number) return player;
		}
		
		throw new TeamException(MSG_GET_PLAYER_NOT_EXISTS_ERROR);
	}
	
	public void addPlayer(Player player) throws TeamException{
		
		if(squad.size()<MAX_PLAYERS && player!=null) {
			if(!squad.contains(player)) {
				if(player.getTeam()!=null && player.getTeam()!=this) {	
					//Le quitamos el equipo actual al jugador, si no esta en el mismo equipo al que le queremos asignar					
					player.getTeam().removePlayer(player);
					player.setTeam(this);//Le asignamos el nuevo equipo
				}
				squad.add(player);
			}else {
				throw new TeamException(MSG_ADD_PLAYER_EXISTS_ERROR);
			}
		}else {
			throw new TeamException(MSG_ADD_PLAYER_MAX_ERROR);
		}		
	}
		
	public double getSpeedAverage() {
		//TODO
	}
	
	public double getDefenseAverage() {
		//TODO
	}
	
	public double getJumpAverage() {
		//TODO
	}
	
	public double getEnergyAverage() {
		//TODO
	}
	
	public double getFtAverage() {
		//TODO
	}
	
	public double getFg2pAverage() {
		//TODO
	}
	
	public double getFg3pAverage() {
		//TODO
	}
	
	public double getReboundsAverage() {
		//TODO
	}
	
	public double getAssistsAverage() {
		//TODO
	}
	
	public double getAverage() {
		return (getSpeedAverage()+getDefenseAverage()+getJumpAverage()+getEnergyAverage()+getFtAverage()+getFg2pAverage()+getFg3pAverage()+getReboundsAverage()+getAssistsAverage())/9;
	}
	
	public void removePlayer(Player player) throws TeamException{
		if(getSquad()==null || getSquad().size()==MIN_PLAYERS) {
			throw new TeamException(MSG_REMOVE_PLAYER_MIN_ERROR);
		}else{
			if(!squad.remove(player)) {
				throw new TeamException(MSG_REMOVE_PLAYER_NOT_EXISTS_ERROR);
			}else {
				player.setTeam(null); //Al jugador le quitamos el equipo
			}
		}		
	}	
	
	private void setupCoaches() throws Exception {
		coaches.add(new CoachAssists("Carlos", "Cabellero", "Carlillo", LocalDate.parse("01/01/1978",DateTimeFormatter.ofPattern("dd/MM/yyyy")),Country.ES, 600000, this, 1));
		coaches.add(new CoachDefense("Artur", "Dinaret", "McArthur", LocalDate.parse("01/01/1978",DateTimeFormatter.ofPattern("dd/MM/yyyy")),Country.ES, 600000, this, 3));
		coaches.add(new CoachFg2p("Joan Francesc", "Muñoz", "Cesco", LocalDate.parse("01/01/1978",DateTimeFormatter.ofPattern("dd/MM/yyyy")),Country.ES, 600000, this, 4));
		coaches.add(new CoachFg3p("Albert", "Almà", "AA", LocalDate.parse("01/01/1978",DateTimeFormatter.ofPattern("dd/MM/yyyy")),Country.ES, 600000, this, 1));
		coaches.add(new CoachFt("Sonia", "Matamoros", "Mrs. Xtrem", LocalDate.parse("01/01/1978",DateTimeFormatter.ofPattern("dd/MM/yyyy")),Country.ES, 600000, this, 2));
		coaches.add(new CoachRebounds("Marta", "Tarrés", "MT", LocalDate.parse("01/01/1978",DateTimeFormatter.ofPattern("dd/MM/yyyy")),Country.ES, 600000, this, 2));
	}
	
	public List<Coach> getCoaches(){
		return coaches;
	}
	
	public void assignPlayer2Coach(Player player, Coach coach) throws Exception {
		coach.addTraining(player);
	}
	
	public List<Player> getPlayersWithoutCoach(){
		//TODO
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		
		if(this == obj) return true;
				
		//TODO
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(getLongName()+" ("+getShortName()+") | "+getLeague());
		
		str.append("\nFounded: "+getFounded());
		str.append("\nPresident: "+getPresident());
		str.append("\nMembers: "+String.format("%,d",getMembers()));
		str.append("\nBudget: "+String.format("%,d",getBudget())+ " ptas.");
		str.append("\nSponsor: "+getSponsor());
		str.append("\nStadium: "+getStadium());
		str.append("\nSquad: ");
		
		for(Player player : getSquad().stream().sorted((p1,p2)-> p1.getNumber()-p2.getNumber()).collect(Collectors.toList())) {
			str.append("\n\t"+player);
		}
		
		return str.toString();
	}
	
	@Override
	public Team clone() {
		//TODO	
	}
}
