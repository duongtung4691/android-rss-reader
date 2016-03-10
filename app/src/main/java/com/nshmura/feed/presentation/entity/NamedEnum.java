package com.nshmura.feed.presentation.entity;

import java.io.Serializable;

public interface NamedEnum<T> extends Serializable {
  int getNameResource();
  T getEntity();
}
