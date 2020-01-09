package org.yasn.config;

import javax.validation.Validation;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.yasn.data.models.service.user.UserServiceModel;
import org.yasn.data.models.service.wall.PostCommentServiceModel;
import org.yasn.data.models.service.wall.WallPostServiceModel;
import org.yasn.utils.FileUtil;
import org.yasn.utils.FileUtilImpl;
import org.yasn.utils.TimeUtil;
import org.yasn.utils.TimeUtilImpl;
import org.yasn.web.models.binding.PostCommentBindingModel;
import org.yasn.web.models.binding.UserRegisterBindingModel;
import org.yasn.web.models.binding.UserSendCredentials;
import org.yasn.web.models.binding.WallPostBindingModel;

@Configuration
public class ApplicationBeanConfiguration {

  private static ModelMapper modelMapper;
  private static FileUtil fileUtil;

  static {
    fileUtil = new FileUtilImpl();
    modelMapper = new ModelMapper();
    initMapper(modelMapper);
  }

  private static void initMapper(ModelMapper modelMapper) {

    modelMapper.getConfiguration()
               .setMatchingStrategy(MatchingStrategies.STRICT)
               .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
               .setSkipNullEnabled(true);

    modelMapper.createTypeMap(UserRegisterBindingModel.class, UserServiceModel.class)
               .addMappings(mapper -> mapper.skip(UserServiceModel::setId))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setUsername))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setActive))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setCreatedOn))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setUserProfile))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setAuthorities));

    modelMapper.createTypeMap(WallPostBindingModel.class, WallPostServiceModel.class)
               .addMappings(mapper -> mapper.skip(WallPostServiceModel::setCreatedOn))
               .addMappings(mapper -> mapper.skip(WallPostServiceModel::setComments))
               .addMappings(mapper -> mapper.skip(WallPostServiceModel::setId))
               .addMappings(mapper -> mapper.skip(WallPostServiceModel::setPostOwner))
               .addMappings(mapper -> mapper.skip(WallPostServiceModel::setActualLikes));

    modelMapper.createTypeMap(PostCommentBindingModel.class, PostCommentServiceModel.class)
               .addMappings(mapper -> mapper.skip(PostCommentServiceModel::setId))
               .addMappings(mapper -> mapper.skip(PostCommentServiceModel::setParentPost))
               .addMappings(mapper -> mapper.skip(PostCommentServiceModel::setCreatedOn))
               .addMappings(mapper -> mapper.skip(PostCommentServiceModel::setCommentOwner));

    modelMapper.createTypeMap(UserServiceModel.class, UserSendCredentials.class)
               .addMappings(mapper -> mapper.skip(UserSendCredentials::setUserProfileId));

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
  public TimeUtil timeUtil() {
    return new TimeUtilImpl();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration =
        new CorsConfiguration().applyPermitDefaultValues();
    corsConfiguration.addExposedHeader(
        "Authorization," +
            " X-XSRF-TOKEN," +
            " X-Auth-Token," +
            " Access-Control-Allow-Headers," +
            " Origin," +
            " Accept," +
            " X-Requested-With," +
            " Content-Type," +
            " Access-Control-Request-Method," +
            " Custom-Filter-Header");
    corsConfiguration.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }

  @Bean
  public CsrfTokenRepository csrfTokenRepository() {
    CookieCsrfTokenRepository tokenRepository =
        CookieCsrfTokenRepository.withHttpOnlyFalse();
    tokenRepository.setCookiePath("/");
    tokenRepository.setHeaderName("X-XSRF-TOKEN");
    tokenRepository.setCookieName("XSRF-TOKEN");
    tokenRepository.setCookieHttpOnly(true);
    return tokenRepository;
  }

}
