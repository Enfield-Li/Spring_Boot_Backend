package com.example.reddit.comments.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import com.example.reddit.post.entity.Post;
import com.example.reddit.user.entity.User;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
public class Comments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_at")
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column(nullable = false)
  private String comment_text;

  @ColumnDefault(value = "0")
  @Column(name = "reply_amount", nullable = true)
  private Integer replyAmount;

  @Column(name = "upvote_amount")
  @ColumnDefault(value = "0")
  private Integer upvoteAmount;

  @Column(name = "downvote_amount")
  @ColumnDefault(value = "0")
  private Integer downvoteAmount;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parent_comment_id", insertable = false, updatable = false)
  private Comments parentComment;

  @OneToMany(
    mappedBy = "parentComment",
    cascade = ALL,
    orphanRemoval = true,
    targetEntity = Comments.class,
    fetch = LAZY
  )
  // @JsonBackReference
  private List<Comments> replies = new ArrayList<>();

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", insertable = false, updatable = false)
  private Post post;
}
