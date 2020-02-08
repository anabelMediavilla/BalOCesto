package edu.uoc.baluocesto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Stadium implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name, address;
	private int capacity, parkingLots;
	private LocalDate created;
	private String location;
	
	private Set<Team> teams;
	
	private final String MSG_CAPACITY_ERROR = "[ERROR] Capacity must be greater than 0!!";
	private final String MSG_PARKINGLOTS_ERROR = "[ERROR] Parking lots must be greater or equal than 0!!";
	
	
	public Stadium(String name, String address, String location, int capacity, int parkingLots, LocalDate created) throws Exception{
		setName(name);
		setAddress(address);
		setLocation(location);
		setCapacity(capacity);
		setParkingLots(parkingLots);
		setCreated(created);
		teams = new HashSet<Team>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) throws Exception{
		//TODO
	}
	
	public int getParkingLots() {
		return parkingLots;
	}
	
	public void setParkingLots(int parkingLots) throws Exception{
		//TODO
	}
	
	public LocalDate getCreated() {
		return created;
	}
	
	public void setCreated(LocalDate created) {
		this.created = created;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public boolean addTeam(Team team) {
		return teams.add(team);
	}
	
	public boolean removeTeam(Team team) {
		return teams.remove(team);
	}
	
	public Set<Team> getTeams(){
		return teams;
	}

	@Override
	public String toString() {
		//TODO
	}	
	
	@Override 
	public Stadium clone() {
		Stadium stadiumClone = null;
		try{
			stadiumClone = new Stadium(getName(), getAddress(), getLocation(), getCapacity(), getParkingLots(), getCreated());
		
			for(var team : getTeams()) {
				stadiumClone.addTeam(team.clone());
			}			
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return stadiumClone;
	}
}
