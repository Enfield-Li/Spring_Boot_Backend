package com.example.reddit.user.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.example.reddit.interactions.entity.Interactions;
import com.example.reddit.mapper.source.userPost.UserPostInfoWithInteractions;
import com.example.reddit.mapper.source.userPost.UserPostInfoWithoutInteractions;
import com.example.reddit.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@JsonSerialize
@DynamicInsert
@SqlResultSetMapping(
  name = "UserProfileWithoutInteractions", // em creation name
  classes = {
    @ConstructorResult(
      targetClass = UserPostInfoWithoutInteractions.class,
      columns = {
        @ColumnResult(name = "id", type = Long.class),
        @ColumnResult(name = "userCreatedAt", type = Instant.class),
        @ColumnResult(name = "email"),
        @ColumnResult(name = "post_amounts"),
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
  name = "UserProfileWithInteractions", // em creation name
  classes = {
    @ConstructorResult(
      targetClass = UserPostInfoWithInteractions.class,
      columns = {
        @ColumnResult(name = "id", type = Long.class),
        @ColumnResult(name = "userCreatedAt", type = Instant.class),
        @ColumnResult(name = "email"),
        @ColumnResult(name = "post_amounts"),
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
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "created_at")
  @CreationTimestamp
  private Instant createdAt;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Embedded
  @JsonIgnore
  private Password password;

  @Column(name = "post_amounts")
  @ColumnDefault(value = "0")
  private Integer postAmounts;


  @ToString.Exclude
  @JsonIgnore
  @OneToMany(
    mappedBy = "user",
    cascade = ALL,
    orphanRemoval = true,
    targetEntity = Post.class,
    fetch = LAZY
  )
  private List<Post> post = new ArrayList<>();

  @JsonIgnore
  @OneToMany(
    mappedBy = "user",
    cascade = ALL,
    orphanRemoval = true,
    targetEntity = Interactions.class,
    fetch = LAZY
  )
  private List<Interactions> interactions = new ArrayList<>();

  public User() {}

  public User(String username, String email, Password password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public static User of(String username, String email, Password password) {
    return new User(username, email, password);
  }
}
