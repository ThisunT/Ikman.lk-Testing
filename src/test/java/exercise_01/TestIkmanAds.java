package exercise_01;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.List;

/**
 * Created by Thisun Pathirage on 09/07/2018.
 */

public class TestIkmanAds {

    private static WebDriver chromeDriver;
    private static int numberOfHousesWithThreeBeds;

    @BeforeTest
    public void setEnvironment() {
        System.setProperty("webdriver.chrome.driver", "/Users/thisunpathirage/Downloads/chromedriver");
        chromeDriver = new ChromeDriver();
        chromeDriver.get("https://ikman.lk/");
    }

    @Test(priority = 0)
    public static void openPagesToGetFilteredAds() {

        //click Property
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div.home-top > div > div.home-focus > div > div:nth-child(1) > div:nth-child(2) > a")).click();

        //click Houses
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div:nth-child(3) > div > ul > li > ul:nth-child(2) > li > ul > li.ui-link-tree-item.cat-411 > a")).click();

        //click Colombo
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div:nth-child(4) > div > ul > li > ul > li.ui-link-tree-item.loc-1506 > a")).click();

        //fill values
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div.ui-accordion-item.filter-price > a")).click();

        chromeDriver.findElement(By.xpath("//*[@id=\"filters[0][minimum]\"]")).sendKeys("5000000");
        chromeDriver.findElement(By.xpath("//*[@id=\"filters[0][maximum]\"]")).sendKeys("7500000");

        //click on Apply filter button
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div.ui-accordion-item.filter-price.is-open > div > div:nth-child(6) > div > div > button")).click();

        //choose the number of beds
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div.ui-accordion-item.filter-enum.filter-bedrooms > a")).click();
        chromeDriver.findElement(By.xpath("//*[@id=\"filters2values-3\"]")).click();

        //get the number of houses with three beds
        numberOfHousesWithThreeBeds = Integer.parseInt(chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-9 > div > div > div:nth-child(1) > div > div > div > span")).getText().substring(chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-9 > div > div > div:nth-child(1) > div > div > div > span")).getText().indexOf("of") + 3, chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-9 > div > div > div:nth-child(1) > div > div > div > span")).getText().indexOf("ads") - 1));

    }

    @Test(priority = 1)
    public static void getThePricesOfTheAds() {
        //list to store the prices
        //List<String> priceOfTheHouses = new ArrayList<>();

        int count = 1; //to label the add
        //iterate through all the pages
        for (int i=0; i<numberOfHousesWithThreeBeds/25 + 1; i++){

            //catch the filtered items
            WebElement serpItems = chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-9 > div > div > div.row.lg-g > div.col-12.lg-9 > div"));
            List<WebElement> listItems = serpItems.findElements(By.className("ui-item"));

            //print the price of each list item and the validation of the record is attached to it
            for (WebElement item : listItems) {
                System.out.println("Ad Number "+ count +" Price is : "+item.findElement(By.className("item-info")).getText() + " "+ validatePrice(Integer.parseInt(item.findElement(By.className("item-info")).getText().replace("Rs ", "").replace(",",""))) + " " + validateBeds(Integer.parseInt(item.findElement(By.className("item-meta")).getText().substring(item.findElement(By.className("item-meta")).getText().indexOf("Beds")+6,item.findElement(By.className("item-meta")).getText().indexOf(",")))));
                //priceOfTheHouses.add(item.findElement(By.className("item-info")).getText()); //just for fun :D
                count++;
            }

            if(i!=numberOfHousesWithThreeBeds/25){ //check for the last page of the results
                //next button click
                chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div.row.lg-g > div > div > div > div > div > a.col-6.lg-3.pag-next")).click();
            }
        }
        //System.out.println(priceOfTheHouses);
    }

    @AfterTest
    public void exitChrome() {
        chromeDriver.close();
    }

    public static String validatePrice(int price){
        return price>= 5000000 && price<=7500000? "" : "Not in the price range";
    }

    public static String validateBeds(int numberOfBeds){
        return numberOfBeds==3? "" : "Not the chosen number of beds";
    }

}