package support;

import java.util.concurrent.TimeUnit;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static support.TestContext.getDriver;

public class Hooks implements Loggable {

    @Before(order = 0)
    public void scenarioStart(Scenario scenario) {
        getLogger().info("\nScenario: " + scenario.getName() + "\n====== BEGIN ====\n");
        TestContext.initialize();
        getDriver().manage().deleteAllCookies();
//        getDriver().manage().window().setSize(new Dimension(1920,1080));
        getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @After(order = 0)
    public void scenarioEnd(Scenario scenario) {
        getLogger().info("\nScenario: " + scenario.getName() + "\n====== END ====\n");
        if (scenario.isFailed()) {
            TakesScreenshot screenshotTaker = (TakesScreenshot) getDriver();
            byte[] screenshot = screenshotTaker.getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }
        TestContext.close();
    }
}
