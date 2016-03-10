package com.nshmura.feed.presentation.viewmodel;

import android.databinding.BaseObservable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseViewModel extends BaseObservable {

  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  public void destroy() {
    compositeSubscription.unsubscribe();
  }

  public void addSubscription(Subscription subscription) {
    compositeSubscription.add(subscription);
  }
}
