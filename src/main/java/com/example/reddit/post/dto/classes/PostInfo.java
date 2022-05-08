package com.example.reddit.post.dto.classes;

import java.util.Objects;

public class PostInfo {

  private Long id;
  private String title;

  public PostInfo() {}

  public PostInfo(Long id, String title) {
    this.id = id;
    this.title = title;
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

  public PostInfo id(Long id) {
    setId(id);
    return this;
  }

  public PostInfo title(String title) {
    setTitle(title);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof PostInfo)) {
      return false;
    }
    PostInfo postWithAuthor = (PostInfo) o;
    return (
      Objects.equals(id, postWithAuthor.id) &&
      Objects.equals(title, postWithAuthor.title)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title);
  }

  @Override
  public String toString() {
    return "{" + " id='" + getId() + "'" + ", title='" + getTitle() + "'" + "}";
  }
}
