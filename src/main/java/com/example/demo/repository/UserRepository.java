package com.example.demo.repository;

import com.example.demo.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //component scan에 의해 자동으로 bean에 등록
@RequiredArgsConstructor
public class UserRepository {

    //@PersistenceContext //엔터티와 데이터베이스 간의 상호 작용을 관리하는 데 사용, EntityManager를 빈으로 주입할 때 사용
    private final EntityManager em;

    //유저 정보 저장
    public void save(User user) {
        em.persist(user);
    }


    public void remove(User user){em.remove(user);}

    public void update(User user){em.refresh(user);}


    //유저 조회
    public User findbyId(Long id) {
        return em.find(User.class, id); //조회할 엔티티의 primary key 값을 전달받음
    }


    //모든 유저 조회
    //JPQL 사용하려면 entity maneger를 주입해야한다.
    //entity 객체에 대한 쿼리를 한다.
    //조회된 결과를 User class 객체로 변환
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public User findOne(Long id) {
        return em.createQuery("select u from User u where u.id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    //parameter binding으로 특정 회원 이름만 검색
    public List<User> findByName(String name) {
        return em.createQuery("select  u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

}
