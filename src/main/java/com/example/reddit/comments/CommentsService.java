package com.example.reddit.comments;

import com.example.reddit.comments.dto.request.CreateCommentOrReplyDto;
import com.example.reddit.comments.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CommentsService {

  private final CommentsRepository commentsRepository;

  @Autowired
  CommentsService(CommentsRepository commentsRepository) {
    this.commentsRepository = commentsRepository;
  }

  public void createCommentOrReply(
    CreateCommentOrReplyDto dto,
    Long meId,
    Long postId
  ) {}
}
