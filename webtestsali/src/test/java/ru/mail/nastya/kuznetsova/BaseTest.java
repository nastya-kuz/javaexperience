package ru.mail.nastya.kuznetsova;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver firefox;

    @BeforeClass
    public static void setup(){
        System.setProperty("webdriver.gecko.driver", "/Users/akuznetsova/Downloads/geckodriver");
        firefox = new FirefoxDriver();
        firefox.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        firefox.get("https://ru.aliexpress.com");
    }

    @AfterClass
    public static void teardown(){
        firefox.quit();
    }

}
