package com.example.reddit.post.dto.classes;

public class PostWithUserInteractions {

  private Long id;
  private String title;
  private String username;
  private Boolean voteStatus;

  public PostWithUserInteractions() {}

  public PostWithUserInteractions(
    Long id,
    String title,
    String username,
    Boolean voteStatus
  ) {
    this.id = id;
    this.title = title;
    this.username = username;
    this.voteStatus = voteStatus;
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

  public Boolean isVoteStatus() {
    return this.voteStatus;
  }

  public Boolean getVoteStatus() {
    return this.voteStatus;
  }

  public void setVoteStatus(Boolean voteStatus) {
    this.voteStatus = voteStatus;
  }

  public PostWithUserInteractions id(Long id) {
    setId(id);
    return this;
  }

  public PostWithUserInteractions title(String title) {
    setTitle(title);
    return this;
  }

  public PostWithUserInteractions username(String username) {
    setUsername(username);
    return this;
  }

  public PostWithUserInteractions voteStatus(Boolean voteStatus) {
    setVoteStatus(voteStatus);
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
      ", voteStatus='" +
      isVoteStatus() +
      "'" +
      "}"
    );
  }
}
