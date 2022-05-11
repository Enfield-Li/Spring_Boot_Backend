package com.example.reddit.mapper;

import com.example.reddit.mapper.DTO_POJO.PostAndInteractions;
import com.example.reddit.mapper.DTO_POJO.UserPostAndInteractions;
import com.example.reddit.user.dto.DB_POJO.UserProfileWithInteractions;
import com.example.reddit.user.dto.DB_POJO.UserProfileWitoutInteractions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserPostMapper {
  @Mappings(
    {
      @Mapping(target = "post.id", source = "userProfile.postId"),
      @Mapping(target = "post.createdAt", source = "userProfile.postCreatedAt"),
      @Mapping(target = "post.updatedAt", source = "userProfile.postUpdatedAt"),
      @Mapping(target = "post.title", source = "userProfile.title"),
      @Mapping(target = "post.content", source = "userProfile.content"),
      @Mapping(target = "post.viewCount", source = "userProfile.viewCount"),
      @Mapping(target = "post.votePoints", source = "userProfile.votePoints"),
      @Mapping(target = "post.likePoints", source = "userProfile.likePoints"),
      @Mapping(
        target = "post.confusedPoints",
        source = "userProfile.confusedPoints"
      ),
      @Mapping(target = "post.laughPoints", source = "userProfile.laughPoints"),
      @Mapping(
        target = "post.commentAmounts",
        source = "userProfile.commentAmounts"
      ),
      @Mapping(target = "post.userId", source = "userProfile.id"),
    }
  )
  UserPostAndInteractions toPostAndInteractions(
    UserProfileWitoutInteractions userProfile
  );

  @Mappings(
    {
      @Mapping(
        target = "post.id",
        source = "UserProfileWithInteractions.postId"
      ),
      @Mapping(
        target = "post.createdAt",
        source = "UserProfileWithInteractions.postCreatedAt"
      ),
      @Mapping(
        target = "post.updatedAt",
        source = "UserProfileWithInteractions.postUpdatedAt"
      ),
      @Mapping(
        target = "post.title",
        source = "UserProfileWithInteractions.title"
      ),
      @Mapping(
        target = "post.content",
        source = "UserProfileWithInteractions.content"
      ),
      @Mapping(
        target = "post.viewCount",
        source = "UserProfileWithInteractions.viewCount"
      ),
      @Mapping(
        target = "post.votePoints",
        source = "UserProfileWithInteractions.votePoints"
      ),
      @Mapping(
        target = "post.likePoints",
        source = "UserProfileWithInteractions.likePoints"
      ),
      @Mapping(
        target = "post.confusedPoints",
        source = "UserProfileWithInteractions.confusedPoints"
      ),
      @Mapping(
        target = "post.laughPoints",
        source = "UserProfileWithInteractions.laughPoints"
      ),
      @Mapping(
        target = "post.commentAmounts",
        source = "UserProfileWithInteractions.commentAmounts"
      ),
      @Mapping(
        target = "post.userId",
        source = "UserProfileWithInteractions.id"
      ),
      @Mapping(
        target = "interactions.createdAt",
        source = "UserProfileWithInteractions.interactionCreatedAt"
      ),
      @Mapping(
        target = "interactions.updatedAt",
        source = "UserProfileWithInteractions.interactionUpdatedAt"
      ),
      @Mapping(
        target = "interactions.voteStatus",
        source = "UserProfileWithInteractions.voteStatus"
      ),
      @Mapping(
        target = "interactions.likeStatus",
        source = "UserProfileWithInteractions.likeStatus"
      ),
      @Mapping(
        target = "interactions.laughStatus",
        source = "UserProfileWithInteractions.laughStatus"
      ),
      @Mapping(
        target = "interactions.confusedStatus",
        source = "UserProfileWithInteractions.confusedStatus"
      ),
      @Mapping(
        target = "interactions.checked",
        source = "UserProfileWithInteractions.checked"
      ),
    }
  )
  UserPostAndInteractions toPostAndInteractions(
    UserProfileWithInteractions UserProfileWithInteractions
  );
}
