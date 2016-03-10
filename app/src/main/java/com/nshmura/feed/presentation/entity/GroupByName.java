package com.nshmura.feed.presentation.entity;

import android.content.Context;
import android.support.annotation.StringRes;
import com.nshmura.feed.R;
import com.nshmura.feed.domain.entity.GroupBy;
import com.nshmura.feed.presentation.util.EnumUtil;

public enum GroupByName implements NamedEnum<GroupBy> {

  FLOAT(GroupBy.FLAT, R.string.group_flat),
  FOLDER(GroupBy.FOLDER, R.string.group_folder),
  RATE(GroupBy.RATE, R.string.group_rate);

  private final GroupBy entity;
  @StringRes private int nameResoure;

  GroupByName(GroupBy entity, @StringRes int nameResoure) {
    this.entity = entity;
    this.nameResoure = nameResoure;
  }

  @Override
  public int getNameResource() {
    return nameResoure;
  }

  @Override
  public GroupBy getEntity() {
    return entity;
  }

  public static String[] getNames(Context context) {
    return EnumUtil.getNames(context, values());
  }

  public static GroupByName convert(GroupBy groupBy) {
    return EnumUtil.convert(groupBy, values());
  }
}