package com.nshmura.feed.data.mapper;

import com.nshmura.feed.data.dto.RssEntryDto;
import com.nshmura.feed.domain.entity.RssEntry;

public class RssEntryDataMapper implements DataMapper<RssEntryDto, RssEntry> {

  @Override public RssEntry transform(RssEntryDto dto) {
    RssEntry rssEntry = new RssEntry();
    rssEntry.body = dto.body;
    rssEntry.createdOn = dto.created_on;
    rssEntry.title = dto.title;
    rssEntry.link = dto.link;
    return rssEntry;
  }
}
