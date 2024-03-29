package com.youcode.interplanetary.Dto.nested;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
@Component
public  class BloodSugar {
    private int fasting;
    private int postprandial;

    public BloodSugar(int i, int i1) {
        this.fasting = i;
        this.postprandial = i1;
    }
}