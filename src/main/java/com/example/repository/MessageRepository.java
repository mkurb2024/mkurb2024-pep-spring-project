package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    List<Message> findByPostedBy(Integer accountId);

    boolean existsByPostedBy(Integer postedBy);
}

