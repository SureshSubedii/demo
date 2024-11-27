package com.example.demo.model;


import com.example.demo.dto.CreateUserDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    
    private String name;  
    

   
    private String email;
    
    private int age;  

    private String password;  

    
    public User() {}
    
    public User(CreateUserDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.age = dto.getAge();
        this.password =  dto.getPassword();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password =  password;
    }
    public void setAge(int age) {
        this.age = age;
    }
}

