package com.youcode.interplanetary.HealthCareService.Service.Impl;

import com.youcode.interplanetary.GlobalConverters.FormatConverter;
import com.youcode.interplanetary.HealthCareService.Entity.PersonDetails.Person;
import com.youcode.interplanetary.HealthCareService.Service.PatientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class PatientServiceImpl implements PatientService {


    private final RestTemplate restTemplate;

    private final FormatConverter fc;

    public PatientServiceImpl(RestTemplate restTemplate , FormatConverter fc) {
        this.restTemplate = restTemplate;
        this.fc = fc;
    }


    public ResponseEntity<String> savePatient(Person personObject) throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            String url = "http://localhost:8081/upload";
            String xmlData = fc.toXml(personObject);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> requestEntity = new HttpEntity<>(xmlData, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            // Log successful response
            logger.info("Uploaded patient data successfully. Response: {}", response.getBody());

            return response;
        } catch (Exception e) {
            // Log exception
            logger.error("Error uploading patient data: {}", e.getMessage(), e);

            // Return internal server error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading patient data: " + e.getMessage());
        }


    }

}
