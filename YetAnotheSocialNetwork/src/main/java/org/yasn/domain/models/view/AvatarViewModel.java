package org.yasn.domain.models.view;

public class AvatarViewModel {
  private String fullName;
  private byte[] profilePicture;

  public AvatarViewModel() {
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public byte[] getProfilePicture() {
    return this.profilePicture;
  }

  public void setProfilePicture(byte[] profilePicture) {
    this.profilePicture = profilePicture;
  }
}
