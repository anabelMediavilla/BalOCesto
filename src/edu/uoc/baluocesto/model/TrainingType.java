package edu.uoc.baluocesto.model;

public enum TrainingType {
	ASSISTS("Assists"),
	DEFENSE("Defense"),	
	FG2P("Fg2p"),
	FG3P("Fg3p"),
	FT("Ft"),
	REBOUNDS("Rebounds");
	
	private String description;
	
	private TrainingType(String description) {
		this.description = description;				
	}
	
	@Override
	public String toString() {
		return description;
	}
}
