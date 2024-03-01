package com.youcode.interplanetary.HealthCareService.Controller;


import com.youcode.interplanetary.Dto.PatientDto;
import com.youcode.interplanetary.HealthCareService.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
        try{
            return ResponseEntity.ok(patientService.getPatient(id));
        }catch(Exception e ){
            return ResponseEntity.badRequest().body(e.getMessage());
    }
}

//TODO :this does not work currently we should solve it
@PostMapping("/patient")
public ResponseEntity<?> getPatientByEmail(@RequestBody String email) {
    try {
        // Validate email format
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Call service method to retrieve patient data
        ResponseEntity<String> response = patientService.getPatientByEmail(email);

        // Check if patient data was found
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.notFound().build();
        }
    } catch (IllegalArgumentException e) {
        // Handle invalid email format
        return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
        // Handle other exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving patient data: " + e.getMessage());
    }
}

    // Helper method to validate email format
    private boolean isValidEmail(String email) {
        // Implement email validation logic here
        return true;
    }


}







