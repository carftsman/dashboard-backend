package com.dhatvibs.dashboard.service;

import com.dhatvibs.dashboard.dto.EditProfileDTO;
import com.dhatvibs.dashboard.entity.User;

public interface EditProfileService {

    User editProfile(Long userId, EditProfileDTO request);

}