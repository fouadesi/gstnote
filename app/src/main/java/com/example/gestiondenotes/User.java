package com.example.gestiondenotes;

public class User {
    String name;

    String email;

    public void setName(String name) {
        this.name = name;
    }

    User(String name, String email) {
        this.name = name;

        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

}
