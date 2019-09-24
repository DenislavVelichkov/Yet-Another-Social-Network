package org.yasn.domain.models.service;

public class RoleServiceModel extends BaseServiceModel {
  private String authority;

  public RoleServiceModel() {
  }

  public String getAuthority() {
    return this.authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
