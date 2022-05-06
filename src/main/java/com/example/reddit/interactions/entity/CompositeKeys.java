package com.example.reddit.interactions.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompositeKeys implements Serializable {

  @Column(
    name = "user_id",
    nullable = false,
    insertable = false,
    updatable = false
  )
  private Long userId;

  @Column(
    name = "post_id",
    nullable = false,
    insertable = false,
    updatable = false
  )
  private Long postId;
}
