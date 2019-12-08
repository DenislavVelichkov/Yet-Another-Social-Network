package org.yasn.service;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.yasn.common.enums.UserRoles;
import org.yasn.data.entities.user.Role;
import org.yasn.data.models.service.RoleServiceModel;
import org.yasn.repository.user.RoleRepository;
import org.yasn.service.interfaces.RoleService;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final ModelMapper modelMapper;

  @Override
  public void seedRolesInDb() {
    if (this.roleRepository.count() == 0) {
      this.roleRepository.saveAndFlush(new Role(UserRoles.ADMIN.toString()));
      this.roleRepository.saveAndFlush(new Role(UserRoles.MODERATOR.toString()));
      this.roleRepository.saveAndFlush(new Role(UserRoles.ROOT.toString()));
      this.roleRepository.saveAndFlush(new Role(UserRoles.USER.toString()));
    }
  }

  @Override
  public Set<RoleServiceModel> findAllRoles() {
    return this.roleRepository.findAll()
                              .stream()
                              .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                              .collect(Collectors.toSet());
  }

  @Override
  public RoleServiceModel findByAuthority(String authority) {
    return this.modelMapper
        .map(this.roleRepository.findByAuthority(authority), RoleServiceModel.class);
  }
}
