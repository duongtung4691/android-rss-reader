package com.nshmura.feed.presentation.util;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

public class FileUtil {

  public static String readAssetsFile(Context context, String filename) throws IOException {
    InputStream input = null;
    try {
      input = context.getResources().getAssets().open(filename);
      return IOUtils.toString(input);
    } finally {
      IOUtils.closeQuietly(input);
    }
  }
}
