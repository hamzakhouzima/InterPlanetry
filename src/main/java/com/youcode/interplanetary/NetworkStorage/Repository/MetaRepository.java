package com.youcode.interplanetary.NetworkStorage.Repository;

import com.youcode.interplanetary.NetworkStorage.Entity.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends JpaRepository<MetaData, String> {

    MetaData findByUserCid(String userCid);

}
