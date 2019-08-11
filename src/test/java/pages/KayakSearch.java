package pages;

import java.util.LinkedList;
import java.util.List;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class KayakSearch extends Page {

    @FindBy(xpath = "//label[text() = 'Depart']/../../../../..//span[@class='origin-destination']")
    private List<WebElement> departList;

    @FindBy(xpath = "//label[text() = 'Return']/../../../../..//span[@class='origin-destination']")
    private List<WebElement> returnList;

    @FindBy(xpath = "(//button[contains(@id,'dialog-close')])[9]")
    private WebElement alertClose;

    @FindBy(xpath = "//a[contains(@id,'close')]")
    private WebElement compareAlertClose;

    public String getDepartValue(int result){
        LinkedList<WebElement> resultList = new LinkedList<>();
        for(WebElement element: departList){
            resultList.add(element);
        }
        return resultList.get(result).getAttribute("innerText");
    }

    public String getReturnValue(int result){
        LinkedList<WebElement> resultList = new LinkedList<>();
        for(WebElement element: returnList){
            resultList.add(element);
        }
        return resultList.get(result).getAttribute("innerText");
    }

    public void closeAlert(){
        try{
            if(alertClose.isDisplayed())
                click(alertClose);
            if(compareAlertClose.isDisplayed())
                click(compareAlertClose);
        }
            catch (NoSuchElementException e){
                try{
                    if(compareAlertClose.isDisplayed())
                        click(compareAlertClose);
                }
                catch (NoSuchElementException e1){
                    // do nothing as there are no popups
                }
            }
    }
}
