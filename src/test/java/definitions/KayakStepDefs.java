package definitions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.bcel.verifier.exc.AssertionViolatedException;
import org.openqa.selenium.By;
import pages.KayakHome;
import pages.KayakSearch;
import support.Loggable;

import static org.assertj.core.api.Assertions.*;
import static support.TestContext.getDriver;

public class KayakStepDefs implements Loggable{

    private String originArpt;
    private String destArpt;
    private boolean inclNearbyOrigin = false;
    private boolean inclNearbyDest = false;

    @Given("^I open kayak page$")
    public void iOpenPage() {
        getLogger().info("\nOpening Kayak Home page.\n");
        new KayakHome().open();
    }

    @When("^I remove pre-selected origin airport$")
    public void iRemovePreSelectedOriginAirport(){
        getLogger().info("\nRemoving pre-selected origin airport\n");
        new KayakHome().removeExistOriginAprt();
    }


    @And("^I click Search$")
    public void iClickSearch() throws InterruptedException {
        getLogger().info("\nClicking search button\n");
        new KayakHome().search();
        // Verify if CAPTCHA is present, then sleep for 65 sec if it is
        if(getDriver().findElements(By.xpath("//script[contains(@src,'captcha')]")).size() != 0)
            Thread.sleep(65000);
    }


    @And("^I set origin airport as \"([^\"]*)\"$")
    public void iSetOriginAirportAs(String origin){
        getLogger().info("\nSetting origin airport as " + origin + ".\n");
        new KayakHome().populateOrigin(origin);
        originArpt = origin;
    }

    @And("^I set origin to Include Nearby Airports as \"([^\"]*)\"$")
    public void iSetOriginToIncludeNearbyAirportsAs(String origNearby){
        getLogger().info("\nSetting origin Include Nearby Airports as " + origNearby + ".\n");
        new KayakHome().setIncludeNearbyAirportOrigin(origNearby);
        if(origNearby.equalsIgnoreCase("yes"))
            inclNearbyOrigin = true;
    }

    @And("^I set destination airport as \"([^\"]*)\"$")
    public void iSetDestinationAirportAs(String dest) throws InterruptedException{
        getLogger().info("\nSetting destination airport as " + dest + ".\n");
        new KayakHome().populateDestination(dest);
        destArpt = dest;
    }

    @And("^I set destination to Include Nearby Airports as \"([^\"]*)\"$")
    public void iSetDestinationToIncludeNearbyAirportsAs(String destNearby){
        getLogger().info("\nSetting destination Include Nearby Airports as " + destNearby + ".\n");
        new KayakHome().setIncludeNearbyAirportDest(destNearby);
        if(destNearby.equalsIgnoreCase("yes"))
            inclNearbyDest = true;
    }

    @And("^I set depart date as \"([^\"]*)\", return date as \"([^\"]*)\"$")
    public void iSetDepartDateAsReturnDateAs(String depDateString, String retDateString) throws InterruptedException{
        getLogger().info("\nValidating departure and return dates\n");
        // Make sure test dates provided are in the future and return date is after the depart date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate depDate = LocalDate.parse(depDateString, formatter);
        LocalDate retDate = LocalDate.parse(retDateString, formatter);
        assertThat(depDate.isAfter(LocalDate.now())).isTrue();
        assertThat(retDate.isAfter(depDate)).isTrue();
        getLogger().info("\nDates are valid, setting depart date to " + depDateString + ", return date to " + retDateString + ".\n");
        new KayakHome().setDates(depDateString, retDateString);
    }

    @Then("^I select search result (\\d+) and verify Origin and Destination match input$")
    public void iSelectSearchResultAndVerifyOriginAndDestinationMatchInput(int resultNo){
        getLogger().info("\nVerifying Origin and Destination information in result " + resultNo + ".\n");

        KayakSearch testPage = new KayakSearch();

        getLogger().info("\nVerifying trip from origin: "+originArpt+" to destination: "+destArpt+".\n");
        String departFlight = testPage.getDepartValue(resultNo);
        getLogger().info("Origin-Destination info from search result: " + departFlight);

        try{assertThat(departFlight.substring(1,4)).isEqualToIgnoringCase(originArpt);}
        catch (AssertionError e){
            if (inclNearbyOrigin){
                getLogger().info("\nOrigin airport doesn't match but Include nearby for Origin was turned on, so this is not a bug\n" + e.getMessage());
            }
            else
                throw new AssertionViolatedException("\nERROR: Origin airport doesn't match and Include nearby for Origin is off, potential bug found!\n" + e.getMessage());

        }

        try{assertThat(departFlight.substring(7,10)).isEqualToIgnoringCase(destArpt);}
        catch (AssertionError e){
            if (inclNearbyDest){
                getLogger().info("\nDestination airport doesn't match but Include nearby for Destination was turned on, so this is not a bug\n" + e.getMessage());
            }
            else
                throw new AssertionViolatedException("\nERROR: Destination airport doesn't match and Include nearby for Destination is off, potential bug found!\n" + e.getMessage());
        }

        getLogger().info("\nVerifying trip from destination: "+destArpt+" to origin: "+originArpt+".\n");
        String returnFlight = testPage.getReturnValue(resultNo);
        getLogger().info("Destination-Origin info from search result: " + returnFlight);

        try{assertThat(returnFlight.substring(1,4)).isEqualToIgnoringCase(destArpt);}
        catch (AssertionError e){
            if (inclNearbyDest){
                getLogger().info("\nDestination airport doesn't match but Include nearby for Destination was turned on, so this is not a bug\n" + e.getMessage());
            }
            else
                throw new AssertionViolatedException("\nERROR: Destination airport doesn't match and Include nearby for Destination is off, potential bug found!\n" + e.getMessage());
        }

        try{assertThat(returnFlight.substring(7,10)).isEqualToIgnoringCase(originArpt);}
        catch (AssertionError e){
            if (inclNearbyOrigin){
                getLogger().info("\nOrigin airport doesn't match but Include nearby for Origin was turned on, so this is not a bug\n" + e.getMessage());
            }
            else
                throw new AssertionViolatedException("\nERROR: Origin airport doesn't match and Include nearby for Origin is off, potential bug found!\n" + e.getMessage());
        }
    }
}
