package com.nshmura.feed.presentation.binding.conversion;

import android.databinding.BindingAdapter;
import android.support.v4.util.Pair;
import android.widget.EditText;
import com.nshmura.feed.R;
import com.nshmura.feed.presentation.binding.bindable.BindableString;
import com.nshmura.feed.presentation.util.TextWatcherAdapter;

@SuppressWarnings("unused")
public class EditTextBindingAdapter {

  private static final int TAG = R.id.bound_observable;

  @SuppressWarnings("unchecked")
  @BindingAdapter("binding")
  public static void bindEditText(EditText view, final BindableString bindableString) {
    Pair<BindableString, TextWatcherAdapter> pair = (Pair) view.getTag(TAG);
    if (pair == null || pair.first != bindableString) {
      if (pair != null) {
        view.removeTextChangedListener(pair.second);
      }
      TextWatcherAdapter watcher = new TextWatcherAdapter() {
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
          bindableString.set(s.toString());
        }
      };
      view.setTag(TAG, new Pair<>(bindableString, watcher));
      view.addTextChangedListener(watcher);
    }

    String newValue = bindableString.get();
    if (!view.getText().toString().equals(newValue)) {
      view.setText(newValue);
    }
  }
}