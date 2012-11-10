package com.capgemini.expense.model;

public enum ExpenseType {
	HOTEL(1), LUNCH(2), DINER(3), TICKET(4), RESTAURANT(5), OTHER(6);

	private int id;

	private ExpenseType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static ExpenseType getById(int id) {
		ExpenseType[] values = ExpenseType.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].id == id) {
				return values[i];
			}
		}

		throw new IllegalArgumentException("id not found:" + id);
	}

}
