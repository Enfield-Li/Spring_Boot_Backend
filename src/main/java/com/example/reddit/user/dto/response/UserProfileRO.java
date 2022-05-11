package com.example.reddit.user.dto.response;

import com.example.reddit.mapper.DTO_POJO.UserPaginatedPosts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRO {

  private UserInfo user;
  private UserPaginatedPosts userPaginatedPost;
}
