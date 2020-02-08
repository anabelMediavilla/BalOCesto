package edu.uoc.baluocesto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Finance implements Serializable{

	private static final long serialVersionUID = 1L;

	private Team team;
	
	private int ticketPrice, billboardPrice, 
				tvIncome, ticketIncome, billboardIncome, transferIncome, 
				fireExpenditure, transferExpenditure,
				initialLoan, currentLoan, loanWeeks;
	
	private List<WeeklyCash> cashArchive;
	
	private final int INTEREST_RATE, TICKET_PRICE_STEP, BILLBOARD_PRICE_STEP, LOAN_STEP, MAX_WEEKS_LOAN_RETURN, NUM_WEEKS_SEASON;
	
	private final String MSG_ERR_TICKET_PRICE_NEGATIVE = "[ERROR] Ticket price cannot be negative!!";
	private final String MSG_ERR_BILLBOARD_PRICE_NEGATIVE = "[ERROR] Billboard price cannot be negative!!";
	private final String MSG_ERR_LOAN_MINIMUM = "[ERROR] The minimum value of a loan is ";
	private final String MSG_ERR_MAX_WEEKS_LOAN_RETURN = "[ERROR] The maximum weeks for returning a loan is ";
	private final String MSG_ERR_MIN_WEEKS_LOAN_RETURN = "[ERROR] The minimum weeks for returning a loan is 1";
	private final String MSG_ERR_VALUE_CANNOT_BE_NEGATIVE = "[ERROR] The value cannot be negative!!";
	
	public Finance(Team team, int numSeasonWeeks) throws Exception{
		setTeam(team);		
		cashArchive = new ArrayList<WeeklyCash>(numSeasonWeeks);
		TICKET_PRICE_STEP = 500;
		BILLBOARD_PRICE_STEP = 100000;
		LOAN_STEP = 100000;
		INTEREST_RATE = 12;
		MAX_WEEKS_LOAN_RETURN = numSeasonWeeks;
		NUM_WEEKS_SEASON = numSeasonWeeks;
		reset();
	}
	
	public void reset() throws Exception{
		setTicketIncome(0);
		setBillboardIncome(0);
		setTransferIncome(0);
		setTransferExpenditure(0);		
		setFireExpenditure(0);
	}
	
	private void setTeam(Team team) {
		this.team = team;
	}
	
	private Team getTeam() {
		return team;
	}
	
	public int getTicketPriceStep(){
		return TICKET_PRICE_STEP;
	}
	
	public void setTicketIncome(int ticketIncome) throws Exception{
		if(ticketIncome<0) {
			throw new Exception(MSG_ERR_VALUE_CANNOT_BE_NEGATIVE);
		}
		this.ticketIncome = ticketIncome;
	}
	
	public int getTicketIncome() {
		return ticketIncome;
	}
	
	public int getTicketPrice() {
		return ticketPrice;
	}
	
	public void increaseTicketPrice() {
		setTicketPrice(getTicketPrice()+TICKET_PRICE_STEP);
	}
	
	public void decreaseTicketPrice() throws Exception{
		if(getTicketPrice()-TICKET_PRICE_STEP<0) {
			throw new Exception(MSG_ERR_TICKET_PRICE_NEGATIVE);
		}		
		setTicketPrice(getTicketPrice()-TICKET_PRICE_STEP);
	}

	private void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public int getBillboardPriceStep(){
		return BILLBOARD_PRICE_STEP;
	}
	
	public void setBillboardIncome(int billboardIncome) throws Exception{
		if(billboardIncome<0) {
			throw new Exception(MSG_ERR_VALUE_CANNOT_BE_NEGATIVE);
		}
		this.billboardIncome = billboardIncome;
	}
	
	public int getBillboardIncome() {
		return billboardIncome;
	}
	
	public int getBillboardPrice() {
		return billboardPrice;
	}
	
	public void increaseBillboardPrice() {
		setBillboardPrice(getBillboardPrice()+BILLBOARD_PRICE_STEP);
	}
	
	public void decreaseBillboardPrice() throws Exception{
		if(getBillboardPrice()-BILLBOARD_PRICE_STEP<0) {
			throw new Exception(MSG_ERR_BILLBOARD_PRICE_NEGATIVE);
		}		
		setBillboardPrice(getBillboardPrice()-BILLBOARD_PRICE_STEP);
	}

	private void setBillboardPrice(int billboardPrice) {
		this.billboardPrice = billboardPrice;
	}
	
	public void setTvIncome(int tvIncome) throws Exception{
		if(tvIncome<0) {
			throw new Exception(MSG_ERR_VALUE_CANNOT_BE_NEGATIVE);
		}
		this.tvIncome = tvIncome;
	}
	
	public int getTvIncome() {
		return tvIncome;
	}
	
	public void setTransferIncome(int transferIncome) throws Exception{
		if(transferIncome<0) {
			throw new Exception(MSG_ERR_VALUE_CANNOT_BE_NEGATIVE);
		}
		this.transferIncome = transferIncome;
	}
	
	public int getFireExpenditure() {
		return fireExpenditure;
	}
	
	public int getTransferIncome() {
		return transferIncome;
	}
		
	public void setFireExpenditure(int fireExpenditure) throws Exception{
		if(fireExpenditure<0) {
			throw new Exception(MSG_ERR_VALUE_CANNOT_BE_NEGATIVE);
		}
		this.fireExpenditure = fireExpenditure;
	}
	
	public int getTransferExpenditure() {
		return transferExpenditure;
	}
	
	public void setTransferExpenditure(int transferExpenditure) throws Exception{
		if(transferExpenditure<0) {
			throw new Exception(MSG_ERR_VALUE_CANNOT_BE_NEGATIVE);
		}
		this.transferExpenditure = transferExpenditure;
	}
	
	public int getInitialLoan() {
		return initialLoan;
	}

	public void setInitialLoan(int initialLoan) {
		this.initialLoan = initialLoan;
		setCurrentLoan(initialLoan);
	}

	public int getCurrentLoan() {
		return currentLoan;
	}

	public void increaseCurrentLoan() {
		setCurrentLoan(getCurrentLoan()+LOAN_STEP);
	}
	
	public void decreaseCurrentLoan() throws Exception{
		if(getCurrentLoan()-LOAN_STEP<LOAN_STEP) {
			throw new Exception(MSG_ERR_LOAN_MINIMUM+LOAN_STEP);
		}
		setCurrentLoan(getCurrentLoan()-LOAN_STEP);
	}
	
	private void setCurrentLoan(int currentLoan) {
		this.currentLoan = currentLoan;
	}

	public int getLoanWeeks() {
		return loanWeeks;
	}

	private void setLoanWeeks(int loanWeeks) {
		this.loanWeeks = loanWeeks;
	}
	
	public void increaseLoanWeeks() throws Exception{
		if(getLoanWeeks()+1>getMaxWeeksLoanReturn()){
			throw new Exception(MSG_ERR_MAX_WEEKS_LOAN_RETURN+getMaxWeeksLoanReturn());
		}
		setLoanWeeks(getLoanWeeks()+1);
	}
	
	public void decreaseLoanWeeks() throws Exception{
		if(getLoanWeeks()-1<1){
			throw new Exception(MSG_ERR_MIN_WEEKS_LOAN_RETURN);
		}
		setLoanWeeks(getLoanWeeks()-1);
	}
	
	public int getMaxWeeksLoanReturn(){
		return MAX_WEEKS_LOAN_RETURN;
	}
	
	public int getInterestRate() {
		return INTEREST_RATE;
	}
	
	public int getWeeklyInterest() {
		return (getCurrentLoan()*INTEREST_RATE)/100;
	}
	
	/**
	 * 
	 * @return Weekly return payment according to initial loan and number of weeks to return it.
	 */
	public int getWeeklyReturnPayment() {
		return getInitialLoan()/getLoanWeeks();
	}
	/**
	 * 
	 * @return Interest + Return fee
	 */
	public int getWeeklyLoanCost() {
		return getWeeklyInterest()+getWeeklyReturnPayment();
	}
	
	public void addWeeklyCash() {
		/*addWeeklyCash(new WeeklyCash(getTicketIncome(),getBillboardIncome(),getTvIncome(),getTransferIncome(), getSquadExpenditure(),
				getStaffExpenditure(), getTransferExpenditure()));
		*/
		addWeeklyCash(new WeeklyCash());
	}
	
	private void addWeeklyCash(WeeklyCash weeklyCash) {
		cashArchive.add(weeklyCash);
	}
	
	public WeeklyCash getWeeklyCash(int week) throws IndexOutOfBoundsException{
		return cashArchive.get(week);
	}
	
	public void updateWeeklyCash() throws Exception{
		WeeklyCash weeklyCash = getWeeklyCash(cashArchive.size()-1);
		weeklyCash.setTicketIncome(getTicketIncome());
		weeklyCash.setBillboardIncome(getBillboardIncome());
		weeklyCash.setTvIncome(getTvIncome());
		weeklyCash.setTransferIncome(getTransferIncome());
		weeklyCash.setSquadExpenditure(getSquadExpenditure());
		weeklyCash.setStaffExpenditure(getStaffExpenditure());
		weeklyCash.setTransferExpenditure(getTransferExpenditure());
		updateBudget();
	}
	
	private void updateBudget() throws Exception{
		getTeam().setBudget(getTeam().getBudget()+cashArchive.stream().mapToInt(weeklyCash -> weeklyCash.getWeekBalance()).sum());
	}
	
	private int getSquadExpenditure(){
		int squadExpenditure = getTeam().getSquad().stream().mapToInt(player -> player.getContract().getSalary()).sum()/NUM_WEEKS_SEASON;		
		squadExpenditure += getFireExpenditure();
		return squadExpenditure; 
	}
	
	private int getStaffExpenditure() {
		return (getTeam().getCoaches().stream().mapToInt(coach -> coach.getContract().getSalary()).sum()/NUM_WEEKS_SEASON);
	}
	
}