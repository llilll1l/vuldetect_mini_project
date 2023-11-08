package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Project {
    @Id @GeneratedValue
    @Column(name = "project_id")
    private Long id;

    private String name;

   @OneToOne
   @JoinColumn(name = "compliance_id")
   private  Compliance compliance;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;


   @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.REMOVE)
   private List<Diagnosis> diagnosisList = new ArrayList<>();


    @OneToMany(orphanRemoval = true, mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<DiagnosisItem> diagnosisItemList = new ArrayList<>() ;



    //@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    //private List<Diagnosis> diagnosisList = new ArrayList<>();

    private int round =0;

    public void incrementRound() {
        this.round ++;
    }

    public void createProject(Project project) {
       // project.setCompliance();

    }

}
