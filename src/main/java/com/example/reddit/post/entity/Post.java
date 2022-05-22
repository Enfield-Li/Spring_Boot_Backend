package com.example.reddit.post.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.example.reddit.interactions.entity.Interactions;
import com.example.reddit.mapper.source.homePost.PostInfoWithInteractions;
import com.example.reddit.mapper.source.homePost.PostInfoWithoutInteractions;
import com.example.reddit.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

/* 
帖子实体（Post Entity）：
+-----------------+--------------+------+-----+---------+----------------+
| Field           | Type         | Null | Key | Default | Extra          |
+-----------------+--------------+------+-----+---------+----------------+
| id              | bigint       | NO   | PRI | NULL    | auto_increment |
| comment_amounts | int          | YES  |     | 0       |                |
| confused_points | int          | YES  |     | 0       |                |
| content         | longtext     | YES  |     | NULL    |                |
| created_at      | datetime(6)  | YES  |     | NULL    |                |
| laugh_points    | int          | YES  |     | 0       |                |
| like_points     | int          | YES  |     | 0       |                |
| title           | varchar(255) | NO   | UNI | NULL    |                |
| updated_at      | datetime(6)  | YES  |     | NULL    |                |
| user_id         | bigint       | YES  | MUL | NULL    |                |
| view_count      | int          | YES  |     | 0       |                |
| vote_points     | int          | YES  |     | 0       |                |
+-----------------+--------------+------+-----+---------+----------------+

例子（Example）：
+----+-----------------+-----------------+-------------+----------------------------+--------------+-------------+----------+----------------------------+---------+------------+-------------+
| id | comment_amounts | confused_points | content     | created_at                 | laugh_points | like_points | title    | updated_at                 | user_id | view_count | vote_points |
+----+-----------------+-----------------+-------------+----------------------------+--------------+-------------+----------+----------------------------+---------+------------+-------------+
|  1 |               0 |            NULL |  lorum ip.. | 2021-01-01 21:53:10.000000 |            6 |          46 | Mr. Nice | 2020-07-17 05:37:04.000000 |       5 |        862 |          60 |
+----+-----------------+-----------------+-------------+----------------------------+--------------+-------------+----------+----------------------------+---------+------------+-------------+
 */

@Data
@Entity
@DynamicInsert
@SqlResultSetMapping(
  name = "HomePostWithoutInteractions", // 对应EM名称 / EntityManager creation name
  classes = {
    @ConstructorResult(
      targetClass = PostInfoWithoutInteractions.class, // 目标类 / Target class
      columns = {
        @ColumnResult(name = "id", type = Long.class),
        @ColumnResult(name = "username"),
        @ColumnResult(name = "postId", type = Long.class),
        @ColumnResult(name = "postCreatedAt", type = Instant.class),
        @ColumnResult(name = "postUpdatedAt", type = Instant.class),
        @ColumnResult(name = "title"),
        @ColumnResult(name = "content"),
        @ColumnResult(name = "view_Count"),
        @ColumnResult(name = "vote_points"),
        @ColumnResult(name = "like_points"),
        @ColumnResult(name = "confused_points"),
        @ColumnResult(name = "laugh_points"),
        @ColumnResult(name = "comment_amounts"),
      }
    ),
  }
)
@SqlResultSetMapping(
  name = "HomePostWithInteractions", // EntityManager creation name
  classes = {
    @ConstructorResult(
      targetClass = PostInfoWithInteractions.class, // Target class
      columns = {
        @ColumnResult(name = "id", type = Long.class),
        @ColumnResult(name = "username"),
        @ColumnResult(name = "postId", type = Long.class),
        @ColumnResult(name = "postCreatedAt", type = Instant.class),
        @ColumnResult(name = "postUpdatedAt", type = Instant.class),
        @ColumnResult(name = "title"),
        @ColumnResult(name = "content"),
        @ColumnResult(name = "view_Count"),
        @ColumnResult(name = "vote_points"),
        @ColumnResult(name = "like_points"),
        @ColumnResult(name = "confused_points"),
        @ColumnResult(name = "laugh_points"),
        @ColumnResult(name = "comment_amounts"),
        @ColumnResult(name = "vote_status"),
        @ColumnResult(name = "like_status"),
        @ColumnResult(name = "laugh_status"),
        @ColumnResult(name = "confused_status"),
      }
    ),
  }
)
public class Post {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "created_at")
  @CreationTimestamp
  private Instant createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private Instant updatedAt;

  @Column(nullable = false, unique = true)
  private String title;

  @Lob
  @Column(nullable = true, length = 512)
  private String content;

  @ColumnDefault(value = "0")
  private Integer viewCount;

  @ColumnDefault(value = "0")
  private Integer votePoints;

  @ColumnDefault(value = "0")
  private Integer likePoints;

  @ColumnDefault(value = "0")
  private Integer confusedPoints;

  @ColumnDefault(value = "0")
  private Integer laughPoints;

  @ColumnDefault(value = "0")
  private Integer commentAmounts;

  @Column(name = "user_id", insertable = false, updatable = false)
  private Long userId;

  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties("post")
  private User user;

  @OneToMany(
    mappedBy = "post",
    cascade = ALL,
    orphanRemoval = true,
    targetEntity = Interactions.class,
    fetch = LAZY
  )
  @JsonIgnoreProperties("post")
  private List<Interactions> interactions = new ArrayList<>();

  public Post() {}

  public Post(String title) {
    this.title = title;
  }

  public Post(
    String title,
    String content,
    User user,
    Integer likePoints,
    Integer votePoints
  ) {
    this.title = title;
    this.content = content;
    this.user = user;
    this.likePoints = likePoints;
    this.votePoints = votePoints;
  }

  public static Post of(String title, String content, User user) {
    return new Post(title, content, user, 1, 1);
  }
}
