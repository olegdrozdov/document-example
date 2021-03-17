package com.example.document;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("document")
public class DocumentRestController {

    @GetMapping("/download")
    public ResponseEntity<Resource> download() throws IOException {
        String fileName = "Тестовый документ.txt";

        byte[] bytes = getFileFromResourceAsStream(fileName);
        Resource resource = new ByteArrayResource(bytes);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", fileName);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private byte[] getFileFromResourceAsStream(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream.readAllBytes();
        }
    }

}
