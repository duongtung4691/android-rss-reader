package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.entity.GroupBy;
import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.entity.Sort;
import com.nshmura.feed.domain.repository.RssChannelRepository;
import com.nshmura.feed.domain.repository.SettingsRepository;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class GetRssChannelsUseCase {

  private final RssChannelRepository rssChannelRepository;
  private final SettingsRepository settingsRepository;

  @Inject public GetRssChannelsUseCase(RssChannelRepository rssChannelRepository,
      SettingsRepository settingsRepository) {
    this.rssChannelRepository = rssChannelRepository;
    this.settingsRepository = settingsRepository;
  }

  public Observable<List<RssChannel>> execute(int unread, int fromId, int limit) {
    return Observable.combineLatest(rssChannelRepository.getRssChannels(unread, fromId, limit),
        settingsRepository.getSortSettings(), settingsRepository.getGroupBySetting(),
        (rssChannels, sort, groupBy) -> groupBy(sort(rssChannels, sort), groupBy));
  }

  private List<RssChannel> groupBy(List<RssChannel> rssChannels, GroupBy groupBy) {
    switch (groupBy) {
      case FLAT:
        break;

      case FOLDER:
        Collections.sort(rssChannels, (l, r) -> l.folder.compareTo(r.folder));
        break;

      case RATE:
        Collections.sort(rssChannels, (l, r) -> r.rate.compareTo(l.rate));
        break;
    }
    return rssChannels;
  }

  private List<RssChannel> sort(List<RssChannel> rssChannels, Sort sort) {
    switch (sort) {
      case MODIFIED_ON_DESC:
        Collections.sort(rssChannels, (l, r) -> r.modifiedOn.compareTo(l.modifiedOn));
        break;
      case MODIFIED_ON_ASC:
        Collections.sort(rssChannels, (l, r) -> l.modifiedOn.compareTo(r.modifiedOn));
        break;
      case UNREAD_COUNT_DESC:
        Collections.sort(rssChannels, (l, r) -> r.unreadCount.compareTo(l.unreadCount));
        break;
      case UNREAD_COUNT_ASC:
        Collections.sort(rssChannels, (l, r) -> l.unreadCount.compareTo(r.unreadCount));
        break;
      case TITLE_ASC:
        Collections.sort(rssChannels, (l, r) -> l.title.compareTo(r.title));
        break;
      case SUBSCRIBERS_COUNT_DESC:
        Collections.sort(rssChannels, (l, r) -> r.subscribersCount.compareTo(l.subscribersCount));
        break;
      case SUBSCRIBERS_COUNT_ASC:
        Collections.sort(rssChannels, (l, r) -> l.subscribersCount.compareTo(r.subscribersCount));
        break;
    }
    return rssChannels;
  }
}