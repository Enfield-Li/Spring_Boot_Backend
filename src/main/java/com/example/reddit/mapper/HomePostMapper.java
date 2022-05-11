package com.example.reddit.mapper;

import com.example.reddit.mapper.DTO_POJO.homePost.PostAndInteractions;
import com.example.reddit.mapper.source_POJO.ProfileWithInteractions;
import com.example.reddit.mapper.source_POJO.ProfileWitoutInteractions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface HomePostMapper {
  @Mappings(
    {
      @Mapping(target = "post.id", source = "profileWitoutInteractions.postId"),
      @Mapping(
        target = "post.createdAt",
        source = "profileWitoutInteractions.postCreatedAt"
      ),
      @Mapping(
        target = "post.updatedAt",
        source = "profileWitoutInteractions.postUpdatedAt"
      ),
      @Mapping(
        target = "post.title",
        source = "profileWitoutInteractions.title"
      ),
      @Mapping(
        target = "post.content",
        source = "profileWitoutInteractions.content"
      ),
      @Mapping(
        target = "post.viewCount",
        source = "profileWitoutInteractions.viewCount"
      ),
      @Mapping(
        target = "post.votePoints",
        source = "profileWitoutInteractions.votePoints"
      ),
      @Mapping(
        target = "post.likePoints",
        source = "profileWitoutInteractions.likePoints"
      ),
      @Mapping(
        target = "post.confusedPoints",
        source = "profileWitoutInteractions.confusedPoints"
      ),
      @Mapping(
        target = "post.laughPoints",
        source = "profileWitoutInteractions.laughPoints"
      ),
      @Mapping(
        target = "post.commentAmounts",
        source = "profileWitoutInteractions.commentAmounts"
      ),
      @Mapping(target = "post.userId", source = "profileWitoutInteractions.id"),
    }
  )
  PostAndInteractions toPostAndInteractions(
    ProfileWitoutInteractions profileWitoutInteractions
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
  PostAndInteractions toPostAndInteractions(
    ProfileWithInteractions ProfileWithInteractions
  );
}
