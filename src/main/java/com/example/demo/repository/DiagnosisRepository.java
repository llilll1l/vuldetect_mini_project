package com.example.demo.repository;

import com.example.demo.entity.Diagnosis;
import com.example.demo.entity.Project;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiagnosisRepository {

    private final EntityManager em;

    public void save(Diagnosis diagnosis){em.persist(diagnosis);}

    public void remove(Diagnosis diagnosis) {em.remove(diagnosis);}

    public Diagnosis findById(Long id) {return em.find(Diagnosis.class, id);}

    public List<Diagnosis> findAll() {
        return em.createQuery("select d from Diagnosis d", Diagnosis.class)
                .getResultList();
    }

    public List<Diagnosis> findByProjectId(Long projectId) {
        return em.createQuery("select d from Diagnosis d where d.project.id = :projectId", Diagnosis.class)
                .setParameter("projectId", projectId)
                .getResultList();
    }

    public List<Integer> findRoundByProjectId(Long id) {
        return em.createQuery("select d.round from Diagnosis d where d.project.id = :projectId", Integer.class)
                .setParameter("projectId", id)
                .getResultList();
    }

    public int findDiagnosisResultByProjectIdAndRound(Project project, int round){
        return em.createQuery("select d.result from Diagnosis d where d.project =:project and d.round = :round", Integer.class)
                .setParameter("project", project)
                .setParameter("round", round)
                .getSingleResult();
    }

    public Diagnosis findDiagnosisByProjectIdAndRound(Project project, int round){
        return em.createQuery("select d from Diagnosis d where d.project =:project and d.round = :round", Diagnosis.class)
                .setParameter("project", project)
                .setParameter("round", round)
                .getSingleResult();
    }


    //어떤 프로젝트의 어떤 round 값 인지..?
}
