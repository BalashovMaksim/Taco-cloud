package com.balashovmaksim.taco.tacocloud.repository;

import com.balashovmaksim.taco.tacocloud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
