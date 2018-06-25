package ru.mail.nastya.kuznetsova;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver firefox;

    @BeforeClass
    public static void setup(){
        System.setProperty("webdriver.gecko.driver", "/Users/akuznetsova/Downloads/geckodriver");
        firefox = new FirefoxDriver();
        firefox.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        firefox.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @BeforeGroups(groups = {"ali"})
    public static void openAliPage(){
        firefox.get("https://ru.aliexpress.com");
    }

    @BeforeGroups(groups = {"tmall"})
    public static void openTmallPage(){
        firefox.get("https://tmall.aliexpress.com");
    }

    @AfterClass
    public static void teardown(){
        firefox.quit();
    }

    public static void searchGoods(String goods){
        WebElement searchField = firefox.findElement(By.id("search-key"));
        searchField.clear();
        searchField.sendKeys(goods);
        searchField.submit();
    }

    public static void waitElementPresence(String id){
        WebDriverWait wait = new WebDriverWait(firefox, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public static void waitForLoad() {
        new WebDriverWait(firefox, 30).until(
                (ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd).executeScript(
                        "return document.readyState").equals("interactive")
        );
        System.out.println("Page Loaded Completely");
    }

    public static int calculatePageNumber(){
        // Need to think about more flexible way of calculating number of pages
        // Current method can be false positive and returns 1 even if page is unavailable
        try {
            waitElementPresence("pagination-max");
            String pageNumberMax = firefox.findElement(By.id("pagination-max")).getAttribute("innerHTML");
            return Integer.valueOf(pageNumberMax);
        } catch (Exception e) {
            // If there are no hidden pagination-max element on the page, there is only 1 page
            return 1;
        }
    }

}
