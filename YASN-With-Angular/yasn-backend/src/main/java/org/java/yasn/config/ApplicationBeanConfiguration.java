package org.java.yasn.config;

import java.util.Collections;
import javax.validation.Validation;
import javax.validation.Validator;

import org.java.yasn.data.entities.Notification;
import org.java.yasn.data.models.service.action.NotificationServiceModel;
import org.java.yasn.data.models.service.user.UserServiceModel;
import org.java.yasn.data.models.service.wall.PostCommentServiceModel;
import org.java.yasn.data.models.service.wall.WallPostServiceModel;
import org.java.yasn.utils.FileUtil;
import org.java.yasn.utils.FileUtilImpl;
import org.java.yasn.web.models.binding.PostCommentModel;
import org.java.yasn.web.models.binding.UserCredentialsModel;
import org.java.yasn.web.models.binding.UserRegisterModel;
import org.java.yasn.web.models.binding.WallPostModel;
import org.java.yasn.web.models.response.NotificationResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class ApplicationBeanConfiguration {

  private static final ModelMapper modelMapper;
  private static final FileUtil fileUtil;

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

    modelMapper.createTypeMap(UserRegisterModel.class, UserServiceModel.class)
               .addMappings(mapper -> mapper.skip(UserServiceModel::setId))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setUsername))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setActive))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setCreatedOn))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setUserProfile))
               .addMappings(mapper -> mapper.skip(UserServiceModel::setAuthorities));

    modelMapper.createTypeMap(WallPostModel.class, WallPostServiceModel.class)
               .addMappings(mapper -> mapper.skip(WallPostServiceModel::setCreatedOn))
               .addMappings(mapper -> mapper.skip(WallPostServiceModel::setId))
               .addMappings(mapper -> mapper.skip(WallPostServiceModel::setPostOwner));

    modelMapper.createTypeMap(PostCommentModel.class, PostCommentServiceModel.class)
               .addMappings(mapper -> mapper.skip(PostCommentServiceModel::setId))
               .addMappings(mapper -> mapper.skip(PostCommentServiceModel::setParentPost))
               .addMappings(mapper -> mapper.skip(PostCommentServiceModel::setCreatedOn))
               .addMappings(mapper -> mapper.skip(PostCommentServiceModel::setCommentOwner));

    modelMapper.createTypeMap(UserServiceModel.class, UserCredentialsModel.class)
               .addMappings(mapper -> mapper.skip(UserCredentialsModel::setUserProfileId));

    modelMapper.createTypeMap(Notification.class, NotificationResponseModel.class)
               .addMapping(src -> src.getRecipient().getId(), (des, val) -> des.setRecipientId((String) val))
               .addMapping(src -> src.getSender().getId(), (des, val) -> des.setSenderId((String) val))
               .addMapping(src -> src.getRecipient().getFullName(), (des, val) -> des.setRecipientFullName((String) val));

    modelMapper.createTypeMap(Notification.class, NotificationServiceModel.class)
               .addMapping(src -> src.getSender().getId(), (des, val) -> des.setSenderId((String) val));
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
  public CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration =
        new CorsConfiguration().applyPermitDefaultValues();
    corsConfiguration.addAllowedMethod(HttpMethod.PUT);
    corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
    corsConfiguration.addAllowedMethod(HttpMethod.GET);
    corsConfiguration.addAllowedMethod(HttpMethod.POST);
    corsConfiguration.addAllowedMethod(HttpMethod.PATCH);
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.addExposedHeader(
        "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
            + "Content-Type, Access-Control-Request-Method, Custom-Filter-Header");
    corsConfiguration.setAllowedOrigins(
        Collections.singletonList("*"));
    source.registerCorsConfiguration("/**", corsConfiguration);

    return source;
  }

}
