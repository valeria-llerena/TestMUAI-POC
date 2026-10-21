package com.saucedemo.pages;

import com.saucedemo.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage {

    private final WebDriverWait wait;
    private final By title = By.cssSelector("[data-test='title']");

    public InventoryPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.explicitWaitSeconds()));
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText().trim();
    }
}
