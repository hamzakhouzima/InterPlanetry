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
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"email"})})

public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
//    @SequenceGenerator(name = "person_seq", sequenceName = "Person_SEQ", allocationSize = 1)
//    private Long id;

    private String name;
    private int age;
    private String gender;
    private String healthDataCID;

//    @Column(unique = true);
    private String email;
//TODO : check this Bug so the email can be unique
    private String phone;
    private String street;
    private String city;
    private String country;
    private String zipcode;


    private String CIN;






}
