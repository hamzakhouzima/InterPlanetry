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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PatientServiceImpl implements PatientService {


    private final RestTemplate restTemplate;

    private final FormatConverter fc;

    @Autowired
    private PersonRepository personRepository;

    private final PatientMapper pm;


    public PatientServiceImpl(RestTemplate restTemplate , FormatConverter fc, PatientMapper pm) {
        this.restTemplate = restTemplate;
        this.fc = fc;
        this.pm = pm;
    }


//    public ResponseEntity<String> savePatient(Person personObject) {
//        Logger logger = LoggerFactory.getLogger(this.getClass());
//        try {
//            String url = "http://localhost:8080/upload";
//            String xmlData = fc.toXml(personObject);
//
//            // Create a Resource from the XML data
//            Resource xmlResource = new ByteArrayResource(xmlData.getBytes());
//
//            // Create multipart request with the 'file' part containing the XML data
//            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
//            parts.add("file", xmlResource);
//            parts.add("person", personObject);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parts, headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//
//            // Log successful response
//            logger.info("Uploaded patient data successfully. Response: {}", response.getBody());
//
//            return response;
//        } catch (Exception e) {
//            // Log exception
//            logger.error("Error uploading patient data: {}", e.getMessage(), e);
//
//            // Return internal server error response
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error uploading patient data: " + e.getMessage());
//        }
//    }
////////////////////////////////////////////////////////////////////////////////////////



//    public ResponseEntity<String> sendJsonPostRequest(String url, String jsonBody) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//
//        return responseEntity;
//    }






    private String convertPatientDtoToJson(PatientDto patientDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(patientDto);
    }



    @Override
    public ResponseEntity<String> savePatient(PatientDto patientDto) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        Person person = new Person();
        try {
            String apiUrl = "http://localhost:8082/upload";
            String patientJson = convertPatientDtoToJson(patientDto);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(patientJson, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);

            logger.info("Uploaded patient data successfully. Response: {}", responseEntity.getBody());

//            person.setCity(patientDto.getCity());


            person.setAge(patientDto.getDemographics().getAge());
            person.setGender(patientDto.getDemographics().getGender());
            person.setCity(String.valueOf(patientDto.getDemographics().getLocation().getRegion()));

            person.setHealthDataCID(extractCidFromResponse(responseEntity));

            person.setEmail(patientDto.getContactInformation().getEmail());
            person.setCountry(String.valueOf(patientDto.getDemographics().getLocation().getCountry()));

//            person.setZipcode(String.valueOf(patientDto.getDemographics().getLocation().ge));
//            person.setName(patientDto.);
//            person.setCountry(String.valueOf(patientDto.getDemographics().getLocation()));
//            person.setHealthDataCID(patientDto.getHealthDataCID());




//            private String name;
//            private int age; **
//            private String **gender;
//            private String *healthDataCID;
//            private String **email;
//            private String phone;
//            private String **city;
//            private String **country;
//            private String zipcode;



//////////////////////////////////////

//////////////////////////////////////



//this part where i should convert the data from the patientDto , to the entity so i can store the metadata

            personRepository.save(person);
//
            return responseEntity;
        } catch (IPFSException e) {
            logger.error("IPFS error occurred: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("IPFS error occurred: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error uploading patient data: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading patient data: " + e.getMessage());
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
