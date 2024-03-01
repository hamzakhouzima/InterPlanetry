package com.youcode.interplanetary.Dto;

//import com.youcode.InterPlanetary2.Dto.nested.Location;
import com.youcode.interplanetary.Dto.nested.Location;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
public class Demographics {
    private int age;
    private String gender;
    private String ethnicity;
    private Location location;


}