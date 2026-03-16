package com.dhatvibs.dashboard.service;

import com.dhatvibs.dashboard.dto.UserRequestDTO;
import com.dhatvibs.dashboard.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO requestDTO);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO);

    void deleteUser(Long id);

}