package edu.miu.cse.controllertestdemo.service;

import edu.miu.cse.controllertestdemo.dto.request.UserRequestDTO;
import edu.miu.cse.controllertestdemo.dto.response.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponseDTO> createUser(UserRequestDTO userRequestDTO);
    Optional<UserResponseDTO> updateUser(String username, UserRequestDTO userRequestDTO);
    Optional<UserResponseDTO> getUserByName(String name);
    void deleteUserByName(String name);
    List<UserResponseDTO> getAllUsers();
}
