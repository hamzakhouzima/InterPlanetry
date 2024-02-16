package com.youcode.interplanetary.HealthCareService.Entity.HealthDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthData {
    private String patientId;
    private String bloodType;
    private List<String> allergies;
    private List<MedicalRecord> medicalHistory;


    public static class MedicalRecord {
        private String date;
        private String diagnosis;
        private String prescription;

    }
}