package com.practice.spring.airbnb.services;

import com.practice.spring.airbnb.dto.ProfileUpdateRequestDto;
import com.practice.spring.airbnb.dto.UserDto;
import com.practice.spring.airbnb.entities.User;

public interface UserService {

    User getUserId(Long id);

    User getUserById(Long userId);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
