package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.repository.RssChannelRepository;
import javax.inject.Inject;
import rx.Observable;

public class TouchAllUseCase {

  private final RssChannelRepository rssChannelRepository;

  @Inject
  public TouchAllUseCase(RssChannelRepository rssChannelRepository) {
    this.rssChannelRepository = rssChannelRepository;
  }

  public Observable<Boolean> execute(String subscribeId) {
    return rssChannelRepository.touchAll(subscribeId);
  }
}