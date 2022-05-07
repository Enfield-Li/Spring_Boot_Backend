package com.example.reddit.interactions.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

  private CompositeKeys(Long userId, Long postId) {
    this.userId = userId;
    this.postId = postId;
  }

  public static CompositeKeys of(Long userId, Long postId) {
    return new CompositeKeys(userId, postId);
  }
}
