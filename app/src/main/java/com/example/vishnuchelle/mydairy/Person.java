package com.example.vishnuchelle.mydairy;

/**
 * Created by Vishnu Chelle on 3/16/2015.
 */
public class Person {

    private String firstName;
    private String lastName;
    private String middleName;
    private String userName;
    private String phoneNumber;
    private int pin;
    private String email;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person(){

    }
    public Person(String firstName, String lastName, String middleName, String userName, String phoneNumber, int pin, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        this.email = email;
    }
}
