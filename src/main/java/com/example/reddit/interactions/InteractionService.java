package com.example.reddit.interactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteractionService {

  InteractionsRepository interactionsRepository;

  @Autowired
  InteractionService(InteractionsRepository interactionsRepository) {
    this.interactionsRepository = interactionsRepository;
  }
}
