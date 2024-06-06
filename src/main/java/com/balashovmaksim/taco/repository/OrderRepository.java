package com.balashovmaksim.taco.repository;

import com.balashovmaksim.taco.model.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<TacoOrder, Long> {
    List<TacoOrder> findByUserId(Long userId);
}
