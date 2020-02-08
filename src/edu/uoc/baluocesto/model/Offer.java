package edu.uoc.baluocesto.model;

import java.io.Serializable;
/**
 * 
 * An offer created by the user
 * 
 */
public class Offer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Player player;
	private Contract contract; //contrato que le ofrecemos
	private int offerTeam;
	private Team team; //equipo que hace la oferta
	private int daysToRespond;
	
	private final String MSG_ERR_VALUE_CANNOT_BE_NEGATIVE = "[ERROR] This value cannot be negative!!";
	
	public Offer(Player player, int offerTeam, int cancellationClause, int salary, int contractYears, Team team) throws Exception{
		setPlayer(player);
		setOfferTeam(offerTeam);
		contract = new Contract(salary, cancellationClause, contractYears);
		setTeam(team);
		setDaysToRespond((int)((Math.random()*((3-1)+1))+1)); //entre 1 y 3 dias en responder a la oferta
	}
	
	private void setPlayer(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Contract getContract() {
		return contract;
	}
		
	
	public int getOfferTeam() {
		return offerTeam;
	}

	public void setOfferTeam(int offerTeam) throws Exception{
		if(offerTeam<0) {
			throw new Exception(MSG_ERR_VALUE_CANNOT_BE_NEGATIVE);
		}
		this.offerTeam = offerTeam;
	}
	
	public Team getTeam() {
		return team;
	}

	private void setTeam(Team team) {
		this.team = team;
	}
	
	public int getDaysToRespond() {
		return daysToRespond;
	}

	private void setDaysToRespond(int daysToRespond) throws Exception{
		if(daysToRespond<0) {
			throw new Exception(MSG_ERR_VALUE_CANNOT_BE_NEGATIVE);
		}
		this.daysToRespond = daysToRespond;
	}
	
	public void decreaseDaysToRespond() throws Exception{
		 setDaysToRespond(getDaysToRespond()-1);
	}

	@Override
	public boolean equals(Object obj) {
		Offer offer = (Offer) obj;
		return (this.getPlayer().equals(offer.player) && this.getTeam().equals(offer.getTeam()));
	}	
}
