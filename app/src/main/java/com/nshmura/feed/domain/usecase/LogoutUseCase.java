package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.repository.AuthRepository;
import javax.inject.Inject;
import rx.Observable;

public class LogoutUseCase {

  private final AuthRepository authRepository;

  @Inject
  public LogoutUseCase(AuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  public Observable<Boolean> execute() {
    return authRepository.logout();
  }
}
