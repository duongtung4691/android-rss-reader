package com.nshmura.feed.domain.repository;

import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.entity.RssChannelDetail;
import java.util.List;
import rx.Observable;

public interface RssChannelRepository {

  Observable<List<RssChannel>> getRssChannels(int unread, int fromId, int limit);

  Observable<RssChannelDetail> getUnreadRssEntries(String subscribeId);

  Observable<Boolean> touchAll(String subscribeId);
}
