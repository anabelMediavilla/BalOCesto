package edu.uoc.baluocesto.model;

import java.io.Serializable;

public class Contract implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int salary, cancellationClause, contractYears;
	
	private final String MSG_SALARY_ERROR = "[ERROR] Salary must be equal or greater than 0!!";
	private final String MSG_CANCELLATION_CLAUSE_ERROR = "[ERROR] Cancellation clause must be equal or greater than 0!!";
	private final String MSG_CONTRACT_YEARS_ERROR = "[ERROR] Contract years must be equal or greater than 0!!";
	
	public Contract(int salary, int cancellationClause, int contractYears) throws Exception{
		setSalary(salary);
		setCancellationClause(cancellationClause);
		setContractYears(contractYears);
	}
	
	public int getSalary() {
		return salary;
	}
		
	public void setSalary(int salary) throws Exception{
		if(salary<0) throw new Exception(MSG_SALARY_ERROR);
		this.salary = salary;
	}
	
	public int getCancellationClause() {
		return cancellationClause;
	}
	
	public void setCancellationClause(int cancellationClause) throws Exception{
		if(cancellationClause<0) throw new Exception(MSG_CANCELLATION_CLAUSE_ERROR);
		this.cancellationClause = cancellationClause;
	}
	
	public int getContractYears() {
		return contractYears;
	}
	
	public void setContractYears(int contractYears) throws Exception{
		if(contractYears<0) throw new Exception(MSG_CONTRACT_YEARS_ERROR);
		this.contractYears = contractYears;
	}
}
