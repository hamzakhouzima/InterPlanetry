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
}