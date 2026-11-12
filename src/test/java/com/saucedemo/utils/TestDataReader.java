// ABOUTME: Loads test data from JSON files in the testdata resource directory.
// ABOUTME: Provides typed access to nested JSON values via dot-notation paths.
package com.saucedemo.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TestDataReader {

    public static JsonObject load(String fileName) {
        try (InputStream is = TestDataReader.class.getClassLoader()
                .getResourceAsStream("testdata/" + fileName)) {
            if (is == null) {
                throw new RuntimeException("Test data file not found: testdata/" + fileName);
            }
            return JsonParser.parseReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8)).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data: " + fileName, e);
        }
    }

    public static String getString(JsonObject data, String path) {
        String[] parts = path.split("\\.");
        JsonObject current = data;
        for (int i = 0; i < parts.length - 1; i++) {
            current = current.getAsJsonObject(parts[i]);
        }
        return current.get(parts[parts.length - 1]).getAsString();
    }

    public static int getInt(JsonObject data, String path) {
        String[] parts = path.split("\\.");
        JsonObject current = data;
        for (int i = 0; i < parts.length - 1; i++) {
            current = current.getAsJsonObject(parts[i]);
        }
        return current.get(parts[parts.length - 1]).getAsInt();
    }
}
