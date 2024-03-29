package com.youcode.interplanetary;

import com.youcode.interplanetary.Dto.ContactInformation;
import com.youcode.interplanetary.Dto.Demographics;
import com.youcode.interplanetary.Dto.UserDto.PatientDto;
import com.youcode.interplanetary.GlobalException.IPFSException;
import com.youcode.interplanetary.HealthCareService.Entity.Person;
import com.youcode.interplanetary.HealthCareService.Repository.PersonRepository;
import com.youcode.interplanetary.HealthCareService.Service.Impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PatientServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSavePatient_Success() {
        // Mocking
        PatientDtoSetup patientDtoSetup = new PatientDtoSetup();
        // Set non-null values for patientDto fields
        // For demonstration purposes, you can set some dummy values
        // Replace these with actual values based on your application's requirements
//        patientDto.setDemographics(new Demographics()); // Assume Demographics is a nested object
//        patientDto.setContactInformation(new ContactInformation()); // Assume ContactInformation is a nested object
            PatientDto patientDto = patientDtoSetup.createSamplePatientDto();
        // Mock successful response from restTemplate.exchange()
        ResponseEntity<String> mockResponseEntity = ResponseEntity.ok().body("CID123");
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenReturn(mockResponseEntity);

        // Test
        ResponseEntity<String> responseEntity = patientService.savePatient(patientDto);

        // Verify
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("CID123", responseEntity.getBody());
        // Add more verification as needed
    }

    @Test
    public void testSavePatient_IPFSException() throws IPFSException {
        // Mocking
        PatientDto patientDto = new PatientDto(); // create a sample patientDto
        when(restTemplate.exchange(anyString(), any(), any(), eq(String.class)))
                .thenThrow(new RestClientException("Error"));

        // Test
        ResponseEntity<String> responseEntity = patientService.savePatient(patientDto);

        // Verify
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    // Similarly, write tests for other methods like getPatient, getPatientByEmail, updatePatient, etc.
}
