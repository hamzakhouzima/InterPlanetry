package com.youcode.interplanetary.HealthCareService.Controller;


import com.youcode.interplanetary.Dto.EmailRequest;
import com.youcode.interplanetary.Dto.UserDto.PatientDto;
import com.youcode.interplanetary.HealthCareService.Service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/interplanetary2")
public class PatientController {

    @Autowired
    private PatientService patientService;


    @PostMapping(value = "/AddPatient")
    public ResponseEntity<String> AddPatient(@RequestBody PatientDto patient) throws Exception {

        try{
            patientService.savePatient(patient);
            return ResponseEntity.ok("Patient added successfully.");
        }catch(Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }



    @GetMapping(value = "/GetPatient/{id}")
    public ResponseEntity<?>  getPatient(@PathVariable String id) throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        try{
            logger.info("Get patient by id : " + id);
            return ResponseEntity.ok(patientService.getPatient(id));
        }catch(Exception e ){
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
    }
}

//TODO :this does not work currently we should solve it
@PostMapping("/patient/")
public ResponseEntity<Map<String, Object>> getPatientByEmail(@RequestBody EmailRequest emailRequest) {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    String email = emailRequest.getEmail();
    logger.info("Received request for patient data with email: {}", email);

    try {
        // Validate email format
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Call service method to retrieve patient data
        ResponseEntity<Map<String, Object>> responseEntity = patientService.getPatientByEmail(email);

        // Check if patient data was found
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(responseEntity.getBody());
        }
        else if (responseEntity.getStatusCode().is4xxClientError()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    } catch (IllegalArgumentException e) {
        // Handle invalid email format
        logger.error("Invalid email format: {}", e.getMessage());
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    } catch (Exception e) {
        // Handle other exceptions
        logger.error("Error retrieving patient data: {}", e.getMessage(), e);
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error retrieving patient data");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}





    @PutMapping(value = "/UpdatePatient/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable(required = false , value = "id") String id, @RequestBody PatientDto personObject, @RequestBody(required = false) String email) throws Exception {
        try {
            patientService.updatePatient(id , personObject , email);
            return ResponseEntity.ok("Patient updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        // Implement your email validation logic


    return true;
    }


}







