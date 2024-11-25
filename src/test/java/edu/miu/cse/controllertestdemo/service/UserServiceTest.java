package edu.miu.cse.controllertestdemo.service;

import edu.miu.cse.controllertestdemo.dto.request.UserRequestDTO;
import edu.miu.cse.controllertestdemo.dto.response.UserResponseDTO;
import edu.miu.cse.controllertestdemo.model.User;
import edu.miu.cse.controllertestdemo.repository.UserRepository;
import edu.miu.cse.controllertestdemo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void createUser_validInput_returnsCreatedUser() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("thiri","mon","Thiri");
        UserResponseDTO userResponseDTO = new UserResponseDTO("tmon");
        User user = new User("thiri","mon","tmon");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Optional<UserResponseDTO> userResponseDTO1 = userService.createUser(userRequestDTO);

        Assertions.assertTrue(userResponseDTO1.isPresent());
        Assertions.assertEquals(userResponseDTO.username(), userResponseDTO1.get().username());
    }

    @Test
    public void updateUser_validInput_returnsUpdatedUser() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("thiri","mon","Thiri");
        UserResponseDTO userResponseDTO = new UserResponseDTO("tmon");
        User user = new User("thiri","mon","tmon");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Optional<UserResponseDTO> userResponseDTO1 = userService.updateUser("thiri",userRequestDTO);

        Assertions.assertTrue(userResponseDTO1.isPresent());
        Assertions.assertEquals(userResponseDTO.username(), userResponseDTO1.get().username());
    }
}
