package com.balashovmaksim.taco.repository;

import com.balashovmaksim.taco.model.Bucket;
import com.balashovmaksim.taco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
    Optional<Bucket> findByUser(User user);
}
