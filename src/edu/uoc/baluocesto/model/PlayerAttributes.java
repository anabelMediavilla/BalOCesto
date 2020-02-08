package edu.uoc.baluocesto.model;

import java.io.Serializable;

public class PlayerAttributes implements Serializable{
	private static final long serialVersionUID = 1L;

	private int height, speed, defense, jump, energy, ft, fg2p, fg3p, rebounds, assists;

	private double weight;
	
	private final String MSG_HEIGHT_ERROR = "[ERROR] Height must be equal or greater than 100 cm!!";
	private final String MSG_WEIGHT_ERROR = "[ERROR] Weight must be equal or greater than 30 kg!!";
	private final String MSG_FEATURE_MAX_VALUE_ERROR = "cannot be greater than 100!!";
	private final String MSG_FEATURE_MIN_VALUE_ERROR = "must be equal or greater than 0!!";
	
	public PlayerAttributes(int height, double weight, int speed, int defense, int jump, int energy,
			int ft, int fg2p, int fg3p, int rebounds, int assists) throws Exception{
		
		setHeight(height);
		setWeight(weight);
		setSpeed(speed);
		setDefense(defense);
		setJump(jump);
		setEnergy(energy);
		setFt(ft);
		setFg2p(fg2p);
		setFg3p(fg3p);
		setRebounds(rebounds);
		setAssists(assists);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) throws Exception{
		if(height<100) throw new Exception(MSG_HEIGHT_ERROR);
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) throws Exception{
		if(weight<30) throw new Exception(MSG_WEIGHT_ERROR);
		this.weight = weight;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) throws Exception{
		if(speed<0) throw new Exception("[ERROR] Speed "+MSG_FEATURE_MIN_VALUE_ERROR);
		if(speed>100) throw new Exception("[ERROR] Speed "+MSG_FEATURE_MAX_VALUE_ERROR);
		this.speed = speed;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) throws Exception{
		if(defense<0) throw new Exception("[ERROR] Defense "+MSG_FEATURE_MIN_VALUE_ERROR);
		if(defense>100) throw new Exception("[ERROR] Defense "+MSG_FEATURE_MAX_VALUE_ERROR);
		this.defense = defense;
	}

	public int getJump() {
		return jump;
	}

	public void setJump(int jump) throws Exception{
		if(jump<0) throw new Exception("[ERROR] Jump "+MSG_FEATURE_MIN_VALUE_ERROR);
		if(jump>100) throw new Exception("[ERROR] Jump "+MSG_FEATURE_MAX_VALUE_ERROR);
		this.jump = jump;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) throws Exception{
		if(energy<0) throw new Exception("[ERROR] Energy "+MSG_FEATURE_MIN_VALUE_ERROR);
		if(energy>100) throw new Exception("[ERROR] Energy "+MSG_FEATURE_MAX_VALUE_ERROR);
		this.energy = energy;
	}

	public int getFt() {
		return ft;
	}

	public void setFt(int ft) throws Exception{
		if(ft<0) throw new Exception("[ERROR] FT "+MSG_FEATURE_MIN_VALUE_ERROR);
		if(ft>100) throw new Exception("[ERROR] FT "+MSG_FEATURE_MAX_VALUE_ERROR);
		this.ft = ft;
	}

	public int getFg2p() {
		return fg2p;
	}

	public void setFg2p(int fg2p) throws Exception{
		if(fg2p<0) throw new Exception("[ERROR] FG2P "+MSG_FEATURE_MIN_VALUE_ERROR);
		if(fg2p>100) throw new Exception("[ERROR] FG2P "+MSG_FEATURE_MAX_VALUE_ERROR);
		this.fg2p = fg2p;
	}

	public int getFg3p() {
		return fg3p;
	}

	public void setFg3p(int fg3p) throws Exception{
		if(fg3p<0) throw new Exception("[ERROR] FG3P "+MSG_FEATURE_MIN_VALUE_ERROR);
		if(fg3p>100) throw new Exception("[ERROR] FG3P "+MSG_FEATURE_MAX_VALUE_ERROR);
		this.fg3p = fg3p;
	}

	public int getRebounds() {
		return rebounds;
	}

	public void setRebounds(int rebounds) throws Exception{
		if(rebounds<0) throw new Exception("[ERROR] Rebounds "+MSG_FEATURE_MIN_VALUE_ERROR);
		if(rebounds>100) throw new Exception("[ERROR] Rebounds "+MSG_FEATURE_MAX_VALUE_ERROR);
		this.rebounds = rebounds;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) throws Exception{
		if(assists<0) throw new Exception("[ERROR] Assists "+MSG_FEATURE_MIN_VALUE_ERROR);
		if(assists>100) throw new Exception("[ERROR] Assists "+MSG_FEATURE_MAX_VALUE_ERROR);
		this.assists = assists;
	}
	
	public int getAverage() {
		return (getSpeed()+getDefense()+getJump()+getEnergy()+getFt()+getFg2p()+getFg3p()+getRebounds()+getAssists())/9;
	}
}

