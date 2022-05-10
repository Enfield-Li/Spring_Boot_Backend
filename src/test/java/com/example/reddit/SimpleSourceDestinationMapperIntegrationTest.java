package com.example.reddit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.reddit.mapper.demo.SimpleDestination;
import com.example.reddit.mapper.demo.SimpleSource;
import com.example.reddit.mapper.demo.SimpleSourceDestinationMapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class SimpleSourceDestinationMapperIntegrationTest {

  private SimpleSourceDestinationMapper mapper = Mappers.getMapper(
    SimpleSourceDestinationMapper.class
  );

  @Test
  public void givenSourceToDestination_whenMaps_thenCorrect() {
    SimpleSource simpleSource = new SimpleSource();
    simpleSource.setName("SourceName");
    simpleSource.setDescription("SourceDescription");
    SimpleDestination destination = mapper.sourceToDestination(simpleSource);

    assertEquals(simpleSource.getName(), destination.getName());
    assertEquals(simpleSource.getDescription(), destination.getDescription());
  }

  @Test
  public void givenDestinationToSource_whenMaps_thenCorrect() {
    SimpleDestination destination = new SimpleDestination();
    destination.setName("DestinationName");
    destination.setDescription("DestinationDescription");
    SimpleSource source = mapper.destinationToSource(destination);
    assertEquals(destination.getName(), source.getName());
    assertEquals(destination.getDescription(), source.getDescription());
  }
}
