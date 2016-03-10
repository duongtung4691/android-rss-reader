package com.nshmura.feed.presentation.util;

import android.content.Context;
import com.nshmura.feed.presentation.entity.NamedEnum;
import java.util.ArrayList;
import java.util.List;

public class EnumUtil {

  public static String[] getNames(Context context, NamedEnum[] values) {
    List<String> list = new ArrayList<>(values.length);
    for (NamedEnum value : values) {
      list.add(context.getString(value.getNameResource()));
    }
    return list.toArray(new String[list.size()]);
  }


  public static <T1, T2 extends NamedEnum<T1>> T2 convert(T1 search, T2[] values) {
    for (T2 value : values) {
      if (value.getEntity() == search) {
        return value;
      }
    }
    throw new IllegalArgumentException("GroupByName not found " + search);
  }
}
