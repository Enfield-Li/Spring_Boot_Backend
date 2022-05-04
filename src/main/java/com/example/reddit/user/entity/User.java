package com.example.reddit.user.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

@Entity
@DynamicInsert
public class User {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @CreatedDate
  private Instant createdAt;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String email;

  @Embedded
  private Password password;

  @ColumnDefault(value = "0")
  private Long postAmounts;
}
