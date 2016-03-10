package com.nshmura.feed.domain.usecase;

import com.nshmura.feed.domain.repository.AuthRepository;
import javax.inject.Inject;
import rx.Observable;

public class LoginUseCase {

  private final AuthRepository authRepository;

  @Inject
  public LoginUseCase(AuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  public Observable<Boolean> execute(String loginId, String password) {
    return authRepository.login(loginId, password);
  }
}
