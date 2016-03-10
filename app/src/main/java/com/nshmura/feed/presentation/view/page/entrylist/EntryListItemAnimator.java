package com.nshmura.feed.presentation.view.page.entrylist;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

public class EntryListItemAnimator extends FadeInUpAnimator {

  public EntryListItemAnimator() {
    setInterpolator(new FastOutSlowInInterpolator());
    setAddDuration(300);
  }

  @Override protected void preAnimateAddImpl(RecyclerView.ViewHolder holder) {
    ViewCompat.setTranslationY(holder.itemView, holder.itemView.getHeight() * 0.1f);
    ViewCompat.setAlpha(holder.itemView, 0);
  }
}
