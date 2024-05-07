package com.balashovmaksim.taco.tacocloud.service;

import com.balashovmaksim.taco.tacocloud.model.TacoOrder;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitOrderMessagingService implements OrderMessagingService {
    private final RabbitTemplate rabbitTemplate;
    @Override
    public void sendOrder(TacoOrder tacoOrder) {
        rabbitTemplate.convertAndSend("tacoshop.order.queue", tacoOrder,
            new MessagePostProcessor(){
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties properties = message.getMessageProperties();
                    properties.setHeader("X_ORDER_SOURCE","WEB");
                    return message;
                }
            }
        );
    }
}
