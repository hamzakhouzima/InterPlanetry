package com.youcode.interplanetary.HealthCareService.Repository;

import com.youcode.interplanetary.HealthCareService.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {



}
