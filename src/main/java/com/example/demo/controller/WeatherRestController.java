package com.example.demo.controller;

import com.example.demo.model.Expectations;
import com.example.demo.model.NeededInfo;
import com.example.demo.model.UtilForDays;
import com.example.demo.model.Weather5Days;
import com.example.demo.parser.Parser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class WeatherRestController {
    Parser parser;
    UtilForDays util = new UtilForDays();

    {
        try {
            parser = new Parser();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getData")
    public ResponseEntity<Weather5Days> weatherList() {
        final Weather5Days weather = parser.getData();
        return weather != null
                ? new ResponseEntity<>(weather, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getTodayExpectations")
    public ResponseEntity<Expectations> expectations() {
        final Expectations expectations = parser.getExpectations();
        return expectations != null
                ? new ResponseEntity<>(expectations, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getSecondDay")
    public ResponseEntity<NeededInfo> getSecondDay() {
        final NeededInfo secondDay = util.getList().get(0);
        return secondDay != null
                ? new ResponseEntity<>(secondDay, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getThirdDay")
    public ResponseEntity<NeededInfo> getThirdDay() {
        final NeededInfo secondDay = util.getList().get(1);
        return secondDay != null
                ? new ResponseEntity<>(secondDay, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getFourthDay")
    public ResponseEntity<NeededInfo> getFourthDay() {
        final NeededInfo secondDay = util.getList().get(2);
        return secondDay != null
                ? new ResponseEntity<>(secondDay, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getFifthDay")
    public ResponseEntity<NeededInfo> getFifthDay() {
        final NeededInfo secondDay = util.getList().get(3);
        return secondDay != null
                ? new ResponseEntity<>(secondDay, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
