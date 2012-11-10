package com.capgemini.expense.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType
public class ExpenseRequest {
	private String token;
	private ExpenseForm expenseForm;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ExpenseForm getExpenseForm() {
		return expenseForm;
	}

	public void setExpenseForm(ExpenseForm expenseForm) {
		this.expenseForm = expenseForm;
	}

}
