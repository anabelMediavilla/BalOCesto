package edu.uoc.baluocesto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Player extends Person implements Serializable{
	private static final long serialVersionUID = 1L;

	private int number, height, numInternational, injuredWeeks;
	private double weight;	
	private Position position;
	
	private String imageSrc;
			
	private final String MSG_NUM_INTERNATIONAL_ERROR = "[ERROR] Num. international must be equal or greater than 0!!";
	private final String MSG_INJURED_WEEKS_ERROR = "[ERROR] Weeks that a player is injured must be equal or greater than 0!!";
	
	public Player(String name, String surname, String nick, LocalDate birthdate, Country country, int salary, int cancellationClause, int contractYears, Team team, 
			int number, int height, double weight, int numInternational, int speed, int defense, int jump, int energy, int ft, int fg2p, int fg3p, int rebounds, 
			int assists, Position position, String imageSrc) throws Exception{
		
		super(name, surname, nick, birthdate, country, salary, cancellationClause, contractYears, team);		
		setNumber(number);
		setHeight(height);
		setWeight(weight);	
		setNumInternational(numInternational);
		setPosition(position);
		setInjuriedWeeks(0);
		setImageSrc(imageSrc);
	}
		
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) throws Exception{
		if(height<100) throw new Exception("[ERROR] Height must be equal or greater than 100 cm!!");
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) throws Exception{
		if(weight<30) throw new Exception("[ERROR] Weight must be equal or greater than 30 kg!!");
		this.weight = weight;
	}	
		
	public int getNumInternational() {		
		return numInternational;
	}
	
	public void setNumInternational(int numInternational) throws Exception{
		if(numInternational<0) throw new Exception(MSG_NUM_INTERNATIONAL_ERROR);
		this.numInternational = numInternational;
	}
			
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public boolean isInjured() {
		return (getInjuredWeeks()>0);
	}

	
	public int getInjuredWeeks() {
		return injuredWeeks;
	}
	
	public void setInjuriedWeeks(int injuredWeeks) throws Exception{
		if(injuredWeeks<0) throw new Exception(MSG_INJURED_WEEKS_ERROR);
		this.injuredWeeks = injuredWeeks;
	}
	
	public String getImageSrc() {
		return imageSrc;
	}
	
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) return false;
		
		if(this == obj) return true;
				
		//TODO
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(getNumber()+". "+getName()+" "+getSurname()+" | "+getTeam().getShortName()+" | ");
		
		str.append((getAttributes().getHeight()/(double)100)+" m. | "+getAttributes().getWeight()+" kg. | "+getNumInternational()+" | ");
		
		str.append(getAttributes().getSpeed()+" | "+getAttributes().getDefense()+" | "+getAttributes().getJump()+" | "+getAttributes().getEnergy()+" | "+getAttributes().getFt()+" | "+getAttributes().getFg2p()+" | "+getAttributes().getFg3p()+" | "+getAttributes().getRebounds()+" | "+getAttributes().getAssists()+" | ");
		
		str.append(getPosition()+" | ");
		
		str.append((getContract().getSalary()/100000000)+" | "+(getContract().getCancellationClause()/100000000)+" | "+getContract().getContractYears());
				
		return str.toString();
	}
	
	@Override
	public Player clone() {
		//TODO
	}
}