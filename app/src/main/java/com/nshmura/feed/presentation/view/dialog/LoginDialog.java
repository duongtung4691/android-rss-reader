package com.nshmura.feed.presentation.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.nshmura.feed.R;
import com.nshmura.feed.databinding.DialogLoginBinding;
import com.nshmura.feed.presentation.view.page.home.HomeActivity;
import com.nshmura.feed.presentation.viewmodel.LoginViewModel;
import javax.inject.Inject;
import rx.Observer;

public class LoginDialog extends BaseDialogFragment {

  @Inject LoginViewModel viewModel;

  public static LoginDialog newInstance() {
    return new LoginDialog();
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    component().inject(this);
    DialogLoginBinding binding = bindView(R.layout.dialog_login);
    binding.setViewModel(viewModel);
    binding.loginSubmit.setOnClickListener(this::onSubmit);
    return builder().setView(binding.getRoot()).create();
  }

  private void onSubmit(View v) {
    viewModel.login(new Observer<Boolean>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        onLoginError();
      }

      @Override public void onNext(Boolean success) {
        if (success) {
          onLoginSuccess();
        } else {
          onLoginError();
        }
      }
    });
  }

  private void onLoginSuccess() {
    Toast.makeText(getContext(), "login success", Toast.LENGTH_SHORT).show(); //TODO
    startActivity(HomeActivity.createIntent(getContext()));
  }

  private void onLoginError() {
    Toast.makeText(getContext(), "login error", Toast.LENGTH_SHORT).show(); //TODO
  }
}
