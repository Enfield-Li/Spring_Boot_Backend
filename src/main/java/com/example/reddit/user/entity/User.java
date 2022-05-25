package com.example.reddit.user.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import com.example.reddit.interactions.entity.Interactions;
import com.example.reddit.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

/* 
用户实体（User Entity）：
+--------------+--------------+------+-----+---------+----------------+
| Field        | Type         | Null | Key | Default | Extra          |
+--------------+--------------+------+-----+---------+----------------+
| id           | bigint       | NO   | PRI | NULL    | auto_increment |
| created_at   | datetime(6)  | YES  |     | NULL    |                |
| email        | varchar(255) | NO   | UNI | NULL    |                |
| password     | varchar(255) | NO   |     | NULL    |                |
| post_amounts | int          | YES  |     | 0       |                |
| username     | varchar(255) | NO   | UNI | NULL    |                |
+--------------+--------------+------+-----+---------+----------------+

例子（Example）：
+----+----------------------------+-----------------+--------------------------------------------------------------+--------------+----------+
| id | created_at                 | email           | password                                                     | post_amounts | username |
+----+----------------------------+-----------------+--------------------------------------------------------------+--------------+----------+
|  1 | 2022-05-11 09:15:20.882000 | user1@gmail.com | $2a$10$EBq/7srjCoxFWdkUn0niyuefFGjDr/422i7zSdEMoJU3PZf5/BSGW |            4 | user1    |
+----+----------------------------+-----------------+--------------------------------------------------------------+--------------+----------+
 */

@Data
@Entity
@JsonSerialize
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
