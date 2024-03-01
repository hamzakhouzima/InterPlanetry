package com.youcode.interplanetary.HealthCareService.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youcode.interplanetary.Dto.PatientDto;
import com.youcode.interplanetary.GlobalConverters.FormatConverter;
import com.youcode.interplanetary.GlobalConverters.PatientMapper;
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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class PatientServiceImpl implements PatientService {


    private final RestTemplate restTemplate;

    private final FormatConverter fc;
   private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    private PersonRepository personRepository;

    private final PatientMapper pm;


    public PatientServiceImpl(RestTemplate restTemplate , FormatConverter fc, PatientMapper pm) {
        this.restTemplate = restTemplate;
        this.fc = fc;
        this.pm = pm;
    }
    @Value("${ipfs.api.endpoint}")
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
//            String apiUrl = "http://localhost:8082/upload";
            String patientJson = convertPatientDtoToJson(patientDto);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(patientJson, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(IpfsUrl, HttpMethod.POST, requestEntity, String.class);
            logger.info("Uploaded patient data successfully. Response: {}", responseEntity.getBody());
//            person.setCity(patientDto.getCity());
            person.setAge(patientDto.getDemographics().getAge());
            person.setGender(patientDto.getDemographics().getGender());
            person.setCity(String.valueOf(patientDto.getDemographics().getLocation().getRegion()));
            person.setHealthDataCID(extractCidFromResponse(responseEntity));
            person.setEmail(patientDto.getContactInformation().getEmail());
            person.setCountry(String.valueOf(patientDto.getDemographics().getLocation().getCountry()));
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
    public ResponseEntity<String> getPatient(String id) throws Exception {
        Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            // Make GET request to IPFS endpoint
            ResponseEntity<String> responseEntity = restTemplate.exchange(IpfsUrl + "file/" + id, HttpMethod.GET, null, String.class);
            String patientData = responseEntity.getBody();

            // Save patient data to file
//            String filename = "patient_" + id + ".json";
            Path filePath = Paths.get("C:", "Users", "Youcode", "Downloads", id + ".json");
            Files.write(filePath, patientData.getBytes());

            logger.info("Downloaded patient data successfully. File saved at: {}", filePath);

            return responseEntity;
        } catch (RestClientException e) {
            logger.error("Error downloading patient data from IPFS: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error downloading patient data: " + e.getMessage());
        }
    }



    private String extractCidFromResponse(ResponseEntity<String> responseEntity) {
        String body = responseEntity.getBody();
        System.out.println("Response body: " + body);

        // Assuming the response body is in the format {"cid": "YOUR_CID_HERE"}
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


}
