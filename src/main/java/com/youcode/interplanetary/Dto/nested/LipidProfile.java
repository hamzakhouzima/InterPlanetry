package com.youcode.interplanetary.Dto.nested;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
@Component
public  class LipidProfile {
    private int totalCholesterol;
    private int ldl;
    private int hdl;
    private int triglycerides;

    public LipidProfile(int i, int i1, int i2, int i3) {
        this.totalCholesterol = i;
        this.ldl = i1;
        this.hdl = i2;
        this.triglycerides = i3;
    }
}