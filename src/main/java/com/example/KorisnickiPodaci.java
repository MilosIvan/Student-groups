package com.example;

import java.io.Serializable;

public record KorisnickiPodaci(String usernameInput, String passwordInput) implements Serializable {
    static boolean admin;

    public void setAdmin(boolean admin) {
        KorisnickiPodaci.admin = admin;
    }
    public boolean admin() {
        return admin;
    }
}
