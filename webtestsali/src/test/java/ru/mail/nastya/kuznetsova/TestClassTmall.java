package ru.mail.nastya.kuznetsova;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;


public class TestClassTmall extends BaseTest {

    @Test(groups = {"tmall"})
    public static void testItemsLinkNameConformity(){
        // Define flag for checking presence of invalid items
        Boolean invalidItemsPresence = false;

        // Type needed data to the "Search" field
        searchGoods("smartphone");

        // Wait some time for full page readiness
        waitForLoad();

        // Calculation the number of pages with found items
        // It is more like 'workaround' until more elegant solution of getting total pages number will be found
        String pageNumberMax = firefox.findElement(By.id("pagination-max")).getAttribute("innerHTML");
        int pageNumber = Integer.valueOf(pageNumberMax);

        System.out.println(pageNumber);

        int count = 1;

        // Go page by page
        for (int page = 1; page <= pageNumber; page++) {
            System.out.println(page);

            // Go to the "page" page
            // Step is skipped for "page == 1" because initially we already stay on the 1st page
            if (page != 1){
                firefox.findElement(By.id("pagination-bottom-input")).sendKeys(String.valueOf(page));
                firefox.findElement(By.id("pagination-bottom-goto")).click();
            }

            // Items on page are described in two list elements
            String[] itemsListIDs = {"hs-list-items", "hs-below-list-items"};
            for (String itemsListID : itemsListIDs) {
                List<WebElement> items = firefox.findElements(
                        By.xpath(
                                String.format(
                                        ".//*[@id=\"%s\"]//*[contains(@class, \"history-item product\")]",
                                        itemsListID
                                )
                        )
                );

                // Go item by item
                for (int i = 1; i <= items.size(); i++) {
                    WebElement item = firefox.findElement(
                            By.xpath(
                                    String.format(
                                            ".//*[@id=\"%s\"]//li[%d]//*[contains(@class, \"history-item product\")]",
                                            itemsListID, i
                                    )
                            )
                    );
                    String itemTextInList = item.getText();

                    System.out.println(itemTextInList);
                    System.out.println(count);
                    count++;

                    // Navigate to page with item to check it displayed name
                    firefox.navigate().to(item.getAttribute("href"));

                    String itemTextOnPage = firefox.findElement(By.xpath(".//*[@class=\"product-name\"]")).getText();

                    if (!itemTextOnPage.equals(itemTextInList)) {
                        invalidItemsPresence = true;
                    }

                    // Navigate back to the page with items' list
                    firefox.navigate().back();

                }
            }
        }
        Assert.assertFalse(invalidItemsPresence);
    }
}
