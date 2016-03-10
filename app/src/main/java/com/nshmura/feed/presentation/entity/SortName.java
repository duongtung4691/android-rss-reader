package com.nshmura.feed.presentation.entity;

import android.content.Context;
import android.support.annotation.StringRes;
import com.nshmura.feed.R;
import com.nshmura.feed.domain.entity.Sort;
import com.nshmura.feed.presentation.util.EnumUtil;

public enum SortName implements NamedEnum<Sort> {

  MODIFIED_ON_DESC(Sort.MODIFIED_ON_DESC, R.string.sort_modified_on_desc),
  MODIFIED_ON_ASC(Sort.MODIFIED_ON_ASC, R.string.sort_modified_on_asc),
  UNREAD_COUNT_DESC(Sort.UNREAD_COUNT_DESC, R.string.sort_unread_count_desc),
  UNREAD_COUNT_ASC(Sort.UNREAD_COUNT_ASC, R.string.sort_unread_count_asc),
  TITLE_ASC(Sort.TITLE_ASC, R.string.sort_title_asc),
  SUBSCRIBERS_COUNT_DESC(Sort.SUBSCRIBERS_COUNT_DESC, R.string.sort_subscribers_count_desc),
  SUBSCRIBERS_COUNT_ASC(Sort.SUBSCRIBERS_COUNT_ASC, R.string.sort_subscribers_count_asc);

  private final Sort entity;
  @StringRes private int nameResoure;

  SortName(Sort entity, @StringRes int nameResoure) {
    this.entity = entity;
    this.nameResoure = nameResoure;
  }

  @Override public int getNameResource() {
    return nameResoure;
  }

  @Override public Sort getEntity() {
    return entity;
  }

  public static String[] getNames(Context context) {
    return EnumUtil.getNames(context, values());
  }

  public static SortName convert(Sort sort) {
    return EnumUtil.convert(sort, values());
  }
}