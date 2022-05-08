package com.example.reddit.post.dto.classes;

public class PostTitle {

  private String title;

  public PostTitle() {}

  public PostTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PostTitle title(String title) {
    setTitle(title);
    return this;
  }

  @Override
  public String toString() {
    return "{" + " title='" + getTitle() + "'" + "}";
  }
}
