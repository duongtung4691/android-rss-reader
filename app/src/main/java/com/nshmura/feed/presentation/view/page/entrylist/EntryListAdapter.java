package com.nshmura.feed.presentation.view.page.entrylist;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nshmura.feed.R;
import com.nshmura.feed.databinding.RowEntryListWebviewBinding;
import com.nshmura.feed.domain.entity.RssChannel;
import com.nshmura.feed.domain.entity.RssEntry;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.view.page.entrydetail.EntryDetailActivity;
import com.nshmura.feed.presentation.view.transition.TransitionNames;
import com.nshmura.feed.presentation.viewmodel.viewholder.RowEntryListWebViewViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class EntryListAdapter extends RecyclerView.Adapter<EntryListAdapter.ViewHolder> {

  private LayoutInflater layoutInflater;
  private RssChannel rssChannel;
  private List<RssEntry> items = new ArrayList<>();

  public EntryListAdapter(LayoutInflater layoutInflater, RssChannel rssChannel) {
    this.layoutInflater = layoutInflater;
    this.rssChannel = rssChannel;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(
        DataBindingUtil.inflate(layoutInflater, R.layout.row_entry_list_webview, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(rssChannel, items.get(position));
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public void addAll(List<RssEntry> items) {
    int start = this.items.size();
    this.items.addAll(items);
    notifyItemRangeInserted(start, items.size());
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @Inject RowEntryListWebViewViewModel viewModel;
    private final RowEntryListWebviewBinding binding;

    public ViewHolder(RowEntryListWebviewBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
      BaseActivity.get(itemView.getContext()).component().inject(this);
      binding.setViewModel(viewModel);
      binding.setEventHandler(this);
    }

    public void bind(RssChannel rssChannel, RssEntry rssEntry) {
      viewModel.setRssChannel(rssChannel);
      viewModel.rssEntry.set(rssEntry);

      binding.webview.loadData("", "text/html", "utf-8");
      new Handler().postDelayed(() -> {
        if (rssEntry.equals(viewModel.rssEntry.get())) {
          binding.webview.loadListData(rssEntry.body);
        }
      }, 300);
    }

    @SuppressWarnings("unused") public void onClick(View v) {

      Activity activity = (Activity) v.getContext();
      Intent intent = EntryDetailActivity.createIntent(
          activity, viewModel.geRssChannel(), viewModel.rssEntry.get());

      View toolbar = v.getRootView().findViewById(R.id.toolbar);

      //noinspection unchecked
      Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
          Pair.create(toolbar, TransitionNames.TOOLBAR),
          Pair.create(binding.headerRect, TransitionNames.HEADER),
          Pair.create(binding.webview, TransitionNames.CONTENT),
          Pair.create(binding.title, TransitionNames.TITLE),
          Pair.create(binding.publisher, TransitionNames.PUBLISHER)
      ).toBundle();

      ActivityCompat.startActivity(activity, intent, bundle);
    }
  }
}
