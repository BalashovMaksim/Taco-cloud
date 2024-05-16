package com.balashovmaksim.taco.tacocloud.repository;

import com.balashovmaksim.taco.tacocloud.model.Taco;
import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TacoRepository extends JpaRepository<Taco, Long> {
}
