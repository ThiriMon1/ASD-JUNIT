package edu.miu.cse.controllertestdemo.controller;

import com.google.gson.Gson;
import edu.miu.cse.controllertestdemo.dto.request.UserRequestDTO;
import edu.miu.cse.controllertestdemo.dto.response.UserResponseDTO;
import edu.miu.cse.controllertestdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.swing.text.html.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)//from spring: It loads the application context, but not fully
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean //spring framework
    private UserService userService;

    @Test
    void createUser() throws Exception {
        UserRequestDTO userRequestDTO=
                new UserRequestDTO("thiri","mon","tmon");
        UserResponseDTO userResponseDTO=
                new UserResponseDTO("tmon");
        Mockito.when(userService.createUser(userRequestDTO)).thenReturn(Optional.of(userResponseDTO));

//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/api/v1/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\n" +
//                                "    \"firstName\":\"thiri\",\n" +
//                                "    \"lastName\":\"mon\",\n" +
//                                "    \"username\":\"tmon\"\n" +
//                                "}")
//        ).andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().json("{\n" +
//                        "\"username\":\"tmon\"\n" +
//                        "}"))
//                .andDo(MockMvcResultHandlers.print());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(userRequestDTO))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(MockMvcResultMatchers.content().json(new Gson().toJson(userResponseDTO)));
    }

    @Test
    void createUser_BadRequest() throws Exception {
        UserRequestDTO userRequestDTO=
                new UserRequestDTO("thiri","mon","tmon");

        Mockito.when(userService.createUser(userRequestDTO)).thenReturn(Optional.empty());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(userRequestDTO))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getUsers() throws Exception {
        List<UserResponseDTO> userResponseDTOs= new ArrayList<>();
        userResponseDTOs.add(new UserResponseDTO("tmon"));
        userResponseDTOs.add(new UserResponseDTO("mon"));

        Mockito.when(userService.getAllUsers()).thenReturn(userResponseDTOs);

             mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/users")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(MockMvcResultMatchers.content().json(new Gson().toJson(userResponseDTOs)));
    }


    @Test
    void updateUser() throws Exception {
        UserRequestDTO userRequestDTO=
                new UserRequestDTO("thiri","mon","tmonnnn");
        String username ="tmon";
        UserResponseDTO userResponseDTO=
                new UserResponseDTO("tmon");
        Mockito.when(userService.updateUser(username,userRequestDTO)).thenReturn(Optional.of(userResponseDTO));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/users/"+username)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new Gson().toJson(userRequestDTO))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(MockMvcResultMatchers.content().json(new Gson().toJson(userResponseDTO)));
    }

    @Test
    void deleteUser() throws Exception {
        String username ="tmon";
        Mockito.doNothing().when(userService).deleteUserByName(username);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/users/"+username)

                )
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(MockMvcResultMatchers.status().isNoContent());

    }
}