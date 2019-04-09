package com.vasilgmarkov.blog.blog.repositories;

import com.vasilgmarkov.blog.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
