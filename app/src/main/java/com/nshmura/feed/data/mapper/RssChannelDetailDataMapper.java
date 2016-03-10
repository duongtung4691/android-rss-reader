package com.nshmura.feed.data.mapper;

import com.nshmura.feed.data.dto.RssChannelDetailDto;
import com.nshmura.feed.data.dto.RssEntryDto;
import com.nshmura.feed.domain.entity.RssChannelDetail;
import com.nshmura.feed.domain.entity.RssEntry;

public class RssChannelDetailDataMapper {

  private final RssChannelDataMapper rssChannelDataMapper;
  private final ListDataMapper<RssEntryDto, RssEntry> rssEntriesDataMapper;

  public RssChannelDetailDataMapper() {
    rssChannelDataMapper = new RssChannelDataMapper();
    rssEntriesDataMapper = new ListDataMapper<>(new RssEntryDataMapper());
  }

  public  RssChannelDetail transform(RssChannelDetailDto dto) {
    RssChannelDetail detail = new RssChannelDetail();
    detail.channel = rssChannelDataMapper.transform(dto.channel);
    detail.subscribeId = dto.subscribe_id;
    detail.items = rssEntriesDataMapper.transform(dto.items);
    return detail;
  }
}
