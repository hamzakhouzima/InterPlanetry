package com.youcode.interplanetary.HealthCareService.Service;

import com.youcode.interplanetary.HealthCareService.Entity.PersonDetails.Person;
import org.springframework.http.ResponseEntity;

public interface PatientService {


    ResponseEntity<String> savePatient(Person personObject) throws Exception ;




}
