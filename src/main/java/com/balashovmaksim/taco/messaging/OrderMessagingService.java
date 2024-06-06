package com.balashovmaksim.taco.messaging;

import com.balashovmaksim.taco.model.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder tacoOrder);
}
