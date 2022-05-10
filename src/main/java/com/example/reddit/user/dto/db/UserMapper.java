package com.example.reddit.user.dto.db;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserMapper {
  @Mappings(
    {
      @Mapping(target = "userId", source = "userProfile.id"),
      @Mapping(target = "postTitle", source = "userProfile.title"),
      @Mapping(target = "postContent", source = "userProfile.content"),
    }
  )
  UserAtZero userProfileToUserAtZero(UserProfile userProfile);
}
