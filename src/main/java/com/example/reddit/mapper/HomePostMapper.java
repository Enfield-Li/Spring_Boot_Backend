package com.example.reddit.mapper;

import com.example.reddit.mapper.source.homePost.PostInfoWithInteractions;
import com.example.reddit.mapper.source.homePost.PostInfoWithoutInteractions;
import com.example.reddit.mapper.target.homePost.PostAndInteractions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface HomePostMapper {
  @Mappings(
    {
      @Mapping(target = "post.user.id", source = "postInfo.id"),
      @Mapping(target = "post.user.username", source = "postInfo.username"),
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
    }
  )
  PostAndInteractions toPostAndInteractions(
    PostInfoWithoutInteractions postInfo
  );

  @Mappings(
    {
      @Mapping(target = "post.user.id", source = "postInfo.id"),
      @Mapping(target = "post.user.username", source = "postInfo.username"),
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
  PostAndInteractions toPostAndInteractions(PostInfoWithInteractions postInfo);
}
