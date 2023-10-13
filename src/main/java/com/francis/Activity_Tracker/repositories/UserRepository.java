package com.francis.Activity_Tracker.repositories;

import com.francis.Activity_Tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findById( Long id);
    Optional<User> findByEmail( String email);
    Optional<User> deleteByIdAndEmail(Long id, String email);
}
