package com.example.reddit.interactions;

import com.example.reddit.interactions.entity.CompositeKeys;
import com.example.reddit.interactions.entity.Interactions;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InteractionService {

  private final InteractionsRepository interactionsRepo;
  private final EntityManager em;

  @Autowired
  InteractionService(
    InteractionsRepository interactionsRepository,
    EntityManager em
  ) {
    this.interactionsRepo = interactionsRepository;
    this.em = em;
  }

  @Transactional
  public Boolean interact(
    Long postId,
    Long meId,
    String field,
    Boolean boolValue
  ) {
    /* 
      设置需要插入/更新的初始值 
      Set up initial values to be inserted/updated
     */
    String fieldStatus = field + "_status";
    String fieldPoints = field + "_points";
    Integer intValue = boolValue ? 1 : -1;

    Optional<Interactions> interactionOptional = interactionsRepo.findById(
      CompositeKeys.of(meId, postId)
    );

    /*
      用户先前无互动状态，创建新的互动
      User has no previous interactions, therefore create
     */
    if (!interactionOptional.isPresent()) {
      int insertRes = em
        .createNativeQuery(
          "INSERT INTO interactions (post_id, user_id, " +
          fieldStatus +
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
          " = IFNULL(" +
          fieldPoints +
          ", 0) + :intValue WHERE id = :postId"
        )
        .setParameter("postId", postId)
        .setParameter("intValue", intValue)
        .executeUpdate();

      return insertRes == 1 && updatePointRes == 1 ? true : false;
    }

    /*
      用户先前有互动，更新互动状态
      User has previous interactions, therefore update
     */
    Interactions interactions = interactionOptional.get();

    /* 
      修改用于更新字段vote相关的值
      Modify values to be updated on vote
     */
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

    /* 
      修改用于更新其他字段相关的值
      Modify values to be updated on other fields
     */
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

    /* 
      更新互动状态
      Update interactions fields status
     */
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

    /* 
      更新帖子点数
      Update post field points
     */
    int updatePointRes = em
      .createNativeQuery(
        "UPDATE post SET " +
        fieldPoints +
        " = IFNULL(" +
        fieldPoints +
        ", 0) + :intValue WHERE id = :postId"
      )
      .setParameter("postId", postId)
      .setParameter("intValue", intValue)
      .executeUpdate();

    return updatePointRes == 1 && updateStatusRes == 1 ? true : false;
  }

  // Test wierd case
  @Transactional
  public Object testCase() {
    String fieldStatus = "vote_points";
    Query q = em
      .createNativeQuery("select " + fieldStatus + " from post where id = :id")
      .setParameter("id", 1);

    System.out.println(q.getSingleResult());
    return q.getSingleResult();
  }
}
