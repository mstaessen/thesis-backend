package com.capgemini.expense.model;

import java.util.Date;

public class ExpenseContext {

	private Employee employee;
	private Date lastAccess;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public ExpenseContext(Employee employee, Date lastAccess) {
		super();
		this.employee = employee;
		this.lastAccess = lastAccess;
	}

}
