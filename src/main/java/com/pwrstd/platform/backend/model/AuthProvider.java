package com.pwrstd.platform.backend.model;

public enum AuthProvider {
    email("email"),
    github("github");


    private final String name;

    private AuthProvider(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

}
