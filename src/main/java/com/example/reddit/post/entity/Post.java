package com.example.reddit.post.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import com.example.reddit.interactions.entity.Interactions;
import com.example.reddit.post.dto.classes.PostWithUserInteractions;
import com.example.reddit.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@ToString(exclude = "user")
@Entity
@DynamicInsert
@NamedNativeQuery(
  name = "Post.getPostTitle", // repository name
  query = "SELECT p.id, p.title, u.username, i.vote_status AS voteStatus FROM post p JOIN user u on p.user_id = u.id JOIN interactions i on p.id = i.post_id AND u.id = i.user_id WHERE p.id = 1 AND u.id = 1;",
  resultSetMapping = "PostTitle"
)
@SqlResultSetMapping(
  name = "PostTitle", // em creation name
  classes = {
    @ConstructorResult(
      targetClass = PostWithUserInteractions.class,
      columns = {
        @ColumnResult(name = "id", type = java.lang.Long.class),
        @ColumnResult(name = "title"),
        @ColumnResult(name = "username"),
        @ColumnResult(name = "voteStatus"),
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

  @ManyToOne(fetch = EAGER)
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties("post")
  private User user;

  @OneToMany(
    mappedBy = "post",
    cascade = ALL,
    orphanRemoval = true,
    targetEntity = Interactions.class,
    fetch = EAGER
  )
  @JsonIgnoreProperties("post")
  private List<Interactions> interactions = new ArrayList<>();

  public Post() {}

  public Post(String title) {
    this.title = title;
  }

  public Post(String title, String content, User user) {
    this.title = title;
    this.content = content;
    this.user = user;
  }

  public static Post of(String title, String content, User user) {
    return new Post(title, content, user);
  }
}
