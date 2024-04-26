package com.balashovmaksim.taco.tacocloud.repository;

import com.balashovmaksim.taco.tacocloud.model.Taco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacoRepository extends JpaRepository<Taco, Long> {
}
