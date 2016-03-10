package com.nshmura.feed.domain.entity;

import java.io.Serializable;
import java.util.List;

public class RssChannelDetail  implements Serializable {
  public String subscribeId;
  public RssChannel channel;
  public List<RssEntry> items;
}
