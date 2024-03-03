package com.youcode.interplanetary.HealthCareService.Service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.youcode.interplanetary.Dto.PatientDto;
import org.apache.coyote.Response;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface PatientService {


    ResponseEntity<String> savePatient(PatientDto personObject) throws Exception ;

//    ResponseEntity<InputStreamResource> getPatient(String id) throws Exception ;
    ResponseEntity<String> getPatient(String id) throws Exception ;
    ResponseEntity<String> getPatientByEmail(String email) throws Exception;


}
