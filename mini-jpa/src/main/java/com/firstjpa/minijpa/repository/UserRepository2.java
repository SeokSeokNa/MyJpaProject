package com.firstjpa.minijpa.repository;

import com.firstjpa.minijpa.domain.Board;
import com.firstjpa.minijpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository2 extends JpaRepository<User, Long> {


    List<User> findAll();

}
