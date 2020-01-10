package org.yasn.web.models.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileEditBindingModel {

  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private String oldPassword;
  private String newPassword;
  private String gender;
  private String birthday;
  private String confirmEmail;
  private String confirmNewPassword;
  private MultipartFile profilePicture;
  private MultipartFile coverPicture;
}
