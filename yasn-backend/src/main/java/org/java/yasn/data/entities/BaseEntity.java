package org.java.yasn.data.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

  @Id
  @GeneratedValue(generator = "uuid-string")
  @GenericGenerator(
      name = "uuid-string",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id",
      updatable = false,
      unique = true,
      nullable = false)
  private String id;
}
