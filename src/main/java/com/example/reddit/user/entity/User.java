package com.example.reddit.user.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import com.example.reddit.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
@ToString(exclude = "post")
@Entity
@DynamicInsert
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

  @OneToMany(
    mappedBy = "user",
    cascade = ALL,
    orphanRemoval = true,
    targetEntity = Post.class,
    fetch = EAGER
  )
  @JsonIgnoreProperties("user")
  private List<Post> post = new ArrayList<>();

  public User() {}

  public User(String username, String email, Password password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
