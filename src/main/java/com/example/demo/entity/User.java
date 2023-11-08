package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String password;

    /*
    @OneToMany
    @JoinColumn(name = "user_id")
    //한 유저는 여러개의 프로젝트를 가지고 있다.
    private List<Project> projects = new ArrayList<>();


     */

}
