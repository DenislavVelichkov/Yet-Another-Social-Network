package org.yasn.utils;

import java.net.URL;

public interface FileUtil {
  byte[] downloadImageFile(URL url);

  String encodeByteArrayToBase64String(byte[] content);
}


