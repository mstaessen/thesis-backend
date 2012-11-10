package com.capgemini.expense.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType
public class Expense {
	private Date date;
	private String projectCode;
	private double amount;
	private String remarks;
	private String evidence;
	private int expenseTypeId;
	private int expenseLocationId;
	private String currency;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public int getExpenseTypeId() {
		return expenseTypeId;
	}

	public void setExpenseTypeId(int expenseTypeId) {
		this.expenseTypeId = expenseTypeId;
	}

	public int getExpenseLocationId() {
		return expenseLocationId;
	}

	public void setExpenseLocationId(int expenseLocationId) {
		this.expenseLocationId = expenseLocationId;
	}

	public Errors validate() {
		List<String> errors = new ArrayList<String>();
		if (date == null) {
			errors.add("no date filled in");
		}
		if (amount < 0) {
			errors.add("incorrect amount:" + amount);
		}
		if (evidence == null) {
			errors.add("no evidence");
		}

		if (currency == null || currency.length() != 3) {
			errors.add("invalid currency: " + currency + " expected currency: EUR, USD, ...");
		}
		try {
			ExpenseType.getById(expenseTypeId);
		} catch (IllegalArgumentException e) {
			errors.add("invalid expense type:" + expenseTypeId);
		}

		try {
			ExpenseType.getById(expenseLocationId);
		} catch (IllegalArgumentException e) {
			errors.add("invalid expenseLocation:" + expenseLocationId);
		}

		if (errors.size() > 0) {
			Errors errorsObject = new Errors();
			errorsObject.setErrors(errors);
			return errorsObject;
		}

		return null;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
