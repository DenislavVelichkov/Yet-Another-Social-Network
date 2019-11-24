package org.yasn.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingRespectLayoutTitleStrategy;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.yasn.utils.FileUtil;
import org.yasn.utils.FileUtilImpl;
import org.yasn.utils.TimeUtil;
import org.yasn.utils.TimeUtilImpl;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicatonBeanConfiguration {

  private static ModelMapper modelMapper;
  private static FileUtil fileUtil;

  static {
    fileUtil = new FileUtilImpl();
    modelMapper = new ModelMapper();
//    MappingsInitializer.initMappings(modelMapper);
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapper.validate();
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

  @Bean
  public TimeUtil timeUtil(){
    return new TimeUtilImpl();
  }

  //  Required in order layout to work properly
  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect(new GroupingRespectLayoutTitleStrategy(), true);
  }
}
