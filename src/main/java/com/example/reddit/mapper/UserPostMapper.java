package com.example.reddit.mapper;

import com.example.reddit.mapper.dto.homePost.PostAndInteractions;
import com.example.reddit.mapper.dto.userPost.UserPostAndInteractions;
import com.example.reddit.mapper.source.ProfileWithInteractions;
import com.example.reddit.mapper.source.ProfileWitoutInteractions;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserPostMapper {
  @Mappings(
    {
      @Mapping(target = "post.id", source = "profile.postId"),
      @Mapping(target = "post.createdAt", source = "profile.postCreatedAt"),
      @Mapping(target = "post.updatedAt", source = "profile.postUpdatedAt"),
      @Mapping(target = "post.title", source = "profile.title"),
      @Mapping(target = "post.content", source = "profile.content"),
      @Mapping(target = "post.viewCount", source = "profile.viewCount"),
      @Mapping(target = "post.votePoints", source = "profile.votePoints"),
      @Mapping(target = "post.likePoints", source = "profile.likePoints"),
      @Mapping(
        target = "post.confusedPoints",
        source = "profile.confusedPoints"
      ),
      @Mapping(target = "post.laughPoints", source = "profile.laughPoints"),
      @Mapping(
        target = "post.commentAmounts",
        source = "profile.commentAmounts"
      ),
      @Mapping(target = "post.userId", source = "profile.id"),
    }
  )
  UserPostAndInteractions toPostAndInteractions(
    ProfileWitoutInteractions profile
  );

  @Mappings(
    {
      @Mapping(target = "post.id", source = "ProfileWithInteractions.postId"),
      @Mapping(
        target = "post.createdAt",
        source = "ProfileWithInteractions.postCreatedAt"
      ),
      @Mapping(
        target = "post.updatedAt",
        source = "ProfileWithInteractions.postUpdatedAt"
      ),
      @Mapping(target = "post.title", source = "ProfileWithInteractions.title"),
      @Mapping(
        target = "post.content",
        source = "ProfileWithInteractions.content"
      ),
      @Mapping(
        target = "post.viewCount",
        source = "ProfileWithInteractions.viewCount"
      ),
      @Mapping(
        target = "post.votePoints",
        source = "ProfileWithInteractions.votePoints"
      ),
      @Mapping(
        target = "post.likePoints",
        source = "ProfileWithInteractions.likePoints"
      ),
      @Mapping(
        target = "post.confusedPoints",
        source = "ProfileWithInteractions.confusedPoints"
      ),
      @Mapping(
        target = "post.laughPoints",
        source = "ProfileWithInteractions.laughPoints"
      ),
      @Mapping(
        target = "post.commentAmounts",
        source = "ProfileWithInteractions.commentAmounts"
      ),
      @Mapping(target = "post.userId", source = "ProfileWithInteractions.id"),
      @Mapping(
        target = "interactions.createdAt",
        source = "ProfileWithInteractions.interactionCreatedAt"
      ),
      @Mapping(
        target = "interactions.updatedAt",
        source = "ProfileWithInteractions.interactionUpdatedAt"
      ),
      @Mapping(
        target = "interactions.voteStatus",
        source = "ProfileWithInteractions.voteStatus"
      ),
      @Mapping(
        target = "interactions.likeStatus",
        source = "ProfileWithInteractions.likeStatus"
      ),
      @Mapping(
        target = "interactions.laughStatus",
        source = "ProfileWithInteractions.laughStatus"
      ),
      @Mapping(
        target = "interactions.confusedStatus",
        source = "ProfileWithInteractions.confusedStatus"
      ),
      @Mapping(
        target = "interactions.checked",
        source = "ProfileWithInteractions.checked"
      ),
    }
  )
  UserPostAndInteractions toPostAndInteractions(
    ProfileWithInteractions ProfileWithInteractions
  );
}
