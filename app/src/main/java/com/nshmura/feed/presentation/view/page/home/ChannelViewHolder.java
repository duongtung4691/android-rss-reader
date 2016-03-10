package com.nshmura.feed.presentation.view.page.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.nshmura.feed.databinding.RowFeedChannelBinding;
import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.view.page.entrylist.EntryListActivity;
import com.nshmura.feed.presentation.view.transition.TransitionNames;
import com.nshmura.feed.presentation.viewmodel.viewholder.RowFeedChannelViewModel;
import javax.inject.Inject;

public class ChannelViewHolder extends RecyclerView.ViewHolder {

  @Inject RowFeedChannelViewModel viewModel;
  private RowFeedChannelBinding binding;

  public ChannelViewHolder(RowFeedChannelBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
    BaseActivity.get(itemView.getContext()).component().inject(this);
    binding.setViewModel(viewModel);
    binding.setEventHandler(this);
  }

  public void bind(RssChannel rssChannel) {
    viewModel.rssChannel.set(rssChannel);
  }

  @SuppressWarnings("unused") public void onClick(View v) {

    Activity activity = (Activity) v.getContext();
    Intent intent = EntryListActivity.createIntent(activity,  viewModel.rssChannel.get());

    //noinspection unchecked
    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
          Pair.create(v, TransitionNames.HEADER),
          Pair.create(binding.title, TransitionNames.TITLE))
          .toBundle();

    ActivityCompat.startActivity(activity, intent, bundle);
  }
}