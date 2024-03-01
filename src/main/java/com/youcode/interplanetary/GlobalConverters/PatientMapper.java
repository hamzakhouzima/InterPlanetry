package com.youcode.interplanetary.GlobalConverters;

import com.youcode.interplanetary.Dto.PatientDto;
import com.youcode.interplanetary.HealthCareService.Entity.Person;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PatientMapper {

    private final ModelMapper modelMapper;

    public PatientMapper() {
        this.modelMapper = new ModelMapper();
    }

    public PatientDto convertToDto(Person patient) {
        return modelMapper.map(patient, PatientDto.class);
    }

    public Person convertToEntity(PatientDto patientDto) {
        return modelMapper.map(patientDto, Person.class);
    }
}
