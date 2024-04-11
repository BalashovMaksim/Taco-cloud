package com.balashovmaksim.taco.tacocloud.repository;

import com.balashovmaksim.taco.tacocloud.model.TacoOrder;

public interface OrderRepository {
    TacoOrder save (TacoOrder tacoOrder);
}
