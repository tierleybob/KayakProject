package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class KayakHome extends Page {

    public KayakHome(){
        this.setUrl("https://www.kayak.com/");
    }

    @FindBy(xpath = "(//div[contains(@id,'origin-airport-display-inner')])[1]")
    private WebElement originAprt;

    @FindBy(xpath = "(//input[contains(@id,'origin-airport')])[1]")
    private WebElement originAprtTxtBox;

    @FindBy(xpath = "(//*[contains(@id,'origin-airport-display-multi-container')])[1]//button[contains(@class,'js-remove-selection')]")
    private WebElement originPreSelectedRemove;

    @FindBy(xpath = "(//div[contains(@id,'destination-airport-display')])[1]")
    private WebElement destAprt;

    @FindBy(xpath = "(//input[contains(@id,'destination-airport')])[1]")
    private WebElement destAprtTxtBox;

    @FindBy(xpath = "//input[contains(@id,'origin-airport-nearbyCheck')]")
    private WebElement inclNearbyAirportsOrigin;

    @FindBy(xpath = "//input[contains(@id,'destination-airport-nearbyCheck')]")
    private WebElement inclNearbyAirportsDest;

    @FindBy(xpath = "//div[contains(@id,'origin-airport-nearbyCheck-icon')]")
    private WebElement inclNearbyAirportsOriginIcon;

    @FindBy(xpath = "//div[contains(@id,'destination-airport-nearbyCheck-icon')]")
    private WebElement inclNearbyAirportsDestIcon;

    @FindBy(xpath = "//button[contains(@id,'compareTo-noneLink')]")
    private WebElement compareNone;

    @FindBy(xpath = "(//div[contains(@id,'dateRangeInput-display-start-inner')])[1]")
    private WebElement departDate;

    @FindBy(xpath = "//div[contains(@id,'depart-input')]")
    private WebElement departDateBox;

    @FindBy(xpath = "(//div[contains(@id,'dateRangeInput-display-end-inner')])[1]")
    private WebElement returnDate;

    @FindBy(xpath = "//div[contains(@id,'return-input')]")
    private WebElement returnDateBox;

    @FindBy(xpath = "(//button[contains(@id,'submit')])[3]")
    private WebElement submitSearch;

    public void removeExistOriginAprt(){
        try{
            if (originPreSelectedRemove.isDisplayed()){
                click(originPreSelectedRemove);
                click(originAprt);
            }
        }
        catch (NoSuchElementException e){
            click(originAprt);
            originAprtTxtBox.clear();

        }
    }

    public void populateOrigin(String origin){
        click(originAprt);
        originAprtTxtBox.sendKeys(origin);
        originAprtTxtBox.sendKeys(Keys.ENTER);
    }

    public void populateDestination(String dest) throws InterruptedException {
        click(destAprt);
        Thread.sleep(1000);
        destAprtTxtBox.sendKeys(dest);
        destAprtTxtBox.sendKeys(Keys.ENTER);
    }

    public String setIncludeNearbyAirportOrigin(String flag){
        click(originAprt);
        if ((flag.equalsIgnoreCase("yes") && inclNearbyAirportsOrigin.getAttribute("aria-checked").equalsIgnoreCase("false")) ||
                (flag.equalsIgnoreCase("no") && inclNearbyAirportsOrigin.getAttribute("aria-checked").equalsIgnoreCase("true")))
            click(inclNearbyAirportsOriginIcon);

       return inclNearbyAirportsOrigin.getAttribute("aria-checked");
    }

    public String setIncludeNearbyAirportDest(String flag){
        click(destAprt);
        if ((flag.equalsIgnoreCase("yes") && inclNearbyAirportsDest.getAttribute("aria-checked").equalsIgnoreCase("false")) ||
                (flag.equalsIgnoreCase("no") && inclNearbyAirportsDest.getAttribute("aria-checked").equalsIgnoreCase("true")))
            click(inclNearbyAirportsDestIcon);

        destAprtTxtBox.sendKeys(Keys.ENTER);

        return inclNearbyAirportsDest.getAttribute("aria-checked");
    }

    public void setDates(String depDateString, String retDateString) throws InterruptedException {
        click(departDate);
        Thread.sleep(1000);
        click(departDateBox);
        Thread.sleep(1000);
        departDateBox.clear();
        departDateBox.sendKeys(depDateString);
        click(returnDateBox);
        Thread.sleep(1000);
        returnDateBox.clear();
        returnDateBox.sendKeys(retDateString);
        returnDateBox.sendKeys(Keys.ENTER);
    }

    public void search(){
        click(compareNone);
        click(submitSearch);
    }
}
