package edu.miu.cse.controllertestdemo.controller;

import edu.miu.cse.controllertestdemo.dto.request.UserRequestDTO;
import edu.miu.cse.controllertestdemo.dto.response.UserResponseDTO;
import edu.miu.cse.controllertestdemo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//here, the spring application context will not be loaded
@ExtendWith(MockitoExtension.class)
public class UserControllerTest2 {
    //Mock userservice and inject it into usercontroller
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    public void createUser_validInput_returnsCreateUser(){
        UserRequestDTO userRequestDTO = new UserRequestDTO("thiri","mon", "tmon");
        UserRequestDTO userRequestDTO1 = new UserRequestDTO("thiriiiiiii","mon", "tmon");
     //   User savedUser=new User("thiri","mon", "tmon");
        UserResponseDTO userResponseDTO = new UserResponseDTO("tmonn");

        Mockito.when(userService.createUser(Mockito.any(UserRequestDTO.class))).thenReturn(Optional.of(userResponseDTO));
        ResponseEntity<UserResponseDTO> responseEntity=userController.createUser(userRequestDTO1);

        assert responseEntity.getStatusCode()== HttpStatus.CREATED;
      //  assert responseEntity.getBody()==userResponseDTO;
        Assertions.assertEquals(userResponseDTO,responseEntity.getBody());
    }

    @Test
    public void updateUser_validInput_returnsUpdateUser(){
        String username = "tmon";
        UserRequestDTO userRequestDTO = new UserRequestDTO("thiri","mon", "tmon");
        UserResponseDTO userResponseDTO = new UserResponseDTO("tmonn");

        Mockito.when(userService.updateUser(username,userRequestDTO)).thenReturn(Optional.of(userResponseDTO));
        ResponseEntity<UserResponseDTO> responseEntity=userController.updateUser(username,userRequestDTO);

        assert responseEntity.getStatusCode()== HttpStatus.OK;
        Assertions.assertEquals(userResponseDTO,responseEntity.getBody());
    }

    @Test
    public void updatedUser_inValidUsername_noUpdate(){
        UserRequestDTO userRequestDTO = new UserRequestDTO("thiri","mon", "tmon");
        String username = "tmonn";
        Optional<UserResponseDTO> userResponseDTO = Optional.empty();

        Mockito.when(userService.updateUser(username,userRequestDTO)).thenReturn(userResponseDTO);
        ResponseEntity<UserResponseDTO> responseEntity=userController.updateUser(username,userRequestDTO);
        assert responseEntity.getStatusCode()== HttpStatus.NOT_FOUND;
    }

    @Test
    public void deleteUser_validInput(){
        String username = "tmonn";

        Mockito.doNothing().when(userService).deleteUserByName(username);

        ResponseEntity<UserResponseDTO> responseEntity = userController.deleteUser(username);
        assert responseEntity.getStatusCode()== HttpStatus.NO_CONTENT;
    }

    @Test
    public void getUsers_returnsUserList(){
        List<UserResponseDTO> userResponseDTOs = new ArrayList<>();
        userResponseDTOs.add(new UserResponseDTO("tmo"));
        userResponseDTOs.add(new UserResponseDTO("tmonn"));

        Mockito.when(userService.getAllUsers()).thenReturn(userResponseDTOs);

        ResponseEntity<List<UserResponseDTO>> responseEntity = userController.getUsers();
        assert responseEntity.getStatusCode()== HttpStatus.OK;
        Assertions.assertEquals(userResponseDTOs,responseEntity.getBody());

    }

}
