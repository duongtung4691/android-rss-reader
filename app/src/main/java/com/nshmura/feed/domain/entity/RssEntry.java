package com.nshmura.feed.domain.entity;

import java.io.Serializable;

public class RssEntry  implements Serializable {
  public String title;
  public long createdOn;
  public String body;
  public String link;
}
