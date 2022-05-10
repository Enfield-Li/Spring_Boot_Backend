package com.example.reddit.mapper.demo;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleSourceDestinationMapper {
  SimpleDestination sourceToDestination(SimpleSource source);
  SimpleSource destinationToSource(SimpleDestination destination);
}
// SimpleSourceDestinationMapper mapper = Mappers.getMapper(
//   SimpleSourceDestinationMapper.class
// );
// SimpleSource simpleSource = new SimpleSource();
// simpleSource.setName("SourceName");
// simpleSource.setDescription("SourceDescription");
// SimpleDestination destination = mapper.sourceToDestination(simpleSource);
// System.out.println(destination.toString());
// SimpleDestination destination2 = new SimpleDestination();
// destination2.setName("DestinationName");
// destination2.setDescription("DestinationDescription");
// SimpleSource source = mapper.destinationToSource(destination2);
// System.out.println(source.toString());
