package com.youcode.interplanetary.HealthCareService.Entity.PersonDetails;

//import com.youcode.interplanetary.HealthCareService.Entity.PersonDetails.Adress;
//import com.youcode.interplanetary.HealthCareService.Entity.PersonDetails.Contact;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {


    private int id;
    private String name;
    private int age;
    private String gender;
    private Address address;
    private Contact contact;
    private String healthDataCID;


    @Data
    public static class Contact {
        private String email;
        private String phone;
    }

    @Data
    public static class Address {
            private String street;
            private String city;
            private String country;
            private String zipcode;


    }

}
