//package com.youcode.interplanetary.HealthCareService.Service.Impl;
//
//
//import com.youcode.interplanetary.Dto.UserDto.UserDto;
//import com.youcode.interplanetary.GlobalConverters.mappers.UserMapper;
//import com.youcode.interplanetary.HealthCareService.Entity.User;
//import com.youcode.interplanetary.HealthCareService.Repository.UserRepository;
//import com.youcode.interplanetary.HealthCareService.Service.UserService;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@Data
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService{
//
//    private final UserRepository userRepository;
//
//    private final UserMapper userMapper;
//
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
//
//    @Override
//    public void RegisterUser(UserDto user) {
//
//        try {
//
//            User userEntity = userMapper.convertToEntity(user);
//
//            userRepository.save(userEntity);
//
//            logger.info("User saved successfully: {}", userEntity.getUsername());
//        } catch (Exception e) {
//            logger.error("Error registering user: {}", e.getMessage(), e);
//
//        }
//    }
//
//    @Override
//    public User findUserByEmail(String email) {
//
//
//        return userRepository.findUserByEmail(email);
//
//    }
//
//    @Override
//    public void deleteUserByEmail(String email) {
//
//    }
//
//    @Override
//    public List<User> getAllUsers() {
//        return null;
//    }
//
//    @Override
//    public User getUserById(Long id) {
//        return null;
//    }
//
////    @Override
////    public User GetUser() {
////        return null;
////    }
////
////    @Override
////    public void DeleteUser() {
////
////    }
//}
