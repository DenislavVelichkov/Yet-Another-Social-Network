package org.yasn.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.yasn.mappings.MappingsInitializer;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicatonBeanConfiguration {
  static ModelMapper modelMapper;

  static {
    modelMapper = new ModelMapper();
    MappingsInitializer.initMappings(modelMapper);
  }

  @Bean
  public ModelMapper modelMapper() {
    return modelMapper;
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public Validator validator() {
    return Validation.buildDefaultValidatorFactory().getValidator();
  }
}
