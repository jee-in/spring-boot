package com.example.demo.multipart.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@Setter
public class ImageDto {
    private String title;
    private String description;
    private MultipartFile image;
}
