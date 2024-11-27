package com.example.demo.dto;


public class CreateUserDto {

    private String name; 
    private String email;    
    private String password; 
    private int age; 


    public CreateUserDto() {}

    public CreateUserDto(String name, String email, String password, int age) {
        this.name = name;
        this.email = email;      
        this.age = age;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email; 
    }

    public void setEmail(String email) {
        this.email = email; 
    }
    public int getAge() {
        return age; 
    }
    public void setAge(int age) {
        this.age = age; 
    }

    public String getPassword() {
        return password; 
    }

    public void setPassword(String password) {
        this.password = password; 
    }

    @Override
    public String toString() {
        return "CreateUserDto{" +
                "name='" + name + "'" +  
                ", email='" + email + "'" +
                ", password='" + password + "'" +
                ",age = '" + age + "'" +
                "}";
    }
    
}

