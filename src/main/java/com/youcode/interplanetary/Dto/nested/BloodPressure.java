package com.youcode.interplanetary.Dto.nested;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
@Component
public  class BloodPressure {
    private int systolic;
    private int diastolic;

    public BloodPressure(int i, int i1) {
        this.systolic = i;
        this.diastolic = i1;

    }
}