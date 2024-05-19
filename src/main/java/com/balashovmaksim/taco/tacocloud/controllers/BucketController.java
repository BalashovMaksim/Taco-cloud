package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.model.Bucket;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import com.balashovmaksim.taco.tacocloud.model.User;
import com.balashovmaksim.taco.tacocloud.repository.UserRepository;
import com.balashovmaksim.taco.tacocloud.service.BucketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/bucket")
@SessionAttributes({"bucket", "tacoOrder"})
@RequiredArgsConstructor
public class BucketController {
    private final UserRepository userRepository;
    private final BucketService bucketService;

    @ModelAttribute("bucket")
    public Bucket bucket() {
        return new Bucket();
    }

    @ModelAttribute("tacoOrder")
    public TacoOrder tacoOrder() {
        return new TacoOrder();
    }

    @GetMapping
    public String showBucket(@ModelAttribute("bucket") Bucket bucket, Model model, Principal principal) {
        if (principal == null || principal.getName() == null) {
            model.addAttribute("bucket", bucket);
        } else {
            User user = userRepository.findByUsername(principal.getName())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            Bucket userBucket = bucketService.findByUser(user);
            if (userBucket == null) {
                userBucket = bucketService.createBucketForUser(user);
            }
            userBucket.updateTotalPrice();
            model.addAttribute("bucket", userBucket);
        }

        return "bucket";
    }
}