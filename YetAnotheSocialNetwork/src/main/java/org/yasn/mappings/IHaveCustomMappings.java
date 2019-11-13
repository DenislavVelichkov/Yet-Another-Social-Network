package org.yasn.mappings;

import org.modelmapper.ModelMapper;

public interface IHaveCustomMappings {
  void configureMappings(ModelMapper modelMapper);
}
