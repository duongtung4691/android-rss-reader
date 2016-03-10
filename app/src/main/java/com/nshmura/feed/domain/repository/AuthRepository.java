package com.nshmura.feed.domain.repository;

import rx.Observable;

public interface AuthRepository {

  Observable<Boolean> login(String id, String password);

  Observable<Boolean> isLogined();

  Observable<Boolean> logout();
}