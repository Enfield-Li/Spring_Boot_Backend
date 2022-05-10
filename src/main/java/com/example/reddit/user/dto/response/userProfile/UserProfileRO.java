package com.example.reddit.user.dto.response.userProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRO {

  private UserInfo user;
  private UserPaginatedPost userPaginatedPost;
}
