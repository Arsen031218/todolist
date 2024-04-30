package com.nttdata.ta.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    User getById(Long id);
}