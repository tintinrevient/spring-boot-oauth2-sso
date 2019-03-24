package com.hncy.platform.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hncy.platform.auth.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);
}