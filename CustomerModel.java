package com.example.sqlitedatabasetutorial;

public class CustomerModel {

    private final int id;
    private String name;
    private int age;
    private boolean isActive;

    public CustomerModel(int id, String name, int age, boolean isActive){

        this.id = id;
        this.name = name;
        this.age = age;
        this.isActive = isActive;
    }

    //to string method is important for printing


    @Override
    public String toString() {
        return "CustomerModel{" +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                '}';
    }

    //getters and setters

    public int getId(){
        return id;
    }

    public CustomerModel(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
