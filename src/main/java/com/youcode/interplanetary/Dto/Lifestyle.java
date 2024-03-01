package com.youcode.interplanetary.Dto;

//import com.youcode.networkstorageservice.Dto.nested.AlcoholConsumption;
//import com.youcode.networkstorageservice.Dto.nested.PhysicalActivity;
import com.youcode.interplanetary.Dto.nested.AlcoholConsumption;
import com.youcode.interplanetary.Dto.nested.PhysicalActivity;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
public class Lifestyle {
    private String smokingStatus;
    private AlcoholConsumption alcoholConsumption;
    private PhysicalActivity physicalActivity;
    private String diet;




}
