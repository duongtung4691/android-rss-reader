package com.nshmura.feed.data.repository;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.nshmura.feed.data.PreferencesStore;
import com.nshmura.feed.data.web.LoginApi;
import com.nshmura.feed.domain.repository.AuthRepository;
import info.metadude.android.typedpreferences.StringPreference;
import java.io.IOException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AuthDataRepository implements AuthRepository {

  private final StringPreference idPreference;
  private final StringPreference passwordPreference;
  private final ApiKeyRepository apiKeyRepository;
  private final LoginApi loginApi;

  public AuthDataRepository(SharedPreferences sharedPreferences, ApiKeyRepository apiKeyRepository,
      LoginApi loginApi) {
    this.idPreference = PreferencesStore.getIdPreference(sharedPreferences);
    this.passwordPreference = PreferencesStore.getPaswordPreference(sharedPreferences);
    this.apiKeyRepository = apiKeyRepository;
    this.loginApi = loginApi;
  }

  @Override public Observable<Boolean> login(String id, String password) {
    return loginAsync(id, password).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<Boolean> isLogined() {
    return loginCheck().subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  @Override public Observable<Boolean> logout() {
    return logoutAsync().subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<Boolean> loginAsync(final String id, final String password) {
    return Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override public void call(Subscriber<? super Boolean> subscriber) {
        try {
          loginApi.login(id, password);
          String apiKey = loginApi.getApiKey();

          if (TextUtils.isEmpty(apiKey)) {
            subscriber.onError(new IOException("cant get api key"));
            return;
          }

          apiKeyRepository.setApiKey(apiKey);
          idPreference.set(id);
          passwordPreference.set(password);

          subscriber.onNext(true);
          subscriber.onCompleted();
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }

  private Observable<Boolean> loginCheck() {
    return Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override public void call(Subscriber<? super Boolean> subscriber) {
        try {
          String apiKey = loginApi.getApiKey();
          apiKeyRepository.setApiKey(apiKey);

          if (TextUtils.isEmpty(apiKey)) {
            subscriber.onNext(false);
            return;
          }

          subscriber.onNext(true);
          subscriber.onCompleted();
        } catch (Exception e) {
          subscriber.onNext(false);
        }
      }
    });
  }

  private Observable<Boolean> logoutAsync() {
    return Observable.create(new Observable.OnSubscribe<Boolean>() {
      @Override public void call(Subscriber<? super Boolean> subscriber) {
        try {
          loginApi.logout();
          idPreference.set("");
          passwordPreference.set("");
          apiKeyRepository.setApiKey("");
          subscriber.onNext(true);
          subscriber.onCompleted();
        } catch (Exception e) {
          subscriber.onError(e);
        }
      }
    });
  }
}
