package ru.mail.nastya.kuznetsova;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClassAli extends BaseTest{

    @Test(groups = {"ali"})
    public static void testSearch() {
        searchGoods("fifa");

        waitElementPresence("ipt-kwd");

        WebElement fifaCap = firefox.findElement(By.xpath("//a[contains(@href, 'plush-cap')]"));
        Assert.assertTrue(fifaCap.isDisplayed());
    }

    @Test(groups = {"ali"})
    public static void testShopCart(){
        WebElement cartNum = firefox.findElement(By.id("nav-cart-num"));

        Assert.assertEquals("0", cartNum.getText());

    }

}
