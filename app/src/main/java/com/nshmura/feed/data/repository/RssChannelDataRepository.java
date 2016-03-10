package com.nshmura.feed.data.repository;

import com.nshmura.feed.data.dto.RssChannelDto;
import com.nshmura.feed.data.mapper.ListDataMapper;
import com.nshmura.feed.data.mapper.RssChannelDataMapper;
import com.nshmura.feed.data.mapper.RssChannelDetailDataMapper;
import com.nshmura.feed.data.web.LdrApi;
import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.entity.RssChannelDetail;
import com.nshmura.feed.domain.repository.RssChannelRepository;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RssChannelDataRepository implements RssChannelRepository {

  private final LdrApi ldrApi;
  private final ApiKeyRepository apiKeyRepository;
  private final ListDataMapper<RssChannelDto, RssChannel> rssChannelDataMapper;
  private final RssChannelDetailDataMapper rssChannelDetailDataMapper;

  public RssChannelDataRepository(LdrApi ldrApi, ApiKeyRepository apiKeyRepository) {
    this.ldrApi = ldrApi;
    this.apiKeyRepository = apiKeyRepository;
    this.rssChannelDataMapper = new ListDataMapper<>(new RssChannelDataMapper());
    this.rssChannelDetailDataMapper = new RssChannelDetailDataMapper();
  }

  @Override public Observable<List<RssChannel>> getRssChannels(int unread, int fromId, int limit) {
    return ldrApi.getChannels(unread, fromId, limit).map(rssChannelDataMapper::transform)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<RssChannelDetail> getUnreadRssEntries(String subscribeId) {
    return ldrApi.getUnreadEntries(subscribeId, apiKeyRepository.getApiKey())
        .map(rssChannelDetailDataMapper::transform)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<Boolean> touchAll(String subscribeId) {
    return ldrApi.touchAll(subscribeId, apiKeyRepository.getApiKey())
        .map(touchAllResponse -> touchAllResponse.isSuccess == 1)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
