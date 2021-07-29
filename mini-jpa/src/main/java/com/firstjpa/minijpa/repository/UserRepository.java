package com.firstjpa.minijpa.repository;

import com.firstjpa.minijpa.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    //회원가입
    public void save(User user) {
        em.persist(user);
    }

    //중복된 아디값 찾기//
    public Long findByUserIdOverlap(String userId) {
        Long cnt = em.createQuery("select count (m) from User m where m.userId =: userId", Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
        return cnt;
    }

    //로그인
    public List<User> findByUserId(String userId, String password) throws javax.persistence.NoResultException {
        List<User> user = em.createQuery(
                        "select m from User m where m.userId =: userId and m.password =: password", User.class)
                .setParameter("userId", userId)
                .setParameter("password", password)
                .getResultList();
        return user;
    }
}
