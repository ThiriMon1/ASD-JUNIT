package edu.miu.cse.controllertestdemo.service.impl;

import edu.miu.cse.controllertestdemo.dto.request.UserRequestDTO;
import edu.miu.cse.controllertestdemo.dto.response.UserResponseDTO;
import edu.miu.cse.controllertestdemo.model.User;
import edu.miu.cse.controllertestdemo.repository.UserRepository;
import edu.miu.cse.controllertestdemo.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public Optional<UserResponseDTO> createUser(UserRequestDTO userRequestDTO) {
        if(userRepository.findByUsername(userRequestDTO.username()).isPresent()){
            return Optional.empty();
        }
        User user=new User(userRequestDTO.firstName(), userRequestDTO.lastName(), userRequestDTO.username());
        User savedUser=userRepository.save(user);
        UserResponseDTO userResponseDTO = new UserResponseDTO(savedUser.getUsername());

        return Optional.of(userResponseDTO);
    }

    @Override
    public Optional<UserResponseDTO> updateUser(String username, UserRequestDTO userRequestDTO) {
        Optional<User> userFound=userRepository.findByUsername(username);
        if(userFound.isPresent()){
            User user=userFound.get();
            user.setUsername(userRequestDTO.username());
            user.setFirstName(userRequestDTO.firstName());
            user.setLastName(userRequestDTO.lastName());
            User savedUser=userRepository.save(user);
            UserResponseDTO userResponseDTO = new UserResponseDTO(savedUser.getUsername());
            return Optional.of(userResponseDTO);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserResponseDTO> getUserByName(String name) {
        Optional<User> userFound=userRepository.findByUsername(name);
        if(userFound.isPresent()){
            UserResponseDTO userResponseDTO = new UserResponseDTO(userFound.get().getUsername());
            return Optional.of(userResponseDTO);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteUserByName(String name) {
        getUserByName(name).ifPresent(userResponseDTO -> {
            userRepository.deleteByUsername(userResponseDTO.username());
        });

    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users=userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS=new ArrayList<>();
        for(User user:users){
            UserResponseDTO userResponseDTO=new UserResponseDTO(user.getUsername());
            userResponseDTOS.add(userResponseDTO);
        }
        return userResponseDTOS;
    }
}
