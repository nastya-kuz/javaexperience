package ru.mail.nastya.kuznetsova;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MainPageTest extends BaseTest{

    @Test
    public static void testSearch() {
        WebElement searchField = firefox.findElement(By.id("search-key"));
        searchField.sendKeys("fifa");
        searchField.submit();

        WebDriverWait wait = new WebDriverWait(firefox, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ipt-kwd")));

        WebElement fifaCap = firefox.findElement(By.xpath("//a[contains(@href, 'plush-cap')]"));
        Assert.assertTrue(fifaCap.isDisplayed());
    }

    @Test
    public static void testShopCart(){
        WebElement cartNum = firefox.findElement(By.id("nav-cart-num"));

        Assert.assertEquals("0", cartNum.getText());

    }

}
