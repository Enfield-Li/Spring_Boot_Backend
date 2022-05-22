package com.example.reddit.post.dto;

import com.example.reddit.user.dto.UserInfo;
import lombok.Data;

@Data
public class PostWithUserInfo {

  private Long id;
  private String title;
  private String content;
  private UserInfo user;
}
