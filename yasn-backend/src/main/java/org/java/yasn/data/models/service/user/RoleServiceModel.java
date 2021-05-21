package org.java.yasn.data.models.service.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.java.yasn.common.enums.Authorities;
import org.java.yasn.data.models.service.BaseServiceModel;

@Getter
@Setter
@NoArgsConstructor
public class RoleServiceModel extends BaseServiceModel {

  private Authorities authority;
}
