package com.example.reddit.interactions;

import com.example.reddit.interactions.entity.CompositeKeys;
import com.example.reddit.interactions.entity.Interactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionsRepository
  extends JpaRepository<Interactions, CompositeKeys> {}
