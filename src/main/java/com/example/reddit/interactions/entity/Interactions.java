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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

/* 
互动状态实体：
Interactions Entity:
+-----------------+-------------+------+-----+---------+-------+
| Field           | Type        | Null | Key | Default | Extra |
+-----------------+-------------+------+-----+---------+-------+
| post_id         | bigint      | NO   | PRI | NULL    |       |
| user_id         | bigint      | NO   | PRI | NULL    |       |
| have_checked    | bit(1)      | YES  |     | b'0'    |       |
| confused_status | bit(1)      | YES  |     | NULL    |       |
| created_at      | datetime(6) | YES  |     | NULL    |       |
| laugh_status    | bit(1)      | YES  |     | NULL    |       |
| like_status     | bit(1)      | YES  |     | NULL    |       |
| have_read       | bit(1)      | YES  |     | b'0'    |       |
| updated_at      | datetime(6) | YES  |     | NULL    |       |
| vote_status     | bit(1)      | YES  |     | NULL    |       |
+-----------------+-------------+------+-----+---------+-------+

例子：
Example: 
+---------+---------+----------------------------+----------------------------------+------------+----------------------------+--------------------------+----------------------+------------+----------------------+
| post_id | user_id | have_checked               | confused_status                  | created_at | laugh_status               | like_status              | have_read            | updated_at | vote_status          |
+---------+---------+----------------------------+----------------------------------+------------+----------------------------+--------------------------+----------------------+------------+----------------------+
|       3 |       1 | 0x00                       | 0x01                             | NULL       | NULL                       | NULL                     | 0x00                 | NULL       | NULL                 |
+---------+---------+----------------------------+----------------------------------+------------+----------------------------+--------------------------+----------------------+------------+----------------------+
 */

@Data
@Entity
@DynamicInsert
public class Interactions {

  @EmbeddedId
  @JsonIgnore
  private CompositeKeys compositeKeys;

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
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", insertable = false, updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Post post;

  public Interactions() {}

  public Interactions(
    CompositeKeys compositeKeys,
    Boolean voteStatus,
    Boolean likeStatus
  ) {
    this.compositeKeys = compositeKeys;
    this.voteStatus = voteStatus;
    this.likeStatus = likeStatus;
  }

  public static Interactions ofCreation(CompositeKeys compositeKeys) {
    return new Interactions(compositeKeys, true, true);
  }
}
