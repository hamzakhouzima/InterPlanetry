package com.youcode.interplanetary.HealthCareService.Service;

import com.youcode.interplanetary.Dto.PatientDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PatientService {


    ResponseEntity<String> savePatient(PatientDto personObject) throws Exception ;

//    ResponseEntity<InputStreamResource> getPatient(String id) throws Exception ;
//    ResponseEntity<String> getPatient(String id) throws Exception ;
    ResponseEntity<Map<String, Object>> getPatient(String  id) throws Exception ;
    //    ResponseEntity<String> getPatientByEmail(String email) throws Exception;
    ResponseEntity<Map<String, Object>> getPatientByEmail(String email) throws Exception;

    void updatePatient(String id, PatientDto personObject , String email) throws Exception;



}
