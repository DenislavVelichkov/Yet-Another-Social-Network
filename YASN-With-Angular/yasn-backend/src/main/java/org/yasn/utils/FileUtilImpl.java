package org.yasn.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class FileUtilImpl implements FileUtil {

  @Override
  public byte[] downloadImageFile(URL url) {
    byte[] image = null;

    try {
      URLConnection conn = url.openConnection();
      conn.setConnectTimeout(5000);
      conn.setReadTimeout(5000);
      conn.connect();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      IOUtils.copy(conn.getInputStream(), baos);
      image = baos.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return image;
  }

  @Override
  public String encodeByteArrayToBase64String(byte[] content) {
    return Base64.getEncoder().encodeToString(content);
  }
}
