package com.example.reddit.mapper;

import com.example.reddit.mapper.source.userPost.UserPostInfoWithInteractions;
import com.example.reddit.mapper.source.userPost.UserPostInfoWithoutInteractions;
import com.example.reddit.mapper.target.userPost.UserPostAndInteractions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserPostMapper {
  @Mappings(
    {
      @Mapping(target = "post.id", source = "postInfo.postId"),
      @Mapping(target = "post.createdAt", source = "postInfo.postCreatedAt"),
      @Mapping(target = "post.updatedAt", source = "postInfo.postUpdatedAt"),
      @Mapping(target = "post.title", source = "postInfo.title"),
      @Mapping(target = "post.content", source = "postInfo.content"),
      @Mapping(target = "post.viewCount", source = "postInfo.viewCount"),
      @Mapping(target = "post.votePoints", source = "postInfo.votePoints"),
      @Mapping(target = "post.likePoints", source = "postInfo.likePoints"),
      @Mapping(
        target = "post.confusedPoints",
        source = "postInfo.confusedPoints"
      ),
      @Mapping(target = "post.laughPoints", source = "postInfo.laughPoints"),
      @Mapping(
        target = "post.commentAmounts",
        source = "postInfo.commentAmounts"
      ),
      @Mapping(target = "post.userId", source = "postInfo.id"),
    }
  )
  UserPostAndInteractions toPostAndInteractions(
    UserPostInfoWithoutInteractions postInfo
  );

  @Mappings(
    {
      @Mapping(target = "post.id", source = "postInfo.postId"),
      @Mapping(target = "post.createdAt", source = "postInfo.postCreatedAt"),
      @Mapping(target = "post.updatedAt", source = "postInfo.postUpdatedAt"),
      @Mapping(target = "post.title", source = "postInfo.title"),
      @Mapping(target = "post.content", source = "postInfo.content"),
      @Mapping(target = "post.viewCount", source = "postInfo.viewCount"),
      @Mapping(target = "post.votePoints", source = "postInfo.votePoints"),
      @Mapping(target = "post.likePoints", source = "postInfo.likePoints"),
      @Mapping(
        target = "post.confusedPoints",
        source = "postInfo.confusedPoints"
      ),
      @Mapping(target = "post.laughPoints", source = "postInfo.laughPoints"),
      @Mapping(
        target = "post.commentAmounts",
        source = "postInfo.commentAmounts"
      ),
      @Mapping(target = "post.userId", source = "postInfo.id"),
      @Mapping(
        target = "interactions.voteStatus",
        source = "postInfo.voteStatus"
      ),
      @Mapping(
        target = "interactions.likeStatus",
        source = "postInfo.likeStatus"
      ),
      @Mapping(
        target = "interactions.laughStatus",
        source = "postInfo.laughStatus"
      ),
      @Mapping(
        target = "interactions.confusedStatus",
        source = "postInfo.confusedStatus"
      ),
    }
  )
  UserPostAndInteractions toPostAndInteractions(
    UserPostInfoWithInteractions postInfo
  );
}
