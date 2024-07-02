package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.crptapi.CrptApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        CrptApi api = new CrptApi(TimeUnit.SECONDS, 1, "https://ismp.crpt.ru/api/v3/lk/documents/create");

        String json = "";
        try {
            byte[] bytes = Files.readAllBytes(Paths.get("test.json"));
            json = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        CrptApi.Document document = null;
        try {
            document = mapper.readValue(json, CrptApi.Document.class);
        } catch (JsonProcessingException e) {
            //TODO
            throw new RuntimeException(e);
        }

        String signature = "signature";

        try {
            api.createDocument(document, signature);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        api.shutdown();
    }
}