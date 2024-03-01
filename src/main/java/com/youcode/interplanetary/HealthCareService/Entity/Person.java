package com.youcode.interplanetary.HealthCareService.Entity;

//import com.youcode.interplanetary.HealthCareService.Entity.PersonDetails.Adress;
//import com.youcode.interplanetary.HealthCareService.Entity.PersonDetails.Contact;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    private String name;
    private int age;
    private String gender;
    private String healthDataCID;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String country;
    private String zipcode;





}
