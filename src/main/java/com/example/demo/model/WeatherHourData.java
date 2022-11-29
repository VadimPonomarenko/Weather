package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherHourData {

    private int hour;
    private int temp;
    private int reel_feel;
    private int clouds;
    private String cloudNotes;
    private String weatherIcon;
    private int rain;
    private int wind_speed;
    private int wind_max;
    private float humidity;
    private float fog_probability;
    private String hidden;
}
