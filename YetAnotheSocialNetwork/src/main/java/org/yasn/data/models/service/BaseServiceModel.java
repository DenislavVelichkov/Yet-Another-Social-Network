package org.yasn.data.models.service;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class BaseServiceModel implements Serializable {

  private String id;
}
