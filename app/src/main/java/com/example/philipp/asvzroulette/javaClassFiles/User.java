package com.example.philipp.asvzroulette.javaClassFiles;

import com.example.philipp.asvzroulette.authenticator.HashedPassword;

public class User {

    private String forename;
    private String surname;
    private String email;
    private HashedPassword password;
    private long userID;

    //default constructor
    public User(){

    }

    //constructor
    public User(String forename, String surname, String email, HashedPassword password){
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public void setUserID(long ID){
        this.userID = ID;
    }

    public long getUserID(){
        return this.userID;
    }
    public String getForename(){
        return this.forename;
    }
    public String getSurname(){
        return this.surname;
    }
    public HashedPassword getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }


}
