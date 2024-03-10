package com.example.books.utils;

import com.example.books.DTO.Root;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


@Slf4j
@Component
@NoArgsConstructor
public class FileWorker {

    @Value("${library.json.path}")
    private String filePath;

    //File file = new File("src/main/resources/library.json");
    //File file = new File(filePath);

    ObjectMapper objectMapper = new ObjectMapper();

    public Root readFromFile() {
        log.info("File path: {}", filePath);
        File file = new File("src/main/resources/library.json");
        Root root = null;


        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        try {
            root = objectMapper.readValue(file, Root.class);
        } catch (IOException e) {
            log.error("Error reading from file", e);
        }

        return root;
    }

    public void writeToFile(Root root) {
        File file = new File("src/main/resources/library.json");
        try {
            objectMapper.writeValue(file, root);
        } catch (IOException e) {
            log.error("Error writing to file", e);
        }

    }
}
