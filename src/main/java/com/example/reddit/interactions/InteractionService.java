package com.example.reddit.interactions;

import com.example.reddit.interactions.entity.CompositeKeys;
import com.example.reddit.interactions.entity.Interactions;
import com.example.reddit.post.PostRepository;
import com.example.reddit.post.entity.Post;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InteractionService {

  private final InteractionsRepository interactionsRepository;
  private final PostRepository postRepository;
  private final EntityManager em;

  @Autowired
  InteractionService(
    InteractionsRepository interactionsRepository,
    PostRepository postRepository,
    EntityManager em
  ) {
    this.postRepository = postRepository;
    this.interactionsRepository = interactionsRepository;
    this.em = em;
  }

  @Transactional
  public Boolean interact(
    Long postId,
    Long meId,
    String field,
    Boolean boolValue
  ) {
    // Initial values to be inserted/updated
    String fieldStatus = field + "_status";
    String fieldPoints = field + "_points";
    Integer intValue = boolValue ? 1 : -1;

    Optional<Interactions> interactionOptional = interactionsRepository.findById(
      CompositeKeys.of(meId, postId)
    );

    /*
     *  User has no previous record, therefore create record
     */
    if (!interactionOptional.isPresent()) {
      int insertRes = em
        .createNativeQuery(
          "INSERT INTO interactions (post_id, user_id, " +
          fieldStatus + // Couldn't use setParameter, weird
          ")" +
          " VALUES (:postId, :userId, :value)"
        )
        .setParameter("postId", postId)
        .setParameter("userId", meId)
        .setParameter("value", boolValue)
        .executeUpdate();

      int updatePointRes = em
        .createNativeQuery(
          "UPDATE post SET " +
          fieldPoints +
          " = " +
          fieldPoints +
          " + :value WHERE id = :postId"
        )
        .setParameter("postId", postId)
        .setParameter("value", intValue)
        .executeUpdate();

      return insertRes == 1 && updatePointRes == 1 ? true : false;
    }

    /*
     *  User has previous record, therefore update record
     */
    Interactions interactions = interactionOptional.get();

    // Modify values to be updated on vote
    if (field.equals("vote")) {
      if (
        interactions.getVoteStatus() != boolValue &&
        interactions.getVoteStatus() != null
      ) {
        intValue = intValue * 2;
      }

      if (interactions.getVoteStatus() == boolValue) {
        boolValue = null;
        intValue = -intValue;
      }
    }

    // Modify values to be updated on other fields
    if (field.equals("like")) {
      intValue = interactions.getLikeStatus() == boolValue ? -1 : 1;
      if (interactions.getLikeStatus() == boolValue) boolValue = false;
    }
    if (field.equals("laugh")) {
      intValue = interactions.getLaughStatus() == boolValue ? -1 : 1;
      if (interactions.getLaughStatus() == boolValue) boolValue = false;
    }
    if (field.equals("confused")) {
      intValue = interactions.getConfusedStatus() == boolValue ? -1 : 1;
      if (interactions.getConfusedStatus() == boolValue) boolValue = false;
    }

    // Update fields status
    int updateStatusRes = em
      .createNativeQuery(
        "UPDATE interactions SET " +
        fieldStatus + // Couldn't use parameter
        " = :value" +
        " WHERE post_id = :postId AND user_id = :userId"
      )
      .setParameter("postId", postId)
      .setParameter("userId", meId)
      .setParameter("value", boolValue)
      .executeUpdate();

    // Update field points
    int updatePointRes = em
      .createNativeQuery(
        "UPDATE post SET " +
        fieldPoints +
        " = " +
        fieldPoints +
        " + :value WHERE id = :postId"
      )
      .setParameter("postId", postId)
      .setParameter("value", intValue)
      .executeUpdate();

    return updatePointRes == 1 && updateStatusRes == 1 ? true : false;
  }

  @Transactional
  public void testCase() {}
}
