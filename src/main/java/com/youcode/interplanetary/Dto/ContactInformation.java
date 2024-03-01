package com.youcode.interplanetary.Dto;

//import com.youcode.networkstorageservice.Dto.nested.EmergencyContact;
import com.youcode.interplanetary.Dto.nested.EmergencyContact;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
public class ContactInformation {
//    @NotNull
    private String primaryPhone;
    private String secondaryPhone;
    @Email
    private String email;
    private EmergencyContact emergencyContact;

}
