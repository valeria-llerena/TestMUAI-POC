package com.saucedemo.driver;

import com.saucedemo.config.TestConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void startDriver() {
        String ltUsername = System.getenv("LT_USERNAME");
        String ltAccessKey = System.getenv("LT_ACCESS_KEY");

        // Si existen las credenciales de TestMu AI, se ejecuta de manera remota en la nube
        if (ltUsername != null && !ltUsername.isEmpty() && ltAccessKey != null && !ltAccessKey.isEmpty()) {
            try {
                ChromeOptions browserOptions = new ChromeOptions();
                browserOptions.setPlatformName("Windows 10");
                browserOptions.setBrowserVersion("dev");

                HashMap<String, Object> ltOptions = new HashMap<>();
                ltOptions.put("username", ltUsername);
                ltOptions.put("accessKey", ltAccessKey);
                ltOptions.put("project", "SauceDemo BDD Project");
                ltOptions.put("w3c", true);
                ltOptions.put("plugin", "java-java");
                
                browserOptions.setCapability("LT:Options", ltOptions);

                String hubURL = "https://" + ltUsername + ":" + ltAccessKey + "@hub.lambdatest.com/wd/hub";
                DRIVER.set(new RemoteWebDriver(new URL(hubURL), browserOptions));
                return;

            } catch (MalformedURLException e) {
                throw new RuntimeException("Error al conectar con el Grid de TestMu AI (LambdaTest)", e);
            }
        }

        // De lo contrario, se ejecuta localmente como lo tenías configurado
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        if (TestConfig.headless()) {
            options.addArguments("--headless=new");
        }
        DRIVER.set(new ChromeDriver(options));
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}