package org.yasn.data.entities.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yasn.data.entities.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "avatars")
public class Avatar extends BaseEntity {

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "gender")
  private String gender;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "profile_picture")
  private byte[] profilePicture;

  @OneToOne(targetEntity = UserProfile.class, mappedBy = "avatar")
  private UserProfile avatarOwner;
}
