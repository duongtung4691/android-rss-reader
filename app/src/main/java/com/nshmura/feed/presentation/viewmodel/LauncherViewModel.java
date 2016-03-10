package com.nshmura.feed.presentation.viewmodel;

import com.nshmura.feed.domain.usecase.GetIsLoginUseCase;
import javax.inject.Inject;
import rx.Observable;

public class LauncherViewModel extends BaseViewModel {

  private final GetIsLoginUseCase getIsLoginUseCase;

  @Inject
  public LauncherViewModel(GetIsLoginUseCase loginUseCase) {
    this.getIsLoginUseCase = loginUseCase;
  }

  public Observable<Boolean> isLogined() {
    return getIsLoginUseCase.execute();
  }
}
