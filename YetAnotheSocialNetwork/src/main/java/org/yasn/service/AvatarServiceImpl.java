package org.yasn.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yasn.data.models.service.AvatarServiceModel;
import org.yasn.data.models.view.AvatarViewModel;
import org.yasn.repository.user.AvatarRepository;
import org.yasn.service.interfaces.AvatarService;
import org.yasn.utils.FileUtil;

@Service
public class AvatarServiceImpl implements AvatarService {

  private final ModelMapper modelMapper;
  private final AvatarRepository avatarRepository;
  private final FileUtil fileUtil;

  @Autowired
  public AvatarServiceImpl(ModelMapper modelMapper,
                           AvatarRepository avatarRepository,
                           FileUtil fileUtil) {
    this.modelMapper = modelMapper;
    this.avatarRepository = avatarRepository;
    this.fileUtil = fileUtil;
  }

  @Override
  public AvatarViewModel findAvatarByOwnerId(String id) {

    AvatarServiceModel avatarServiceModel =
        this.modelMapper.map(
            this.avatarRepository.findByAvatarOwner_Id(id),
            AvatarServiceModel.class);

    AvatarViewModel avatarViewModel =
        this.modelMapper.map(
            avatarServiceModel,
            AvatarViewModel.class);

    avatarViewModel.setProfilePicture(
        this.fileUtil.encodeByteArrayToBase64String(
            avatarServiceModel.getProfilePicture()));

    return avatarViewModel;
  }

}
