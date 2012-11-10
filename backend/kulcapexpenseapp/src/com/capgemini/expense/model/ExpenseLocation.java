package com.capgemini.expense.model;

public enum ExpenseLocation {
	DOMESTIC(1), ABROAD(2);

	private int id;

	private ExpenseLocation(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static ExpenseLocation getById(int id) {
		ExpenseLocation[] values = ExpenseLocation.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].id == id) {
				return values[i];
			}
		}

		throw new IllegalArgumentException("id not found:" + id);
	}
}
