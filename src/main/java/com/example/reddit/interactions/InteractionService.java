package com.example.reddit.interactions;

import com.example.reddit.interactions.entity.CompositeKeys;
import com.example.reddit.interactions.entity.Interactions;
import com.example.reddit.interactions.repository.InteractionsMapper;
import com.example.reddit.interactions.repository.InteractionsRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InteractionService {

  private final InteractionsRepository interactionsRepo;
  private final InteractionsMapper interactionsMapper;

  @Autowired
  InteractionService(
    InteractionsRepository interactionsRepository,
    InteractionsMapper interactionMapper
  ) {
    this.interactionsRepo = interactionsRepository;
    this.interactionsMapper = interactionMapper;
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
    String fieldStatStr = field + "_status";
    String fieldPointsStr = field + "_points";
    Integer intValue = boolValue ? 1 : -1;

    Optional<Interactions> interactionOptional = interactionsRepo.findById(
      CompositeKeys.of(meId, postId)
    );

    /*
      用户先前无互动状态，创建新的互动
      User has no previous interactions, therefore create
     */
    if (!interactionOptional.isPresent()) {
      Integer insertRes = interactionsMapper.createInteractions(
        fieldStatStr,
        meId,
        postId,
        boolValue
      );

      Integer updatePointRes = interactionsMapper.updateFieldPoints(
        fieldPointsStr,
        postId,
        intValue
      );

      return insertRes == 1 && updatePointRes > 0 ? true : false;
    }

    /*
      用户先前有互动，更新互动状态
      User has previous interactions, therefore update
     */
    Interactions previousInteractions = interactionOptional.get();

    /* 
      修改字段vote相关的值
      Modify values to be updated on vote
     */
    if (field.equals("vote")) {
      Boolean changeVote =
        previousInteractions.getVoteStatus() != boolValue &&
        previousInteractions.getVoteStatus() != null;
      Boolean cancelVote = previousInteractions.getVoteStatus() == boolValue;

      if (changeVote) {
        intValue = intValue * 2;
      }

      if (cancelVote) {
        boolValue = null;
        intValue = -intValue;
      }
    }

    /* 
      修改其他字段相关的值
      Modify values to be updated on other fields
     */
    Boolean cancelPrevious = null;

    if (field.equals("like")) {
      cancelPrevious = previousInteractions.getLikeStatus() == boolValue;
    }

    if (field.equals("laugh")) {
      cancelPrevious = previousInteractions.getLaughStatus() == boolValue;
    }

    if (field.equals("confused")) {
      cancelPrevious = previousInteractions.getConfusedStatus() == boolValue;
    }

    if (cancelPrevious != null) {
      intValue = cancelPrevious ? -1 : 1;
      boolValue = cancelPrevious ? false : boolValue;
    }

    /* 
      更新互动状态
      Update interactions fields status
     */
    Integer updateStatusRes = interactionsMapper.updateFieldStatus(
      fieldStatStr,
      meId,
      postId,
      boolValue
    );

    /* 
      更新帖子点数
      Update post field points
     */
    Integer updatePointRes = interactionsMapper.updateFieldPoints(
      fieldPointsStr,
      postId,
      intValue
    );

    return updatePointRes == 1 && updateStatusRes > 0 ? true : false;
  }

  // Test wierd case
  @Transactional
  public Object testCase() {
    return null;
  }
}
