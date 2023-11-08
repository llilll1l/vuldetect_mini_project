package com.example.demo.repository;

import com.example.demo.entity.Project;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepository {

    private final EntityManager em;

    public void save(Project project) {em.persist(project);}

    public void remove(Project project) {em.remove(project);}

    public void update(Project project) {em.merge(project);}



    public Project findOne(String name) {
        return em.createQuery("select p from Project p where p.name = :name", Project.class)
                .setParameter("name", name)
                .getSingleResult();
    }
    public Project findById(Long id) {
        return em.createQuery("select p from Project p where p.id = :id", Project.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Project> findAll() {
        return em.createQuery("select p from Project p", Project.class)
                .getResultList();
    }



}
