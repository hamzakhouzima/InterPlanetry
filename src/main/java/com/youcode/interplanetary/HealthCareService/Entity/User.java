//package com.youcode.interplanetary.HealthCareService.Entity;
//
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.sql.Date;
//import java.util.Collection;
//
//@Data
//@RequiredArgsConstructor
//@Entity
//public class User implements UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//
//    private String FirstName;
//    private String LastName;
//    private String Email;
//    private String Password;
//    private String PhoneNumber;
//    private Date DateOfBirth;
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//}
