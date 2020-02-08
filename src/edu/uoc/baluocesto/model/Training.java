package edu.uoc.baluocesto.model;

import java.io.Serializable;

public class Training implements Serializable{
	private static final long serialVersionUID = 1L;
	
	int numWeeks;
	Player player;
	
	private final String MSG_ERR_NUM_WEEKS_NEGATIVE = "[ERROR] Number of weeks that a player has been training cannot be negative!!";
	
	public Training(Player player) throws Exception{
		setPlayer(player);
		setNumWeeks(0);
	}
	
	private void setPlayer(Player player) {
		//TODO
	}
	
	public Player getPlayer() {
		//TODO
	}
	
	private void setNumWeeks(int numWeeks) throws Exception{
		//TODO
	}
	
	public int getNumWeeks() {
		//TODO
	}
	
	public void increaseNumWeeks() throws Exception{
		//TODO
	}
	
	public void decreaseNumWeeks() throws Exception{
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
		return getPlayer().getNumber()+". "+getPlayer().getSurname()+", "+getPlayer().getName()+" ("+getPlayer().getPosition()+")";
	}
}
