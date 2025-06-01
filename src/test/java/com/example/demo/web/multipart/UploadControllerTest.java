package com.example.demo.web.multipart;

import com.example.demo.multipart.UploadController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UploadController.class)
public class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("@ModelAttribute 테스트")
    void testUploadImageModelAttribute() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
            "image",
            "test-image.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "image content".getBytes()
        );

        mockMvc.perform(multipart("/api/upload/modelAttribute")
                .file(image)
                .param("title", "다과 세트")
                .param("description", "다과 이미지입니다."))
            .andExpect(status().isOk())
            .andExpect(content().string("success"));
    }

    @Test
    @DisplayName("@RequestPart 테스트")
    void testUploadImageRequestPart() throws Exception {
        MockMultipartFile requestPart = new MockMultipartFile(
            "requestPart",
            "",
            MediaType.APPLICATION_JSON_VALUE,
            "{\"title\":\"다과 세트\",\"description\":\"다과 세트입니다.\"}".getBytes()
        );

        MockMultipartFile image = new MockMultipartFile(
            "image",
            "test-image.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "image content".getBytes()
        );

        mockMvc.perform(multipart("/api/upload/requestPart")
                .file(requestPart)
                .file(image)
                .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isOk())
            .andExpect(content().string("success"));
    }

    @Test
    @DisplayName("@RequestParam 테스트")
    void testUploadImageRequestParam() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
            "image",
            "test-image.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            "image content".getBytes()
        );

        mockMvc.perform(multipart("/api/upload/requestParam")
                .file(image)
                .param("title", "다과 세트")
                .param("description", "다과 이미지입니다."))
            .andExpect(status().isOk())
            .andExpect(content().string("success"));
    }
}
