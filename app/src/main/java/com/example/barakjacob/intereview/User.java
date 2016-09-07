package com.example.barakjacob.intereview;

/**
 * A Singleton class that represents a certain User
 * consists of the full name of the user, and the user's email address
 */
public class User {
    //Singleton
    private static User instance;
    private String fullName;
    private String email;

    /**
     * Gets the only instance of User, or calling the constructor with default parameters if null
     * @return the only instance of the User singleton
     */
    public static User getInstance(){
        if(instance==null){
            instance = new User("","");
        }
        return instance;
    }

    /**
     * Overloaded method
     * Gets the instance of User, or calling the constructor with certain name and email if its the first time its called
     * @param name the User's full name
     * @param mail the User's email address
     * @return the only instance of the User singleton
     */
    public static User getInstance(String name,String mail){
        if(instance==null){
            instance = new User(name,mail);
        }
        return instance;
    }

    /**
     * Constructs a User object with a certain name and email address
     * @param name the User's name
     * @param mail the user's Name
     */
    private User(String name,String mail){
        this.fullName = name.trim();
        this.email = mail.trim();
    }

    /**
     * Gets the first name out of a full name string
     * @return the first name of the User
     */
    public String getFirstName(){
        String toReturn = "";
        if(fullName.length()>0)
            toReturn += (""+fullName.charAt(0)).toUpperCase();
        if(fullName.indexOf(" ") != -1 && fullName.length()>1)
            toReturn += fullName.substring(1,fullName.indexOf(" ")).toLowerCase();
        return toReturn;
    }

    /**
     * Gets the User's email address
     * @return the User's email
     */
    public String getEmail(){
        return email;
    }





}
