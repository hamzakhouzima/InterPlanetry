//package com.youcode.interplanetary.HealthCareService.Controller;
//
//
//import com.youcode.interplanetary.Dto.UserDto.UserDto;
//import com.youcode.interplanetary.HealthCareService.Service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.apache.coyote.Response;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/interplanetary2/HealthCareService")
//public class UserController {
//
//    private final UserService userService;
//
//    @GetMapping("/Register")
//    public ResponseEntity<String> getHealthCareService(UserDto userDto) {
//
//        userService.RegisterUser(userDto);
//        return ResponseEntity.status(HttpStatus.OK).body("User added successfully");
//
//    }
//
//
//
//
//
//
//}