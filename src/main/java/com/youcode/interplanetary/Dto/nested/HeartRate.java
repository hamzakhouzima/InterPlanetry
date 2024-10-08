package com.youcode.interplanetary.Dto.nested;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Data
@Component
public  class HeartRate {
    private int resting;
    private int exercise;

    public HeartRate(int i, int i1) {
        this.resting = i;
        this.exercise = i1;
    }
}