package com.nshmura.feed.presentation.view.dialog;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import com.nshmura.feed.MainApplication;
import com.nshmura.feed.di.ActivityComponent;
import com.nshmura.feed.presentation.view.page.BaseActivity;

public class BaseDialogFragment extends AppCompatDialogFragment {

  public AlertDialog.Builder builder() {
    return new AlertDialog.Builder(getContext(),
        MainApplication.get(getContext()).getDialogTheme());
  }

  public <T extends ViewDataBinding> T bindView(@LayoutRes int layoutRes) {
    LayoutInflater inflater = LayoutInflater.from(getContext());
    return DataBindingUtil.inflate(inflater, layoutRes, null, false);
  }

  public ActivityComponent component() {
    return BaseActivity.get(getContext()).component();
  }
}
