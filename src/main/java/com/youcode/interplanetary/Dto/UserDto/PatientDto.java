package com.youcode.interplanetary.Dto.UserDto;

import com.youcode.interplanetary.Dto.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
@Component
@ToString
public class PatientDto {

//    private String patientId;

    private Demographics demographics;
    private MedicalHistory medicalHistory;
    private VitalSigns vitalSigns;
    private LaboratoryTests laboratoryTests;
    private HeartData heartData;
    private Lifestyle lifestyle;
    private ContactInformation contactInformation;

    private String CIN;


//    private Long timestamp;



}



