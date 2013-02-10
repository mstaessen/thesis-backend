package com.capgemini.expense.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ExpenseForm {
    private int id;
    private Date date;
    private int statusId;
    private int employeeId;
    private String signature;
    private String remarks;
    private boolean notification;
    private List<Expense> expenses;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public Errors validate() {
        List<String> errors = new ArrayList<String>();
        if (signature == null) {
            errors.add("signature is not filled in");
        }
        if (date == null) {
            errors.add("date is not filled in");
        }

        if (expenses == null) {
            errors.add("no expenses");
        } else {
            for (Expense expense : expenses) {
                Errors expenseErrors = expense.validate();
                if (expenseErrors != null) {
                    errors.addAll(expenseErrors.getErrors());
                }
            }
        }

        if (errors.size() > 0) {
            Errors errorsObject = new Errors();
            errorsObject.setErrors(errors);
            return errorsObject;
        }

        return null;
    }

}
