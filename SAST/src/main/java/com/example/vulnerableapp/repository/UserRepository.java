package com.example.vulnerableapp.repository;

import com.example.vulnerableapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    // Vulnerability: SQL Injection in native query
    @Query(value = "SELECT * FROM user WHERE username = :username", nativeQuery = true)
    User findUserByUsername(@Param("username") String username);
    
    // Vulnerability: SQL Injection in native query
    @Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
    User findUserByEmail(String email);
    
    // Vulnerability: SQL Injection in native query with concatenation
    @Query(value = "SELECT * FROM user WHERE last_name LIKE '%' || :lastName || '%'", nativeQuery = true)
    List<User> findUsersByLastNameContaining(@Param("lastName") String lastName);
}
