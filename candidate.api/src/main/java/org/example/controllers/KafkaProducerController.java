package org.example.controllers;

import org.example.kafka.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, MessageDto> kafkaTemplate;

    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageDto message) {
        kafkaTemplate.send("my-topic", message);
        return "Message sent: " + message.getContent();
    }
}