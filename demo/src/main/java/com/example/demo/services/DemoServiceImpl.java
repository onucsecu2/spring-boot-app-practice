package com.example.demo.services;

import com.example.demo.web.model.DemoDto;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService{
    @Override
    public DemoDto numInformation(int num) {
        String verdict;
        if(num%2==0){
            verdict = "EVEN";
        } else {
            verdict = "odd";
        }
        return DemoDto.builder().number(num)
                .verdict(verdict)
                .build();
    }
}
