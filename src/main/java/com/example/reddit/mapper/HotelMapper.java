package com.example.reddit.mapper;

import com.example.reddit.domain.Hotel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Eduardo Macarron
 */
@Mapper
public interface HotelMapper {
  Hotel selectByCityId(int cityId);
}
