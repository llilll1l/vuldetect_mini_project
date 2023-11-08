package com.example.demo.repository;

import com.example.demo.entity.Diagnosis;
import com.example.demo.entity.DiagnosisItem;
import com.example.demo.entity.Project;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiagnosisItemRepository {

    private final EntityManager em;
    public void save(DiagnosisItem diagnosisItem){
        em.persist(diagnosisItem);
    }
    public void remove(DiagnosisItem diagnosisItem) {em.remove(diagnosisItem);}

    public void update(DiagnosisItem diagnosisItem , Diagnosis diagnosis) {em.merge(diagnosis);}

    public List<DiagnosisItem> findAll (){
        return em.createQuery("select d from DiagnosisItem d", DiagnosisItem.class)
                .getResultList();
    }


    public DiagnosisItem findByName(String name) {
        return em.createQuery("select d from DiagnosisItem d where d.name = :name", DiagnosisItem.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<DiagnosisItem> findListByProjectIdAndRound(Project project, int round) {
        return em.createQuery("select d from DiagnosisItem d where d.project = :project and d.round = :round", DiagnosisItem.class)
                .setParameter("project", project)
                .setParameter("round", round)
                .getResultList();
    }

    public List<DiagnosisItem> findListByProjectId(Project project) {
        return em.createQuery("select d from DiagnosisItem d where d.project= :project", DiagnosisItem.class)
                .setParameter("project", project)
                .getResultList();
    }

}
