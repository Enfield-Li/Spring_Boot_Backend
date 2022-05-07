package com.example.reddit.interactions.entity;

import com.example.reddit.post.entity.Post;
import com.example.reddit.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@DynamicInsert
public class Interactions {

  @EmbeddedId
  private CompositeKeys CompositeKeys;

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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
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
