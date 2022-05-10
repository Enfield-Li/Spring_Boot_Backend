package com.example.reddit.mapper;

public class SimpleSource {

  private String name;
  private String description;

  public SimpleSource() {}

  public SimpleSource(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public SimpleSource name(String name) {
    setName(name);
    return this;
  }

  public SimpleSource description(String description) {
    setDescription(description);
    return this;
  }

  @Override
  public String toString() {
    return (
      "{" +
      " name='" +
      getName() +
      "'" +
      ", description='" +
      getDescription() +
      "'" +
      "}"
    );
  }
}
