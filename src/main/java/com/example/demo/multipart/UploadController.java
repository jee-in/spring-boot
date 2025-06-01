package com.example.demo.multipart;

import com.example.demo.multipart.dto.ImageDescOnlyDto;
import com.example.demo.multipart.dto.ImageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @PostMapping(value = "/api/upload/modelAttribute")
    public ResponseEntity<String> uploadImageModelAttribute(@ModelAttribute ImageDto imageDto) {

        System.out.println(imageDto);   // ImageDto(title=다과 세트, description=다과 이미지입니다.)

        String title = imageDto.getTitle();
        System.out.println("title: " + title);
        String description = imageDto.getDescription();
        System.out.println("description: " + description);

        MultipartFile image = imageDto.getImage();
        System.out.println("이미지 파일명: " + image.getOriginalFilename());
        System.out.println("이미지 확장자: " + image.getContentType());
        System.out.println("이미지 사이즈: " + String.format("%,d", image.getSize()) + "바이트");

        return ResponseEntity.ok().body("success");
    }

    @PostMapping(value = "/api/upload/requestPart")
    public ResponseEntity<String> uploadImageRequestPart(
        @RequestPart ImageDescOnlyDto requestPart,
        @RequestPart MultipartFile image) {

        System.out.println(requestPart);    // ImageDescOnlyDto(title=다과 세트, description=다과 세트입니다.)

        String title = requestPart.getTitle();
        System.out.println("title: " + title);
        String description = requestPart.getDescription();
        System.out.println("description: " + description);

        System.out.println("이미지 파일명: " + image.getOriginalFilename());
        System.out.println("이미지 확장자: " + image.getContentType());
        System.out.println("이미지 사이즈: " + String.format("%,d", image.getSize()) + "바이트");

        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/api/upload/requestParam")
    public ResponseEntity<String> uploadImageRequestParam(
        @RequestParam String title,
        @RequestParam String description,
        @RequestParam MultipartFile image) {

        System.out.println("title: " + title);
        System.out.println("description: " + description);

        System.out.println("이미지 파일명: " + image.getOriginalFilename());
        System.out.println("이미지 확장자: " + image.getContentType());
        System.out.println("이미지 사이즈: " + String.format("%,d", image.getSize()) + "바이트");

        return ResponseEntity.ok().body("success");
    }
}
