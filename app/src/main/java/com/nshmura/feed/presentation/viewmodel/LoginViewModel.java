package com.nshmura.feed.presentation.viewmodel;

import com.nshmura.feed.domain.usecase.LoginUseCase;
import com.nshmura.feed.presentation.binding.bindable.BindableString;
import javax.inject.Inject;
import rx.Observer;

public class LoginViewModel extends BaseViewModel {

  public final BindableString id = new BindableString();
  public final BindableString password = new BindableString();

  private final LoginUseCase loginUseCase;

  @Inject public LoginViewModel(LoginUseCase loginUseCase) {
    this.loginUseCase = loginUseCase;
  }

  public void login(String loginId, String password, Observer<Boolean> observer) {
    addSubscription(loginUseCase.execute(loginId, password).subscribe(observer));
  }

  public void login(Observer<Boolean> observer) {
    addSubscription(loginUseCase.execute(id.get(), password.get()).subscribe(observer));
  }
}
