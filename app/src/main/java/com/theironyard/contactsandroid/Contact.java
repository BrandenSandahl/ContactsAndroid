package com.theironyard.contactsandroid;

/**
 * Created by branden on 2/17/16.
 */
public class Contact {

    private String name, phone;

    public Contact() {
    }

    public Contact(String name, String phone) {
        setName(name);
        setPhone(phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    @Override
    public String toString() {
        return String.format("%s (%s)", name, phone);
    }

}
