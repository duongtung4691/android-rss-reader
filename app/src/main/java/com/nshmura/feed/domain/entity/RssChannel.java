package com.nshmura.feed.domain.entity;

import java.io.Serializable;

public class RssChannel implements Serializable {
  public String subscribeId;
  public String icon;
  public String folder;
  public Integer rate;
  public String title;
  public Integer unreadCount;
  public Long modifiedOn;
  public Integer subscribersCount;
}
