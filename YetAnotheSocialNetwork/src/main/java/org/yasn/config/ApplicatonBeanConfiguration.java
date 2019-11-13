package org.yasn.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingRespectLayoutTitleStrategy;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.spring.SpringIntegration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

  private final BeanFactory beanFactory;
  private final static ModelMapper modelMapper;
  private final static FileUtil fileUtil;

  static {
    fileUtil = new FileUtilImpl();
    modelMapper = new ModelMapper();
    MappingsInitializer.initMappings(modelMapper);
    modelMapper.validate();
  }

  @Autowired
  public ApplicatonBeanConfiguration(BeanFactory beanFactory) {
    this.beanFactory = beanFactory;
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

  //  Required in order layout to work properly
  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect(new GroupingRespectLayoutTitleStrategy(), true);
  }

  @Bean
  Provider<?> springProvider() {
    return SpringIntegration.fromSpring(this.beanFactory);
  }

}
