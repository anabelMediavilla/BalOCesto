package edu.uoc.baluocesto.model;

import java.io.Serializable;
import java.util.Formatter;

public class WeeklyCash implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int ticketIncome, billboardIncome, tvIncome, transferIncome;
	private int squadExpenditure, staffExpenditure, transferExpenditure;
	
	public WeeklyCash() {
		setTicketIncome(0);
		setBillboardIncome(0);
		setTvIncome(0);
		setTransferIncome(0);
		setSquadExpenditure(0);
		setStaffExpenditure(0);
		setTransferExpenditure(0);
	}
	
	public WeeklyCash(int ticketIncome, int billboardIncome, int tvIncome, int transferIncome, int squadExpenditure,
			int staffExpenditure, int transferExpenditure) {
		setTicketIncome(ticketIncome);
		setBillboardIncome(billboardIncome);
		setTvIncome(tvIncome);
		setTransferIncome(transferIncome);
		setSquadExpenditure(squadExpenditure);
		setStaffExpenditure(staffExpenditure);
		setTransferExpenditure(transferExpenditure);
	}
	public int getTicketIncome() {
		return ticketIncome;
	}
	public void setTicketIncome(int ticketIncome) {
		this.ticketIncome = ticketIncome;
	}
	public int getBillboardIncome() {
		return billboardIncome;
	}
	public void setBillboardIncome(int billboardIncome) {
		this.billboardIncome = billboardIncome;
	}
	public int getTvIncome() {
		return tvIncome;
	}
	public void setTvIncome(int tvIncome) {
		this.tvIncome = tvIncome;
	}
	public int getTransferIncome() {
		return transferIncome;
	}
	public void setTransferIncome(int transferIncome) {
		this.transferIncome = transferIncome;
	}
	public int getSquadExpenditure() {
		return squadExpenditure;
	}
	public void setSquadExpenditure(int squadExpenditure) {
		this.squadExpenditure = squadExpenditure;
	}
	public int getStaffExpenditure() {
		return staffExpenditure;
	}
	public void setStaffExpenditure(int staffExpenditure) {
		this.staffExpenditure = staffExpenditure;
	}
	public int getTransferExpenditure() {
		return transferExpenditure;
	}
	public void setTransferExpenditure(int transferExpenditure) {
		this.transferExpenditure = transferExpenditure;
	}
	
	public int getIncomes() {
		return getTicketIncome() + getBillboardIncome() + getTvIncome() + getTransferIncome();
	}
	
	public int getExpenditures() {
		return getSquadExpenditure()+ getStaffExpenditure() + getTransferExpenditure();
	}
	
	public int getWeekBalance() {
		return getIncomes()-getExpenditures();
	}
	
	@Override
	public String toString() {
		Formatter formatter = new Formatter();
		formatter.format("%s%n", "---------------------------------------------------------------------------------------------------------------------------");
		formatter.format("|%-15s| %10s %10s %10s %15s %15s %15s |%22s|%n","Concept","Tickets","Billboards","Television","Transfer","Squad","Staff","Total");
		formatter.format("%s%n", "---------------------------------------------------------------------------------------------------------------------------");
		formatter.format("|%-15s| %,10d %,10d %,10d %,15d %15s %15s | %,15d ptas.|%n","Incomes",getTicketIncome(),getBillboardIncome(),getTvIncome(),getTransferIncome(),"-","-",getIncomes());
		formatter.format("|%-15s| %10s %10s %10s %,15d %,15d %,15d | %,15d ptas.|%n","Expenditures","-","-","-",getTransferExpenditure(),getSquadExpenditure(),getStaffExpenditure(),getExpenditures());
		formatter.format("%s%n", "---------------------------------------------------------------------------------------------------------------------------");
		formatter.format("%100s %,15d ptas.|%n", "Balance|",getWeekBalance());
		formatter.format("%s%n", "---------------------------------------------------------------------------------------------------------------------------");
		String text = formatter.toString();
		formatter.close();
		return text;
	}
}
