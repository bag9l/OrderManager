package com.example.ordermanager.model;

public enum Role {
    CLIENT("CLIENT"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
