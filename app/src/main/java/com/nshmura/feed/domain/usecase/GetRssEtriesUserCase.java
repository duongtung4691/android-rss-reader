package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.entity.RssChannelDetail;
import com.nshmura.feed.domain.repository.RssChannelRepository;
import javax.inject.Inject;
import rx.Observable;

public class GetRssEtriesUserCase {

  private final RssChannelRepository rssChannelRepository;

  @Inject
  public GetRssEtriesUserCase(RssChannelRepository rssChannelRepository) {
    this.rssChannelRepository = rssChannelRepository;
  }

  public Observable<RssChannelDetail> execute(String subscribeId) {
    return rssChannelRepository.getUnreadRssEntries(subscribeId);
  }
}