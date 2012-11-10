package com.capgemini.expense.model;

public enum Status {
	NEW(1), VERIFIED(2), APPROVED(3), PAIDOUT(4), DISAPPROVED(5);

	private int id;

	private Status(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static Status getById(int id) {
		Status[] values = Status.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].id == id) {
				return values[i];
			}
		}

		throw new IllegalArgumentException("id not found:" + id);
	}
}
