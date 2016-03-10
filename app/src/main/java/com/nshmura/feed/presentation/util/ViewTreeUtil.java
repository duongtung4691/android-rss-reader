package com.nshmura.feed.presentation.util;

import android.view.View;
import android.view.ViewTreeObserver;

public class ViewTreeUtil {
  public static void addOnGlobalLayoutListener(View view, Runnable runnable) {
    view.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            runnable.run();
          }
        });
  }
}
