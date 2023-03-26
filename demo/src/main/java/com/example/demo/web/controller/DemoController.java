package com.example.demo.web.controller;

import com.example.demo.services.DemoService;
import com.example.demo.web.model.DemoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/checknumber/{number}")
    public ResponseEntity<DemoDto> checkNumber(@PathVariable("number") int num){
        return new ResponseEntity<>(demoService.numInformation(num), HttpStatus.OK);
    }
}
