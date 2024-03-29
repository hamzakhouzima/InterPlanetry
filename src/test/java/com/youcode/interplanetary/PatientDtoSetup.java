package com.youcode.interplanetary;

//import com.youcode.interplanetary.Dto.UserDto.Demographics;
//import com.youcode.interplanetary.Dto.UserDto.MedicalHistory;
//import com.youcode.interplanetary.Dto.UserDto.VitalSigns;
//import com.youcode.interplanetary.Dto.UserDto.LaboratoryTests;
//import com.youcode.interplanetary.Dto.UserDto.Lifestyle;
//import com.youcode.interplanetary.Dto.UserDto.ContactInformation;
//import com.youcode.interplanetary.Dto.UserDto.PatientDto;

import com.youcode.interplanetary.Dto.*;
import com.youcode.interplanetary.Dto.UserDto.PatientDto;
import com.youcode.interplanetary.Dto.nested.*;
import lombok.Data;
import lombok.Setter;
//import com.youcode.interplanetary.Dto.UserDto.VitalSigns.BloodPressure;

import java.util.List;

@Setter
public class PatientDtoSetup {

    public static PatientDto createSamplePatientDto() {
        PatientDto patientDto = new PatientDto();

        // Set Demographics
        Demographics demographics = new Demographics();
        demographics.setAge(45);
        demographics.setGender("male");
        demographics.setEthnicity("african");
        Location location = new Location();

        location.setCountry("tunisia");
        location.setRegion("souissi");
        demographics.setLocation(location);
        patientDto.setDemographics(demographics);

        // Set MedicalHistory
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setFamilyHistoryHeartDisease(false);
        medicalHistory.setExistingConditions(List.of(new String[]{"Asthma", "Hypertension"}));
        medicalHistory.setPreviousSurgeries(List.of(new String[]{"Appendectomy"}));
        medicalHistory.setMedications(List.of(new String[]{"Albuterol", "Lisinopril"}));
        patientDto.setMedicalHistory(medicalHistory);

        // Set VitalSigns
        VitalSigns vitalSigns = new VitalSigns();
        vitalSigns.setBloodPressure(new BloodPressure(120, 8));
        vitalSigns.setHeartRate(new HeartRate(70, 100));
        vitalSigns.setRespiratoryRate(16);
        vitalSigns.setBmi(25.2);
        patientDto.setVitalSigns(vitalSigns);

        // Set LaboratoryTests
        LaboratoryTests laboratoryTests = new LaboratoryTests();
        laboratoryTests.setLipidProfile(new LipidProfile(200, 130, 50, 150));
        laboratoryTests.setBloodSugar(new BloodSugar(85, 120));
        laboratoryTests.setElectrolytes(new Electrolytes(4.2, 138, 102));
        laboratoryTests.setCrp(0.5);
        patientDto.setLaboratoryTests(laboratoryTests);

        // Set Lifestyle
        Lifestyle lifestyle = new Lifestyle();
        lifestyle.setSmokingStatus("Non-smoker");
        AlcoholConsumption alcoholConsumption = new AlcoholConsumption();
        alcoholConsumption.setFrequency("Occasional");
        alcoholConsumption.setAmount(1);
        lifestyle.setAlcoholConsumption(alcoholConsumption);
        PhysicalActivity physicalActivity = new PhysicalActivity();
        physicalActivity.setFrequency("3 times per week");
        physicalActivity.setDuration("30 minutes");
        lifestyle.setPhysicalActivity(physicalActivity);
        lifestyle.setDiet("Mediterranean");
        patientDto.setLifestyle(lifestyle);

        // Set ContactInformation
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setFirst_name("hamza");
        contactInformation.setLast_name("khouzima");
        contactInformation.setPrimaryPhone("+1234567890");
        contactInformation.setSecondaryPhone("+1555555555");
        contactInformation.setEmail("222@hamza.com");
        EmergencyContact emergencyContact = new EmergencyContact();
        emergencyContact.setName("khalid khouzima");
        emergencyContact.setPhone("+000000000");
        emergencyContact.setRelationship("not related");
        contactInformation.setEmergencyContact(emergencyContact);
        patientDto.setContactInformation(contactInformation);

        return patientDto;
    }

//    public static void main(String[] args) {
//        // Create a sample PatientDto object
//        PatientDto patientDto = createSamplePatientDto();
//
//        // Print the PatientDto object
//        System.out.println(patientDto);
//    }
}
