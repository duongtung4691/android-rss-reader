package com.nshmura.feed.presentation.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.nshmura.feed.MainApplication;
import com.nshmura.feed.R;
import com.nshmura.feed.presentation.entity.NamedEnum;
import com.nshmura.feed.presentation.util.EnumUtil;

public class NamedEnumDialog<T extends Enum<T> & NamedEnum<T>> extends BaseDialogFragment {

  private static final String KEY_ENTITY_NAME = "KEY_ENTITY_NAME";
  private static final String KEY_CLASS = "KEY_CLASS";

  public class SelectedEvent {
    public final T value;

    public SelectedEvent(T value) {
      this.value = value;
    }
  }

  private Class<T> clazz;
  private T selected;

  public static <T extends NamedEnum> NamedEnumDialog newInstance(T value, Class<T> clazz) {
    Bundle args = new Bundle();
    args.putSerializable(KEY_ENTITY_NAME, value);
    args.putSerializable(KEY_CLASS, clazz);

    NamedEnumDialog dialog = new NamedEnumDialog();
    dialog.setArguments(args);
    return dialog;
  }

  @SuppressWarnings("unchecked") @NonNull @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    selected = (T) getArguments().getSerializable(KEY_ENTITY_NAME);
    clazz = (Class<T>) getArguments().getSerializable(KEY_CLASS);

    final int selected = this.selected.ordinal();
    final String[] names = EnumUtil.getNames(getContext(), clazz.getEnumConstants());

    return builder().setSingleChoiceItems(names, selected, (dialog, which) -> {
      this.selected = clazz.getEnumConstants()[which];
    }).setPositiveButton(R.string.dialog_ok, (dialog, which) -> {
      if (this.selected != null) {
        MainApplication.getBus(getContext()).post(new SelectedEvent(this.selected));
      }
    }).setNegativeButton(R.string.dialog_cancel, (dialog, which) -> {

    }).create();
  }
}
