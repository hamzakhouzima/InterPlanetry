package com.youcode.interplanetary.HealthCareService.Controller;


import com.youcode.interplanetary.Dto.PatientDto;
import com.youcode.interplanetary.HealthCareService.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
