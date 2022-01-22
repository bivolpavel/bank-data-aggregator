package com.paj.psd2.aggregator.utils;

public enum Currency {
    EUR("EUR");

    private String name;

    Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
