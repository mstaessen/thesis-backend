package com.capgemini.expense.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.capgemini.expense.model.Employee;
import com.capgemini.expense.model.ExpenseForm;
import com.capgemini.expense.model.Status;

public class Store {

	private static Map<Integer, List<ExpenseForm>> expenseStore = new HashMap<Integer, List<ExpenseForm>>();
	private static int id = 0;
	private static Store instance = new Store();

	public List<ExpenseForm> getByEmployeeId(int id) {
		if (!expenseStore.containsKey(id)) {
			expenseStore.put(id, new ArrayList<ExpenseForm>());
		}
		return expenseStore.get(id);
	}

	/**
	 * singleton
	 */
	private Store() {

	}

	public static Store getInstance() {
		return instance;
	}

	public List<ExpenseForm> getAll() {
		List<ExpenseForm> results = new ArrayList<ExpenseForm>();
		Iterator<List<ExpenseForm>> it = expenseStore.values().iterator();

		while (it.hasNext()) {
			results.addAll(it.next());
		}
		return results;
	}

	public ExpenseForm getExpenseFormById(int expenseFormId) {
		List<ExpenseForm> results = getAll();
		for (ExpenseForm form : results) {
			if (form.getId() == expenseFormId) {
				return form;
			}
		}

		return null;
	}

	public void storeExpenseForm(ExpenseForm form, Employee employee) {
		form.setId(getNextId());
		form.setEmployeeId(employee.getId());
		form.setStatusId(Status.NEW.getId());
		if (expenseStore.containsKey(employee.getId())) {
			expenseStore.get(employee.getId()).add(form);
		} else {
			List<ExpenseForm> expenseFormList = new ArrayList<ExpenseForm>();
			expenseFormList.add(form);
			expenseStore.put(employee.getId(), expenseFormList);
		}
	}

	public void saveState(int expenseFormId, int statusId) {
		Iterator<List<ExpenseForm>> it = expenseStore.values().iterator();
		while (it.hasNext()) {
			List<ExpenseForm> subList = it.next();
			for (ExpenseForm form : subList) {
				if (form.getId() == expenseFormId) {
					form.setStatusId(Status.getById(statusId).getId());
					return;
				}
			}
		}
		throw new IllegalArgumentException("form not found");
	}

	private synchronized int getNextId() {
		id++;
		return id;
	}

}
