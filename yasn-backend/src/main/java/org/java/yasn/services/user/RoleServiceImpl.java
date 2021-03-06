package org.java.yasn.services.user;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.java.yasn.common.enums.Authorities;
import org.java.yasn.data.entities.user.Role;
import org.java.yasn.data.models.service.user.RoleServiceModel;
import org.java.yasn.repository.user.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
  private final RoleRepository roleRepository;
  private final ModelMapper modelMapper;

  @Override
  public void seedRolesInDb() {
    if (this.roleRepository.count() == 0) {
      this.roleRepository.saveAndFlush(new Role(Authorities.ADMIN));
      this.roleRepository.saveAndFlush(new Role(Authorities.MODERATOR));
      this.roleRepository.saveAndFlush(new Role(Authorities.ROOT));
      this.roleRepository.saveAndFlush(new Role(Authorities.USER));
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
  public RoleServiceModel findByAuthority(Authorities authority) {
    return this.modelMapper
        .map(this.roleRepository.findByAuthority(authority), RoleServiceModel.class);
  }
}
