package com.example.reddit.mapper;

import com.example.reddit.mapper.target.homePost.PostAndInteractions;
import com.example.reddit.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

// @Mapper
public interface SinglePostMapper {
  //   @Mappings({ @Mapping(target = "post"), @Mapping(target = "interactions") })
  //   PostAndInteractions toPostAndInteractions(Post postEntity);
}
