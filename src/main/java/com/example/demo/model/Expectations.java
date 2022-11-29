package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Expectations {
    private String city;
    private String dayOfMonth;
    private String weekDay;
    private String time;
    private String todayIcon;
    private String todayVideo;
    private int fog;
    private String currentTemp;
    private String expectations1;
    private String expectations2;
    private String secondDay;
    private String thirdDay;
    private String fourthDay;
    private String fifthDay;
}
