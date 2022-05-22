package com.example.reddit.mapper;

import com.example.reddit.domain.Hotel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HotelMapper {
  Hotel selectByCityId(int cityId);
  List<Hotel> selectAll();
}
