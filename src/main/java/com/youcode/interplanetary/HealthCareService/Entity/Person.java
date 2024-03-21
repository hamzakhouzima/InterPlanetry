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
//    @Column(name = "id", nullable = false)
    private Long id;
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
//    @SequenceGenerator(name = "person_seq", sequenceName = "Person_SEQ", allocationSize = 1)
//    private Long id;

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


    private String CIN;






}
