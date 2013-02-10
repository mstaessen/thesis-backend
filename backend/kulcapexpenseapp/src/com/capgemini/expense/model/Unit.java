package com.capgemini.expense.model;

public enum Unit {
    G20(1), G21(2), G22(3), G23(4), G30(5), G31(6), G32(7), G33(8), G34(9), G35(10);

    private int id;

    private Unit(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Unit getById(int id) {
        Unit[] values = Unit.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].id == id) {
                return values[i];
            }
        }

        throw new IllegalArgumentException("id not found:" + id);
    }

}
