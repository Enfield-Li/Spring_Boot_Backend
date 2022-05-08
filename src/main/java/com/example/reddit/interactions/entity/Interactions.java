package com.example.reddit.interactions.entity;

import static javax.persistence.FetchType.LAZY;

import com.example.reddit.post.entity.Post;
import com.example.reddit.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@DynamicInsert
public class Interactions {

  @EmbeddedId
  @JsonIgnore
  private CompositeKeys CompositeKeys;

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

  @Column(name = "created_at")
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column(name = "vote_status")
  private Boolean voteStatus;

  @Column(name = "like_status")
  private Boolean likeStatus;

  @Column(name = "laugh_status")
  private Boolean laughStatus;

  @Column(name = "confused_status")
  private Boolean confusedStatus;

  @Column(name = "have_read")
  @ColumnDefault(value = "false")
  private Boolean read;

  @Column(name = "have_checked")
  @ColumnDefault(value = "false")
  private Boolean checked;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", insertable = false, updatable = false)
  private Post post;

  public Interactions() {}

  public static Interactions of(
    CompositeKeys compositeKeys,
    Boolean voteStatus
  ) {
    return new Interactions(compositeKeys, voteStatus);
  }

  private Interactions(CompositeKeys compositeKeys, Boolean voteStatus) {
    this.CompositeKeys = compositeKeys;
    this.voteStatus = voteStatus;
  }
}