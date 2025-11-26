package com.tele_medicine.file_upload.controller;

import com.tele_medicine.file_upload.dto.FileResponse;
import com.tele_medicine.file_upload.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("test")
public class testRoute {
    @GetMapping()
    public String test(){
        return "File-upload service running !";
    }

}
