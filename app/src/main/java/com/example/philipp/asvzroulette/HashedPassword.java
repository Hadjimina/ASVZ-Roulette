package com.example.philipp.asvzroulette;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by david on 16.12.16.
 * This class represents the password hash and the salt which is added. Hashing is done with
 *SHA-256.
 */

public class HashedPassword {

    private String hashedPassword;
    final String salt;

    public HashedPassword(String password){
        MessageDigest md = null;

        Random rm = new Random();
        char[] charSalt = new char[10];
        for (int i = 0; i<10; i++){
            charSalt[i] = ((char) (rm.nextInt(25)+65));
        }
        salt = new String(charSalt);
        password.concat(salt);

        try {
            md = MessageDigest.getInstance("SHA-256");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password.getBytes());
        hashedPassword = Arrays.toString(md.digest());


    }

    public HashedPassword(String password, String salt){
        MessageDigest md = null;
        this.salt = salt;
        password.concat(salt);

        try {
            md = MessageDigest.getInstance("SHA-256");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password.getBytes());
        hashedPassword = Arrays.toString(md.digest());
    }

    public String getHashedPassword(){
        return this.hashedPassword;
    }


}
