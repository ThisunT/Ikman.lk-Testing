package exercise_02;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thisun Pathirage on 12/07/2018.
 */

public class TestIkmanAdsBDD {

    private static WebDriver chromeDriver;
    private int numberOfHousesWithThreeBeds;
    
    @Given("^Navigate to \"([^\"]*)\" page$")
    public void navigateToPage(){
        System.setProperty("webdriver.chrome.driver", "/Users/thisunpathirage/Downloads/chromedriver");
        chromeDriver = new ChromeDriver();
        chromeDriver.get("https://ikman.lk/");

    }

    @When("^Click on Property link$")
    public void clickOnThePropertyLinks() throws Throwable{
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div.home-top > div > div.home-focus > div > div:nth-child(1) > div:nth-child(2) > a > span:nth-child(2)")).click();
    }

    @When("^Click on Houses link$")
    public void clickOnTheHousesLink() throws Throwable {
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div:nth-child(3) > div > ul > li > ul:nth-child(2) > li > ul > li.ui-link-tree-item.cat-411 > a > span")).click();

    }

    @When("^Click on Colombo link$")
    public void clickOnColomboLink() throws Throwable {
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div:nth-child(4) > div > ul > li > ul > li.ui-link-tree-item.loc-1506 > a > span")).click();

    }

    @When("^I click on Price link$")
    public void i_click_on_Price_link() throws Throwable {
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div.ui-accordion-item.filter-price > a > span")).click();

    }

    @When("^Enter min_price of house as \"([^\"]*)\" and a max_price of house as \"([^\"]*)\"$")
    public void enterMinMaxPrices(String min, String max) throws Throwable {
        chromeDriver.findElement(By.cssSelector("#filters\\5b 0\\5d \\5b minimum\\5d")).sendKeys(min);
        chromeDriver.findElement(By.cssSelector("#filters\\5b 0\\5d \\5b maximum\\5d")).sendKeys(max);

    }

    @When("^Click Apply Button$")
    public void clickApplyButton() throws Throwable {
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div.ui-accordion-item.filter-price.is-open > div > div:nth-child(6) > div > div > button")).click();

    }

    @When("^Click on Beds link$")
    public void clickOnBedsLink() throws Throwable {
        chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div.ui-accordion-item.filter-enum.filter-bedrooms > a > span")).click();

    }

    @When("^Select the number of Beds$")
    public void selectTheNumberOfBeds() throws Throwable {
        chromeDriver.findElement(By.cssSelector("#filters2values-3")).click();

    }

    @Then("^Get the number of ads$")
    public void getTheNumberOfAds() throws Throwable {
        numberOfHousesWithThreeBeds = Integer.parseInt(chromeDriver.findElement(By.cssSelector("body > div.app-content > div > div.serp-listing > div.ui-panel.is-rounded.serp-panel > div.ui-panel-content.ui-panel-block > div:nth-child(1) > div.col-12.lg-3.lg-filter-area > div > div > form > div > div.ui-accordion-item.filter-enum.filter-bedrooms.is-open > div > ul > li.ui-link-tree-item.bedrooms-3 > a > span")).getText());
        System.out.println("number of beds ads found " + numberOfHousesWithThreeBeds);

    }

    @Then("^Get all the prices of the filtered ads$")
    public void getAllThePricesOfTheFilteredAds() throws Throwable {
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

    public static String validatePrice(int price){
        return price>= 5000000 && price<=7500000? "" : "Not in the price range";
    }

    public static String validateBeds(int numberOfBeds){
        return numberOfBeds==3? "" : "Not the chosen number of beds";
    }
}

