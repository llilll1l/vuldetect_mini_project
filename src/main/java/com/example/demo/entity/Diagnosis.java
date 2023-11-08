package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Diagnosis {

    @Id @GeneratedValue
    private Long id;

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
   // @JoinColumn(name = "project_id")
   private Project project;

    @JsonManagedReference
    @OneToMany(mappedBy = "diagnosis", orphanRemoval = true)
    //@JoinColumn(name = "diagnosis_id")
    private List<DiagnosisItem> diagnosisItemList = new ArrayList<>();

    private int result;

    private int round;

    //private LocalDateTime diagonosisTime;

    public void saveRound() {
        this.round = project.getRound();
    }

    public int calResult() {
        double a = 0; // 분자
        double b = 0; // 분모

        for (DiagnosisItem item :
                this.diagnosisItemList) {
            if(item.getResult() == "양호"){
                a += item.getAzrDescription().getVulnerableLevel();
                b += item.getAzrDescription().getVulnerableLevel();
            }else {
                b += item.getAzrDescription().getVulnerableLevel();
            }
        }
        //return result = (int) ((a/b) * 100);
        return result = (int) Math.round( a/b * 100);
    }
}
