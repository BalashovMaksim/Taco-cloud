package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.model.Bucket;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.BucketRepository;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class BucketService {
    private final BucketRepository bucketRepository;
    private final UserRepository userRepository;

    @Transactional
    public Bucket getUserBucket(Bucket sessionBucket, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Bucket userBucket = findByUser(user);
        if (userBucket == null) {
            userBucket = createBucketForUser(user);
        } else {
            userBucket = sessionBucket;
        }
        userBucket.updateTotalPrice();
        return userBucket;
    }

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
    public void clearBucket(Bucket bucket) {
        bucket.getTacos().clear();
        bucketRepository.save(bucket);
    }

    @Transactional
    public TacoOrder checkout(Bucket bucket) {
        TacoOrder tacoOrder = new TacoOrder();
        tacoOrder.setTacos(new ArrayList<>(bucket.getTacos()));
        tacoOrder.updateTotalPrice();

        clearBucket(bucket);

        return tacoOrder;
    }
}