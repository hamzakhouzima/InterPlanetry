package com.youcode.interplanetary.HealthCareService.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youcode.interplanetary.Dto.ContactInformation;
import com.youcode.interplanetary.Dto.UserDto.PatientDto;
import com.youcode.interplanetary.GlobalConverters.FormatConverter;
import com.youcode.interplanetary.GlobalConverters.mappers.PatientMapper;
import com.youcode.interplanetary.GlobalException.IPFSException;
import com.youcode.interplanetary.HealthCareService.Entity.Person;
import com.youcode.interplanetary.HealthCareService.Repository.PersonRepository;
import com.youcode.interplanetary.HealthCareService.Service.PatientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class PatientServiceImpl implements PatientService {


    private final RestTemplate restTemplate;

    private final Map<String, String> patientCache = new ConcurrentHashMap<>();

    private final FormatConverter fc;
   private final HttpHeaders headers = new HttpHeaders();

    private PersonRepository personRepository;

    private final PatientMapper pm;


    public PatientServiceImpl(RestTemplate restTemplate , FormatConverter fc, PatientMapper pm , PersonRepository personRepository) {
        this.restTemplate = restTemplate;
        this.fc = fc;
        this.pm = pm;
        this.personRepository = personRepository;
    }
    @Value("${ipfs.api.endpoint}api/NetworkStorage")
    private String IpfsUrl;




    private String convertPatientDtoToJson(PatientDto patientDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(patientDto);
    }



    @Override
    public ResponseEntity<String> savePatient(PatientDto patientDto) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        Person person = new Person();
        try {


            if (patientDto == null || patientDto.getDemographics() == null || patientDto.getContactInformation() == null) {
                throw new IllegalArgumentException("Invalid patientDto: It cannot be null and must contain demographics and contact information.");
            }

            // Check specific fields for null values
            if (patientDto.getDemographics().getAge() == 0 || patientDto.getDemographics().getGender() == null ||
                    patientDto.getContactInformation().getEmail() == null) {
                throw new IllegalArgumentException("Invalid patientDto: Demographics age/gender and contact email cannot be null.");
            }
            ContactInformation contactInformation = patientDto.getContactInformation();
            if (contactInformation == null) {
                throw new IllegalArgumentException("Invalid patientDto: Contact information cannot be null.");
            }
            if (contactInformation.getEmail() == null) {
                throw new IllegalArgumentException("Invalid patientDto: Contact email cannot be null.");
            }

//            String apiUrl = "http://localhost:8082/upload";
            String patientJson = convertPatientDtoToJson(patientDto);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(patientJson, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(IpfsUrl + "/upload", HttpMethod.POST, requestEntity, String.class);
            logger.info("Uploaded patient data successfully. Response: {}", responseEntity.getBody());
//            person.setCity(patientDto.getCity());
            person.setAge(patientDto.getDemographics().getAge());
            person.setGender(patientDto.getDemographics().getGender());
            person.setCity(String.valueOf(patientDto.getDemographics().getLocation().getRegion()));
            person.setHealthDataCID(extractCidFromResponse(responseEntity));
            person.setEmail(patientDto.getContactInformation().getEmail());
            person.setCountry(String.valueOf(patientDto.getDemographics().getLocation().getCountry()));
            person.setCIN(patientDto.getCIN());
//this part where i should convert the data from the patientDto , to the entity so i can store the metadata
            personRepository.save(person);
            return responseEntity;
        } catch (IPFSException e) {
            logger.error("IPFS error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("IPFS error occurred: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error uploading patient data: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading patient data: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<Map<String, Object>> getPatient(String id) throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        String formattedJson = "";
        try {
            // Check if the patient data is already cached
            String cachedPatientData = patientCache.get(id);

//            ObjectMapper mapper = new ObjectMapper();
            if (cachedPatientData != null && !cachedPatientData.isEmpty()) {
                // Create an instance of ObjectMapper
                ObjectMapper mapper = new ObjectMapper();

                try {
                    // Convert the JSON string to a JSON object
                    Object json = mapper.readValue(cachedPatientData, Object.class);

                    // Convert the JSON object to a well-formatted JSON string with 2-space indentation
                     formattedJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

                    // Output the well-formatted JSON string
                    System.out.println(formattedJson);
                } catch (Exception e) {
                    // Handle any exceptions
                    e.printStackTrace();
                }
            } else {
                System.out.println("Cached patient data is null or empty.");
            }
            // make GET request to IPFS endpoint
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(IpfsUrl + "/file/" + id, String.class);
            String patientData = responseEntity.getBody();

            // cache the patient data
            patientCache.put(id, patientData);
            logger.info("Downloaded patient data successfully for ID: {}", id);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("patientData", formattedJson);
            return ResponseEntity.ok()
                    //this was commented , it could be an issue hhhhhh
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(responseData);
        } catch (RestClientException e) {
            logger.error("Error downloading patient data from IPFS: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error downloading patient data");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorResponse);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getPatientByEmail(String email) throws Exception {
        Person person = personRepository.findPersonByEmail(email);
        if (person != null) {
            ResponseEntity<Map<String, Object>> responseEntity = getPatient(person.getHealthDataCID());
            HttpStatus statusCode = (HttpStatus) responseEntity.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                Map<String, Object> responseData = new HashMap<>();

                responseData.put("patientData", responseEntity.getBody().get("patientData"));
                return ResponseEntity.ok(responseData);
            } else {
                // Handle error response from getPatient method
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Error retrieving patient data");
                return ResponseEntity.status(statusCode).body(errorResponse);
            }
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Patient not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
  //////##########################################################//#####################################################////////////////// "###################
    @Override
//    @Transactional
    public void updatePatient(String id, PatientDto personObject, String email) {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // fetch the existing patient data from IPFS
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(IpfsUrl + "/file/" + id, HttpMethod.GET, null, byte[].class);
            byte[] patientData = responseEntity.getBody();

            // convert byte array to JSON string
            assert patientData != null;
            String jsonPatientData = new String(patientData);

            // parse the JSON string to a Map for easy manipulation
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> patientMap = mapper.readValue(jsonPatientData, new TypeReference<Map<String, Object>>(){});

            // update the fields in the patient object based on the provided DTO
            updateDemographics(personObject, patientMap);
            updateMedicalHistory(personObject, patientMap);
            updateContactInformation(personObject, patientMap);

            // convert the updated patient map back to JSON string
            String updatedJsonPatientData = mapper.writeValueAsString(patientMap);

            ObjectMapper objectMapper = new ObjectMapper();
            PatientDto patientDTO = objectMapper.readValue(updatedJsonPatientData, PatientDto.class);

            // save the updated patient data back to IPFS
            String newPatientCid = String.valueOf(savePatient(patientDTO));
            try{
                personRepository.updateCID(id, newPatientCid);
                logger.info("Patient data updated successfully for ID: {}", id);

            }catch(Exception e){
                e.printStackTrace();
            }
        } catch (IOException | RestClientException e) {
            logger.error("Error updating patient data from IPFS: {}", e.getMessage(), e);
        }
    }

    private void updateDemographics(PatientDto personObject, Map<String, Object> patientMap) {
        if (personObject.getDemographics() != null) {
            Map<String, Object> demographicsMap = (Map<String, Object>) patientMap.get("demographics");
            if (personObject.getDemographics().getAge() != 0) {
                demographicsMap.put("age", personObject.getDemographics().getAge());
            }
            if (personObject.getDemographics().getGender() != null) {
                demographicsMap.put("gender", personObject.getDemographics().getGender());
            }
            if (personObject.getDemographics().getEthnicity() != null) {
                demographicsMap.put("ethnicity", personObject.getDemographics().getEthnicity());
            }
            if (personObject.getDemographics().getLocation() != null) {
                Map<String, String> locationMap = (Map<String, String>) demographicsMap.get("location");
                if (locationMap == null) {
                    locationMap = new HashMap<>();
                    demographicsMap.put("location", locationMap);
                }
                if (personObject.getDemographics().getLocation().getCountry() != null) {
                    locationMap.put("country", personObject.getDemographics().getLocation().getCountry());
                }
                if (personObject.getDemographics().getLocation().getRegion() != null) {
                    locationMap.put("region", personObject.getDemographics().getLocation().getRegion());
                }
            }
        }
    }

    private void updateMedicalHistory(PatientDto personObject, Map<String, Object> patientMap) {
        if (personObject.getMedicalHistory() != null) {
            Map<String, Object> medicalHistoryMap = (Map<String, Object>) patientMap.get("medicalHistory");
            if (personObject.getMedicalHistory().isFamilyHistoryHeartDisease()) {
                medicalHistoryMap.put("familyHistoryHeartDisease", true);
            }
            if (personObject.getMedicalHistory().getExistingConditions() != null) {
                medicalHistoryMap.put("existingConditions", personObject.getMedicalHistory().getExistingConditions());
            }
        }
    }

    private void updateContactInformation(PatientDto personObject, Map<String, Object> patientMap) {
        if (personObject.getContactInformation() != null) {
            Map<String, Object> contactInformationMap = (Map<String, Object>) patientMap.get("contactInformation");
            if (personObject.getContactInformation().getFirst_name() != null) {
                contactInformationMap.put("firstName", personObject.getContactInformation().getFirst_name());
            }
            if (personObject.getContactInformation().getLast_name() != null) {
                contactInformationMap.put("lastName", personObject.getContactInformation().getLast_name());
            }
            if (personObject.getContactInformation().getPrimaryPhone() != null) {
                contactInformationMap.put("primaryPhone", personObject.getContactInformation().getPrimaryPhone());
            }
            if (personObject.getContactInformation().getEmail() != null) {
                contactInformationMap.put("email", personObject.getContactInformation().getEmail());
            }
            if (personObject.getContactInformation().getEmergencyContact() != null) {
                Map<String, String> emergencyContactMap = (Map<String, String>) contactInformationMap.get("emergencyContact");
                if (emergencyContactMap == null) {
                    emergencyContactMap = new HashMap<>();
                    contactInformationMap.put("emergencyContact", emergencyContactMap);
                }
                if (personObject.getContactInformation().getEmergencyContact().getName() != null) {
                    emergencyContactMap.put("name", personObject.getContactInformation().getEmergencyContact().getName());
                }
                if (personObject.getContactInformation().getEmergencyContact().getPhone() != null) {
                    emergencyContactMap.put("phone", personObject.getContactInformation().getEmergencyContact().getPhone());
                }
                if (personObject.getContactInformation().getEmergencyContact().getRelationship() != null) {
                    emergencyContactMap.put("relationship", personObject.getContactInformation().getEmergencyContact().getRelationship());
                }
            }
        }
    }


//////##########################################################  //#####################################################////////////////// "###################


    private String extractCidFromResponse(ResponseEntity<String> responseEntity) {
        String body = responseEntity.getBody();
        System.out.println("Response body: " + body);

        //  the response body is in the format {"cid": "CID"}
        String cidPrefix = "\"cid\": \"";
        int startIndex = body.indexOf(cidPrefix);
        if (startIndex != -1) {
            startIndex += cidPrefix.length();
            int endIndex = body.indexOf("\"", startIndex);
            if (endIndex != -1) {
                return body.substring(startIndex, endIndex);
            }
        }
        return null; // CID not found in the response
    }



//    private String convertInputStreamToString(InputStream inputStream) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            stringBuilder.append(line);
//        }
//        return stringBuilder.toString();
//    }








//private String convertToJsonString(String  object) {
//    ObjectMapper mapper = new ObjectMapper();
//
//    try {
//        // Parse the JSON string into a JSON object
//        Object json = mapper.readValue(jsonString, Object.class);
//
//        // Convert the JSON object back to a formatted JSON string
//        String formattedJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
//
//        // Now, formattedJsonString contains the formatted JSON string
//        System.out.println(formattedJsonString);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}




}
