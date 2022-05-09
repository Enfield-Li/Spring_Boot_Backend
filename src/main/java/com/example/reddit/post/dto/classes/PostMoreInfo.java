package com.example.reddit.post.dto.classes;

import java.util.Objects;

public class PostMoreInfo {

  private String title;
  private String content;

  public PostMoreInfo() {}

  public PostMoreInfo(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public PostMoreInfo title(String title) {
    setTitle(title);
    return this;
  }

  public PostMoreInfo content(String content) {
    setContent(content);
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof PostMoreInfo)) {
      return false;
    }
    PostMoreInfo postMoreInfo = (PostMoreInfo) o;
    return (
      Objects.equals(title, postMoreInfo.title) &&
      Objects.equals(content, postMoreInfo.content)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, content);
  }

  @Override
  public String toString() {
    return (
      "{" +
      " title='" +
      getTitle() +
      "'" +
      ", content='" +
      getContent() +
      "'" +
      "}"
    );
  }
}
