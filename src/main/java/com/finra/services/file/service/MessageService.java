package com.finra.services.file.service;

import com.finra.services.file.domain.Message;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Jyothi Bolla
 */
@Singleton
@Service
public class MessageService {
    List<Message> messages = Collections.synchronizedList(new ArrayList<Message>());

    @PostConstruct
    public void init() {
        messages.add(new Message("Jyothi", "Hello Jersey"));
        messages.add(new Message("Bolla", "Spring boot is easy !"));
    }

    public List<Message> getMessages() {
        return messages;
    }
}
