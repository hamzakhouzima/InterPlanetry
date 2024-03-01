package com.youcode.interplanetary.HealthCareService.Service;

import com.youcode.interplanetary.Dto.PatientDto;
import org.springframework.http.ResponseEntity;

public interface PatientService {


    ResponseEntity<String> savePatient(PatientDto personObject) throws Exception ;




}
