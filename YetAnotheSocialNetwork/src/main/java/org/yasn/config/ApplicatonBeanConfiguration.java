package org.yasn.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.yasn.mappings.MappingsInitializer;
import org.yasn.utils.FileUtil;
import org.yasn.utils.FileUtilImpl;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicatonBeanConfiguration {
  static ModelMapper modelMapper;
  static FileUtil fileUtil;

  static {
    modelMapper = new ModelMapper();
    MappingsInitializer.initMappings(modelMapper);
    fileUtil = new FileUtilImpl();
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

  @Bean
  public FileUtil fileUtil() {
    return fileUtil;
  }
}
