package com.nshmura.feed.presentation.view.page.home;

import android.support.v7.widget.RecyclerView;
import com.nshmura.feed.databinding.RowFeedFolderBinding;
import com.nshmura.feed.presentation.view.page.BaseActivity;
import com.nshmura.feed.presentation.viewmodel.viewholder.RowFeedFolderViewModel;
import javax.inject.Inject;

public class FolderViewHolder extends RecyclerView.ViewHolder {

  @Inject RowFeedFolderViewModel viewModel;

  public FolderViewHolder(RowFeedFolderBinding binding) {
    super(binding.getRoot());
    BaseActivity.get(itemView.getContext()).component().inject(this);
    binding.setViewModel(viewModel);
  }

  public void bind(String folderName) {
    viewModel.folder.set(folderName);
  }
}