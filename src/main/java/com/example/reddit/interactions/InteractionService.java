package com.example.reddit.interactions;

import com.example.reddit.interactions.entity.CompositeKeys;
import com.example.reddit.interactions.entity.Interactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InteractionService {

  private final InteractionsRepository interactionsRepository;

  @Autowired
  InteractionService(InteractionsRepository interactionsRepository) {
    this.interactionsRepository = interactionsRepository;
  }

  public Boolean interact(Long postId, Long userId, Boolean value) {
    try {
      Interactions interaction = Interactions.of(
        CompositeKeys.of(userId, postId),
        value
      );

      interactionsRepository.save(interaction);

      return true;
    } catch (Exception e) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Post not found"
      );
    }
  }
}
