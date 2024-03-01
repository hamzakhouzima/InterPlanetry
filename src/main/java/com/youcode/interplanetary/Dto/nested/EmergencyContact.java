package com.youcode.interplanetary.Dto.nested;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
@Component
public  class EmergencyContact {
    private String name;
    private String phone;
    private String relationship;
}