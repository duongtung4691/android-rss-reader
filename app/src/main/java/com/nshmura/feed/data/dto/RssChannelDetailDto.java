package com.nshmura.feed.data.dto;

import java.util.List;

public class RssChannelDetailDto {
  //public List<AdDto> ads;
  public String subscribe_id;
  public long last_stored_on;
  public RssChannelDto channel;
  public List<RssEntryDto> items;
}
