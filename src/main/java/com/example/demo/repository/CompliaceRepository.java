package com.example.demo.repository;

import com.example.demo.entity.Compliance;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompliaceRepository {

    public  final EntityManager em;

    public void  save(Compliance compliance) {
        em.persist(compliance);
    }

    public Compliance fincById(Long id) {
        return em.find(Compliance.class, id);
    }

    public Compliance findByName(String name) {
        return em.createQuery("select c from Compliance c where c.name = :name", Compliance.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<Compliance> findAll() {
        return em.createQuery("select c from Compliance c", Compliance.class)
                .getResultList();
    }




}
