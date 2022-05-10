package com.example.reddit.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface SimpleSourceDestinationMapper {
  SimpleDestination sourceToDestination(SimpleSource source);
  SimpleSource destinationToSource(SimpleDestination destination);
}
