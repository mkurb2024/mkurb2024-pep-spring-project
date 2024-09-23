package com.example.service;

import com.example.entity.Message;
import com.example.exception.*;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        if (message.getPostedBy() == null || !messageRepository.existsByPostedBy(message.getPostedBy())) {
            throw new InvalidInputException("User does not exist.");
        }
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new InvalidInputException("Message text is invalid.");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new InvalidInputException("Message not found."));
    }

    public ResponseEntity<String> deleteMessageById(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return ResponseEntity.ok("1"); // Returning "1" to indicate one row was deleted
        }
        return ResponseEntity.ok(""); // Returning empty response for idempotency
    }


    public int updateMessage(Integer messageId, String messageText) {
        Message message = getMessageById(messageId);
        if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
            throw new InvalidInputException("Invalid message text.");
        }
        message.setMessageText(messageText);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
