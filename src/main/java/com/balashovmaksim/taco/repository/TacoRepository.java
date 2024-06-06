package com.balashovmaksim.taco.repository;

import com.balashovmaksim.taco.model.Taco;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TacoRepository extends JpaRepository<Taco, Long> {
}
