package com.example.studentmanagementusingsqlite;

import android.provider.BaseColumns;

public class Student {
    private String id;
    private String name;
    private String datebirth;
    private String email;
    private String address;

    public Student() {

    }

    public Student(String id, String name, String datebirth, String email, String address) {
        this.id = id;
        this.name = name;
        this.datebirth = datebirth;
        this.email = email;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatebirth() {
        return datebirth;
    }

    public void setDatebirth(String datebirth) {
        this.datebirth = datebirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}