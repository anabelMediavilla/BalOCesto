package edu.uoc.baluocesto.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Coach extends Person{
	private static final long serialVersionUID = 1L;
	
	private final String MSG_ERR_NUM_PLAYERS_INVALID ="[ERROR] Number of players that a coach can train cannot be 0 or negative!!";
	private final String MSG_ERR_MAX_PLAYERS ="[ERROR] You cannot assign more players to this coach!!";
	private final String MSG_ERR_PLAYER_ALREADY_TRAINED = "[ERROR] This player is already being trained!!";
	
	private int maxNumPlayers2Train;
	
	
	protected Coach(String name, String surname, String nick, LocalDate birthdate, 
			  Country country, int salary, Team team, int maxNumPlayers2Train) throws Exception{
		
		super(name,surname,nick,birthdate,country,salary,0,0,team);
		setMaxNumPlayers2Train(maxNumPlayers2Train);
		
	}
	
	public int getMaxNumPlayers2Train() {
		return maxNumPlayers2Train;
	}

	private void setMaxNumPlayers2Train(int maxNumPlayers2Train) throws Exception{
		if(maxNumPlayers2Train<=0) {
			throw new Exception(MSG_ERR_NUM_PLAYERS_INVALID);
		}else {
			this.maxNumPlayers2Train = maxNumPlayers2Train;
		}
	}
	
	public void addTraining(Player player) throws Exception{
		//TODO
	}
	
	public void removeTraining(Player player) {
		//TODO
	}
	
	public boolean isPlayerTrained(Player player) {
		//TODO
	}
	
	public List<Training> getTrainings() {
		return trainings;
	}
	
	public int getNumTrainings() {
		return trainings.size();
	}

	public abstract void train();
	
	@Override
	public String toString() {
		return getSurname()+", "+getName()+" ("+getNumTrainings()+"/"+getMaxNumPlayers2Train()+")";
	}
}
