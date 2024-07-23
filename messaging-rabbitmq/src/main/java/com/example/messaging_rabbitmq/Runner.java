package com.example.messaging_rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {
    
    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;
    
    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }
    
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        List<String> messages = Arrays.asList("Oi", "tudo bem?", "Qual a sua idade?", "Qual o seu hobby?");
        messages.forEach(m -> rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.topicExchangeName, "foo.bar.baz", m));
        receiver.getLatch().await(1000, TimeUnit.MILLISECONDS);
    }
}
