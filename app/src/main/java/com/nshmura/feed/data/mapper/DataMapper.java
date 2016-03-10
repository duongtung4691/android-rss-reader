package com.nshmura.feed.data.mapper;

public interface DataMapper<T1, T2> {
  T2 transform(T1 dto);
}
