package com.example.demo.repository;

import com.example.demo.entity.AZRDescription;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AZRDescriptionRepository {

    private final EntityManager em;

    public void save(AZRDescription azrDescription) {em.persist(azrDescription);}

    public AZRDescription findByName(String name) {
        return em.createQuery("select  a from AZRDescription a where a.name = :name", AZRDescription.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
