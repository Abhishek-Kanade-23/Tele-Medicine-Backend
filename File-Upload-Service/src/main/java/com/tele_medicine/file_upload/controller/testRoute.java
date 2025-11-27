package com.tele_medicine.file_upload.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")

public class testRoute {
    @GetMapping()
    public String test(){
        return "File-upload service running !";
    }

}
