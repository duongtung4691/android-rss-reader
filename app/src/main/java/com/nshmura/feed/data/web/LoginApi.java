package com.nshmura.feed.data.web;

import android.text.TextUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginApi {

  private static final MediaType MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded");
  private static final String LOGIN_URL = "https://member.livedoor.com/login/index";
  private static final String LOGOUT_URL = "http://reader.livedoor.com/reader/logout";
  private static final String API_KEY_URL = "http://reader.livedoor.com/reader/";

  private OkHttpClient client;

  public LoginApi(OkHttpClient client) {
    this.client = client;
  }

  /**
   * login
   */
  public void login(String id, String password) throws IOException {

    String postData = String.format("livedoor_id=%s&password=%s", id, password);
    RequestBody requestBody = RequestBody.create(MEDIA_TYPE, postData);
    Request request = new Request.Builder().url(LOGIN_URL).post(requestBody).build();
    Response response = client.newCall(request).execute();
    String responseBody = response.body().string();

    String errorMessagse = getLoingErrorMessage(responseBody);
    if (!TextUtils.isEmpty(errorMessagse)) {
      throw new IOException(errorMessagse);
    }
  }

  /**
   * get ApiKye
   */
  public String getApiKey() throws IOException {

    Request request = new Request.Builder().url(API_KEY_URL).build();
    Response response = client.newCall(request).execute();
    String responseBody = response.body().string();

    Matcher match = Pattern.compile("(var ApiKey = \"([a-zA-Z0-9]+)\")").matcher(responseBody);
    if (match.find()) {
      return match.group(2);
    }
    return null;
  }

  /**
   * logout
   */
  public void logout() throws IOException {
    Request request = new Request.Builder().url(LOGOUT_URL).build();
    client.newCall(request).execute();
  }

  /**
   * get login error
   *
   * 例）
   * <div class="error-messages" style="font-size: small">
   * <span style="color: #dd0000; font-weight: bold">正しく入力されていません</span>
   * </div>
   */
  private String getLoingErrorMessage(String responseBody) {

    Matcher match =
        Pattern.compile("<div class=\"error-messages\"[^\n]+>[\n\t ]*<span[^\n>]+>(.*)</span>",
            Pattern.MULTILINE).matcher(responseBody);
    if (match.find()) {
      return match.group(1);
    }
    return null;
  }
}
