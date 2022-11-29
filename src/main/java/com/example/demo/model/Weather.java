package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Weather {

    private String city;
    private String date;
    private int hour;
    private int temp;
    private int reel_feel;
    private int clouds;
    private String cloudNotes;
    private int rain;
    private int wind_speed;
    private int wind_max;
    private float humidity;
    private float fog_probability;
}
