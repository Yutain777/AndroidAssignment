package com.example.mymoviememoir.dbentity;

import android.widget.DatePicker;

import java.util.Date;

public class Credentials {
    private Long credentialsId;
    private String username;
    private String passportHash;
    private String signupDate;
    private Person personId;
    public Credentials() {
    }

    public Credentials(Long credentialsId) {
        this.credentialsId = credentialsId;
    }

    public Credentials(Long credentialsId, String username, String passportHash, String signupDate) {
        this.credentialsId = credentialsId;
        this.username = username;
        this.passportHash = passportHash;
        this.signupDate = signupDate;

    }

    public Long getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Long credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassportHash() {
        return passportHash;
    }

    public void setPassportHash(String passportHash) {
        this.passportHash = passportHash;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
    }

    public Long getPersonId() {
        return personId.getPersonId();
    }

    public void setPersonId(Long id) {
        personId = new Person(id);
    }
}
