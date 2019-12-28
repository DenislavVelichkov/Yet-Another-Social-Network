package org.yasn.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

  private final Cloudinary cloudinary;

  @Override
  public String uploadImage(MultipartFile multipartFile) throws IOException {
    File file = File
        .createTempFile("temp-file", multipartFile.getOriginalFilename());
    multipartFile.transferTo(file);

    return this.cloudinary
        .uploader()
        .upload(file, new HashMap())
        .get("url").toString();
  }
}
