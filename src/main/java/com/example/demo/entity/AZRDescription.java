package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AZRDescription {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int vulnerableLevel;

    private String vulnerableGrade;

    private String criteria;

    private String category;

    private String inspectionDescription;

     public void createAZRDescription (String name, int vulnerableLevel, String vulnerableGrade ,String criteria, String category, String inspectionDescription){
         this.name = name;
         this.vulnerableLevel = vulnerableLevel;
         this.vulnerableGrade = vulnerableGrade;
         this.criteria = criteria;
         this.category = category;
         this.inspectionDescription = inspectionDescription;
     }

}
