package com.balashovmaksim.taco.tacocloud.controllers;

import com.balashovmaksim.taco.tacocloud.model.Bucket;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
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

    private final BucketService bucketService;

    @ModelAttribute("bucket")
    public Bucket bucket() {
        return new Bucket();
    }

    @GetMapping
    public String showBucket(@ModelAttribute("bucket") Bucket bucket, Model model, Principal principal) {
        try {
            Bucket userBucket = bucketService.getUserBucket(bucket, principal.getName());
            model.addAttribute("bucket", userBucket);
        } catch (EntityNotFoundException e) {
            log.error("User not found: ", e);
            return "error";
        }
        return "bucket";
    }

    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("bucket") Bucket bucket, Model model) {
        if (bucket.getTacos() != null && !bucket.getTacos().isEmpty()) {
            TacoOrder tacoOrder = bucketService.checkout(bucket);
            model.addAttribute("tacoOrder", tacoOrder);
            model.addAttribute("bucket", bucket);
        } else {
            throw new EntityNotFoundException("Bucket not found");
        }
        return "redirect:/orders/current";
    }
}