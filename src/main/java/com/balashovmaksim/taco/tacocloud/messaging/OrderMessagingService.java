package com.balashovmaksim.taco.tacocloud.messaging;

import com.balashovmaksim.taco.tacocloud.model.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder tacoOrder);
}
