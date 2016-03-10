package com.nshmura.feed.presentation.binding.conversion;

import android.databinding.BindingAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.nshmura.feed.R;
import com.nshmura.feed.presentation.binding.bindable.BindableBoolean;

@SuppressWarnings("unused")
public class RadioGroupBindingAdapter {

  private static final int TAG = R.id.bound_observable;

  @BindingAdapter("binding")
  public static void bindRadioGroup(RadioGroup view, final BindableBoolean bindableBoolean) {
    if (view.getTag(TAG) != bindableBoolean) {
      view.setTag(TAG, bindableBoolean);
      view.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
          bindableBoolean.set(checkedId == group.getChildAt(1).getId());
        }
      });
    }
    Boolean newValue = bindableBoolean.get();
    ((RadioButton) view.getChildAt(newValue ? 1 : 0)).setChecked(true);
  }
}
