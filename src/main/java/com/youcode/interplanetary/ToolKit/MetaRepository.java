package com.youcode.interplanetary.ToolKit;

import com.youcode.interplanetary.ToolKit.Entity.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends JpaRepository<MetaData, String> {

}
