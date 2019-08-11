package pages;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static support.TestContext.getDriver;
import static support.TestContext.getExecutor;
import static support.TestContext.getWait;

public abstract class Page {

    private String url;

    public Page() {
        PageFactory.initElements(getDriver(), this);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void open() {
        getDriver().get(url);
    }

    private void clickWithJS(WebElement element) {
        getExecutor().executeScript("arguments[0].click();", element);
    }

    private void waitForClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(WebElement element) {
        waitForClickable(element);
        try {
            element.click();
        } catch (WebDriverException e) {
            clickWithJS(element);
        }
    }
}
