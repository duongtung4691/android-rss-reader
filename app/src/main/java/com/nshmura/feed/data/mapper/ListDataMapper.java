package com.nshmura.feed.data.mapper;

import java.util.ArrayList;
import java.util.List;

public class ListDataMapper<T1, T2> {

  private DataMapper<T1, T2> dataMapper;

  public ListDataMapper(DataMapper<T1, T2> dataMapper) {
    this.dataMapper = dataMapper;
  }

  public List<T2> transform(List<T1> dtos) {
    List<T2> list = new ArrayList<>(dtos.size());
    for (T1 dto : dtos) {
      T2 item = dataMapper.transform(dto);
      if (item != null) {
        list.add(item);
      }
    }
    return list;
  }
}
