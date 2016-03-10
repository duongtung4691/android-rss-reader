package com.nshmura.feed.presentation.view.page.home;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.nshmura.feed.R;
import com.nshmura.feed.domain.entity.GroupBy;
import com.nshmura.feed.domain.entity.RssChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static class Item {
    public ItemType itemType;
    public RssChannel rssChannel;
    public String holder;
    public String star;
  }

  private enum ItemType {
    FOLDER, STAR, CHANNEL
  }

  private LayoutInflater layoutInflater;
  private List<Item> items = new ArrayList<>();
  private GroupBy groupBy;

  public MainAdapter(LayoutInflater layoutInflater) {
    this.layoutInflater = layoutInflater;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (ItemType.values()[viewType]) {
      case FOLDER:
        return new FolderViewHolder(
            DataBindingUtil.inflate(layoutInflater, R.layout.row_feed_folder, parent, false));

      case STAR:
        return new StarViewHolder(layoutInflater.inflate(R.layout.row_feed_star, parent, false));

      case CHANNEL: {
        return new ChannelViewHolder(
            DataBindingUtil.inflate(layoutInflater, R.layout.row_feed_channel, parent, false));
      }
      default:
        throw new IllegalArgumentException("unknown viewType " + viewType);
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof FolderViewHolder) {
      ((FolderViewHolder) holder).bind(items.get(position).holder);
    } else if (holder instanceof StarViewHolder) {
      ((StarViewHolder) holder).bind(items.get(position).star);
    } else if (holder instanceof ChannelViewHolder) {
      ((ChannelViewHolder) holder).bind(items.get(position).rssChannel);
    }
  }

  @Override public int getItemCount() {
    return items.size();
  }

  @Override public int getItemViewType(int position) {
    return items.get(position).itemType.ordinal();
  }

  public void addAll(List<RssChannel> rssChannels) {
    int start = this.items.size();
    this.items.addAll(convertToItems(rssChannels));
    notifyItemRangeInserted(start, items.size());
  }

  public void clear() {
    int size = items.size();
    items.clear();
    notifyItemRangeRemoved(0, size);
  }

  public void setGroupBy(GroupBy groupBy) {
    this.groupBy = groupBy;
  }

  private List<MainAdapter.Item> convertToItems(List<RssChannel> rssChannels) {
    LinkedList<Item> items = new LinkedList<>();

    String current = "";
    for (RssChannel channel : rssChannels) {
      String group = getChannelGroup(channel);
      if (!current.equals(group)) {
        items.add(createFolderItem(group));
        current = group;
      }
      items.add(createChannelItem(channel));
    }

    return items;
  }

  private String getChannelGroup(RssChannel channel) {
    switch (groupBy) {
      case FLAT:
        return "";

      case FOLDER:
        return channel.folder;

      case RATE:
        String prefix = "★★★★★".substring(0, channel.rate);
        String suffix = "☆☆☆☆☆".substring(0, 5 - channel.rate);
        return prefix + suffix;
    }
    return "";
  }

  private MainAdapter.Item createFolderItem(String holder) {
    MainAdapter.Item item = new MainAdapter.Item();
    item.itemType = MainAdapter.ItemType.FOLDER;
    item.holder = holder;
    return item;
  }

  private MainAdapter.Item createChannelItem(RssChannel channel) {
    MainAdapter.Item item = new MainAdapter.Item();
    item.itemType = MainAdapter.ItemType.CHANNEL;
    item.rssChannel = channel;
    return item;
  }
}
