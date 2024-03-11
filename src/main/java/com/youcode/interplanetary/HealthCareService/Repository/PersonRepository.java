package com.youcode.interplanetary.HealthCareService.Repository;

import com.youcode.interplanetary.HealthCareService.Entity.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

     Person findPersonByEmail(String email);

//     Person updateCID(String newCID);
     Person findByHealthDataCIDOrEmail(String healthDataCID, String email);


     @Modifying
     @Query("UPDATE Person p SET p.healthDataCID = :newCID WHERE p.healthDataCID = :oldCID")
     void updateCID(@Param("oldCID") String oldCID, @Param("newCID") String newCID);

}
