// ABOUTME: Captures browser screenshots on test failure with descriptive filenames.
// ABOUTME: Saves screenshots to the screenshots/ directory with test name and timestamp.
package com.saucedemo.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotHelper {

    private static final String SCREENSHOTS_DIR = "screenshots";
    private static final DateTimeFormatter TIMESTAMP_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public static String capture(WebDriver driver, String testName) {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String fileName = testName + "_" + timestamp + ".png";
        Path destination = Paths.get(SCREENSHOTS_DIR, fileName);

        try {
            Files.createDirectories(destination.getParent());
            Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot: " + fileName, e);
        }

        return destination.toAbsolutePath().toString();
    }
}
