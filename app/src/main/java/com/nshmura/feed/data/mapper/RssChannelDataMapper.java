package com.nshmura.feed.data.mapper;

import com.nshmura.feed.data.dto.RssChannelDto;
import com.nshmura.feed.domain.entity.RssChannel;

public class RssChannelDataMapper implements DataMapper<RssChannelDto, RssChannel> {

  @Override public RssChannel transform(RssChannelDto dto) {
    RssChannel rssChannel = new RssChannel();
    rssChannel.subscribeId = dto.subscribe_id;
    rssChannel.icon = dto.icon;
    rssChannel.title = dto.title;
    rssChannel.folder = dto.folder;
    rssChannel.rate = dto.rate;
    rssChannel.unreadCount = dto.unread_count;
    rssChannel.modifiedOn = dto.modified_on;
    rssChannel.subscribersCount = dto.subscribers_count;
    return rssChannel;
  }
}
