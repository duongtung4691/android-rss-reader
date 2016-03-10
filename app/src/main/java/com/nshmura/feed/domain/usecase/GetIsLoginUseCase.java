package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.repository.AuthRepository;
import javax.inject.Inject;
import rx.Observable;

public class GetIsLoginUseCase {

  private final AuthRepository authRepository;

  @Inject public GetIsLoginUseCase(AuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  public Observable<Boolean> execute() {
    return authRepository.isLogined();
  }
}
