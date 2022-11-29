package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NeededInfo {

    private int lowestTemp;
    private int highestTemp;
    private int rainDrops;
    private int clouds;
    private int fog;
    private String img;
    private String explanation;
}
