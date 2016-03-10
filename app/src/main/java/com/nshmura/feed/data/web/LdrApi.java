package com.nshmura.feed.data.web;

import com.nshmura.feed.data.dto.RssChannelDetailDto;
import com.nshmura.feed.data.dto.RssChannelDto;
import com.nshmura.feed.data.dto.TouchAllResponse;
import java.util.List;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public interface LdrApi {

  /**
   * get channels
   */
  @POST("api/subs") @FormUrlEncoded Observable<List<RssChannelDto>> getChannels(
      @Field("unread") int unread, @Field("from_id") int fromId, @Field("limit") int limit);

  /**
   * get unread entires
   */
  @POST("api/unread") @FormUrlEncoded Observable<RssChannelDetailDto> getUnreadEntries(
      @Field("subscribe_id") String subscribeId, @Field("ApiKey") String apiKey);

  /**
   * mark as read
   */
  @POST("api/touch_all") @FormUrlEncoded Observable<TouchAllResponse> touchAll(
      @Field("subscribe_id") String subscribeId, @Field("ApiKey") String apiKey);

}