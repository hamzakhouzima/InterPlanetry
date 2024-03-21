package com.youcode.interplanetary.Dto.UserDto;

import lombok.Data;

import java.sql.Date;



@Data
public class UserDto {


    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private String PhoneNumber;
    private Date DateOfBirth;


}
