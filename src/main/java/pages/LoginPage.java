package pages;

import appmanager.ApplicationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static appmanager.ApplicationManager.driver;

public class LoginPage {

    private ApplicationManager applicationManager = new ApplicationManager();
    private WebDriver driver;
    public void enterUserNameAndPassword(){
        driver = applicationManager.getWebDriver();
    }
}