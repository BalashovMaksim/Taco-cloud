package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.model.Bucket;
import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.BucketRepository;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BucketService {
    private final BucketRepository bucketRepository;
    private final UserRepository userRepository;

    @Transactional
    public Bucket findByUser(User user) {
        return bucketRepository.findByUser(user).orElse(null);
    }

    @Transactional
    public Bucket createBucketForUser(User user) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        return bucketRepository.save(bucket);
    }

    @Transactional
    public void clearBucket(Bucket bucket, String username) {
        bucket.getTacos().clear();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        bucket.setUser(user);
        bucketRepository.save(bucket);
    }
}