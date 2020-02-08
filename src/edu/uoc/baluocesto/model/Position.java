package edu.uoc.baluocesto.model;

public enum Position {
	PG ("Point Guard", "PG"), //Base
	SG ("Shooting Guard","SG"), //Escolta
	SF ("Small Forward","SF"), //Alero
	PF ("Power Forward","PF"), //Ala-Pivot
	C ("Center","C"); //Pivot
	
	private String description, abbreviation;
	
	private Position(String description, String abbreviation) {
		this.description = description;
		this.abbreviation = abbreviation;				
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
}
