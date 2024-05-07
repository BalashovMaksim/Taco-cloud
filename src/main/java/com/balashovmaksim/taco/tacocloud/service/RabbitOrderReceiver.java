package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.model.TacoOrder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitOrderReceiver {

    @RabbitListener(queues = "tacoshop.order.queue")
    public void processOrder(TacoOrder tacoOrder){
        System.out.println(tacoOrder);
    }
}
