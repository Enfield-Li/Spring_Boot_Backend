package com.example.reddit.mapper;

import com.example.reddit.mapper.DTO_POJO.PostAndInteractions;
import com.example.reddit.user.dto.DB_POJO.UserProfileWithInteractions;
import com.example.reddit.user.dto.DB_POJO.UserProfileWitoutInteractions;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserPostMapper {
  PostAndInteractions toPostAndInteractions(
    UserProfileWitoutInteractions userProfile
  );
  PostAndInteractions userProfileToPostAndInteractions(
    UserProfileWithInteractions UserProfileWithInteractions
  );
}
