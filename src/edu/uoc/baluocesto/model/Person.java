package edu.uoc.baluocesto.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Person implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name, surname, nick;	
	private LocalDate birthdate;
	
	private Contract contract;
	
	private Country country;
	
	private Team team;
	
	public Person(String name, String surname, String nick, LocalDate birthdate, 
				  Country country, int salary, int cancellationClause, int contractYears, Team team) throws Exception {
		
		setName(name);
		setSurname(surname);
		setNick(nick);
		setBirthdate(birthdate);
		setCountry(country);
		contract = new Contract(salary,cancellationClause,contractYears);		
		setTeam(team);		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
		
	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
		
	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
		
	public Team getTeam() {
		return team;
	}
	
	public Contract getContract() {
		return contract;
	}	
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(getName()+" "+getSurname());
		
		if(getNick()!=null) str.append(" '"+getNick()+"'");
		
		str.append(" | "+getBirthdate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))+" | "+getCountry()+" | "+getTeam().getShortName());
		
		return str.toString();
		
	}
}
