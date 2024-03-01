package com.youcode.interplanetary.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Data
public class MedicalHistory {
    private boolean familyHistoryHeartDisease;
    private List<String> existingConditions;
    private List<String> previousSurgeries;
    private List<String> medications;
}