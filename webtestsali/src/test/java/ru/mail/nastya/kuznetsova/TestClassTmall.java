package ru.mail.nastya.kuznetsova;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class TestClassTmall extends BaseTest {

    @DataProvider(name = "inputValues")
    public static Object [] getSearchInputValues(){
        return new Object[] {
                "apple",
                "lego duplo"
        };
    }

    @Test(groups = {"tmall"}, dataProvider = "inputValues")
    public static void testItemsLinkNameConformity(String inputValue){
        // Define flag for checking presence of invalid items
        Boolean invalidItemsPresence = false;

        // Type needed data to the "Search" field
        searchGoods(inputValue);

        // Wait some time for full page readiness
        waitForLoad();

        // Calculation the number of pages with found items
        // It is more like 'workaround' until more elegant solution of getting total pages number will be found
        int pageNumber = calculatePageNumber();

        // Go page by page
        for (int page = 1; page <= pageNumber; page++) {

            // Go to the "page" page
            // Step is skipped for "page == 1" because initially we already stay on the 1st page
            if (page != 1){
                firefox.findElement(By.id("pagination-bottom-input")).sendKeys(String.valueOf(page));
                firefox.findElement(By.id("pagination-bottom-goto")).click();
            }

            List<WebElement> items = firefox.findElements(
                    By.xpath(".//li[contains(@class, \"list-item\")]//a[contains(@class, \"history-item product\")]")

            );

            HashMap<String, String> itemsLinkNameMap = new HashMap<String, String> ();

            for (WebElement item: items){
                String itemTextInList = item.getText();
                String itemPageLink = item.getAttribute("href");

                itemsLinkNameMap.put(itemPageLink, itemTextInList);
                }

            for(Map.Entry<String, String> entry : itemsLinkNameMap.entrySet()) {
                String itemHref = entry.getKey();
                String itemText = entry.getValue();

                // Navigate to page with item to check it displayed name
                firefox.navigate().to(itemHref);

                String itemTextOnPage = firefox.findElement(By.xpath(".//*[@class=\"product-name\"]")).getText();

                if (!itemTextOnPage.equals(itemText)) {
                    invalidItemsPresence = true;
                }

                // Navigate back to the page with items' list
                firefox.navigate().back();
            }
        }
        Assert.assertFalse(invalidItemsPresence);
    }
}

