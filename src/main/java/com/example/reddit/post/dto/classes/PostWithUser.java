package com.example.reddit.post.dto.classes;

public class PostWithUser {

  private Long id;
  private String title;
  private String username;

  public PostWithUser() {}

  public PostWithUser(Long id, String title, String username) {
    this.id = id;
    this.title = title;
    this.username = username;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public PostWithUser id(Long id) {
    setId(id);
    return this;
  }

  public PostWithUser title(String title) {
    setTitle(title);
    return this;
  }

  public PostWithUser username(String username) {
    setUsername(username);
    return this;
  }

  @Override
  public String toString() {
    return (
      "{" +
      " id='" +
      getId() +
      "'" +
      ", title='" +
      getTitle() +
      "'" +
      ", username='" +
      getUsername() +
      "'" +
      "}"
    );
  }
}
