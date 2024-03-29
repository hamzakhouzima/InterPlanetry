package com.youcode.interplanetary.Dto.nested;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
@Component
public  class Electrolytes {
    private double potassium;
    private double sodium;
    private double chloride;

    public Electrolytes(double v, int i, int i1) {
        this.potassium = v;
        this.sodium = i;
        this.chloride = i1;
    }
}