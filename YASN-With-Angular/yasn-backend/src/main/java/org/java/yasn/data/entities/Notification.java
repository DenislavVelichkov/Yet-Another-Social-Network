package org.java.yasn.data.entities;

import java.time.LocalDate;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.common.enums.NotificationType;
import org.java.yasn.data.entities.user.UserProfile;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "notifications")
public class Notification extends BaseEntity {

  @Column(nullable = false)
  boolean isViewed;

  @ManyToOne(targetEntity = UserProfile.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.PERSIST)
  @JoinColumn(
      name = "recipient_id",
      referencedColumnName = "id",
      nullable = false, updatable = false)
  private UserProfile recipient;

  @Column(nullable = false)
  private String recipientPicture;

  @Column(nullable = false, updatable = false)
  private String recipientFullName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private NotificationType notificationType;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:MM")
  @Column(name = "created_on",
      updatable = false,
      nullable = false)
  private LocalDate createdOn;

  @Column(nullable = false, updatable = false)
  private String content;
}
